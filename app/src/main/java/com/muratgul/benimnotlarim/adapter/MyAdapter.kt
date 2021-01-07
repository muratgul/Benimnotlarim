package com.muratgul.benimnotlarim.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.muratgul.benimnotlarim.R
import com.muratgul.benimnotlarim.model.VerilerModel
import com.muratgul.benimnotlarim.repository.Repository
import com.muratgul.benimnotlarim.view.AddDataActivity
import com.muratgul.benimnotlarim.view.MainActivity
import com.muratgul.benimnotlarim.viewmodel.MainViewModel
import com.muratgul.benimnotlarim.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter(val listener: MainActivity.OnClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var myList = emptyList<VerilerModel>()

    class MyViewHolder(itemView: View, val listener: MainActivity.OnClickListener) : RecyclerView.ViewHolder(itemView),
        PopupMenu.OnMenuItemClickListener {



        var itemClick: ((String) -> Unit)? = null
        var onItemLongPress: ((VerilerModel) -> Unit)? = null

        val menu = itemView.findViewById<TextView>(R.id.textViewOptions)
        val backgrounColor = itemView.findViewById<RelativeLayout>(R.id.renkLayout)

        fun showMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.options_menu)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
        }

        fun bindView(veriler: VerilerModel) {
            itemView.id_txt.text = veriler.id.toString()
            itemView.konu_txt.text = veriler.konu
            itemView.detay_txt.text = veriler.detay

            if(veriler.tamam == "1"){
                backgrounColor.setBackgroundResource(R.drawable.tamam_background)
            }else{
                backgrounColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }

            //val menu = LayoutInflater.from(itemView.context).inflate(R.layout.row_layout,itemView.contsLayout, false)

            menu.setOnClickListener {
                showMenu(it)
            }

            itemView?.setOnClickListener {
                Toast.makeText(itemView.context, veriler.konu, Toast.LENGTH_LONG).show()
                itemClick?.invoke(veriler.konu)
            }

            itemView?.setOnLongClickListener {
                onItemLongPress?.invoke(veriler)
                return@setOnLongClickListener true
            }
        }

        fun silmeIslemi(){

            itemView.visibility = View.GONE
        }

        fun duzenlemeIslemi(){

        }

        override fun onMenuItemClick(p: MenuItem?): Boolean {

            if (p?.itemId == R.id.menu1) {
                listener.onClick(Integer.parseInt(itemView.id_txt.text.toString()))
            }
            if (p?.itemId == R.id.menu2) {
                listener.onClickEdit(Integer.parseInt(itemView.id_txt.text.toString()))
            }

            return false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.itemView.id_txt.text = myList[position].id.toString()
        //holder.itemView.konu_txt.text = myList[position].konu
        //holder.itemView.detay_txt.text = myList[position].detay



        holder.bindView(myList[position])
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(newList: List<VerilerModel>) {
        myList = newList
        notifyDataSetChanged()
    }
}