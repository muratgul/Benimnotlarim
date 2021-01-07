package com.muratgul.benimnotlarim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratgul.benimnotlarim.R
import com.muratgul.benimnotlarim.adapter.MyAdapter
import com.muratgul.benimnotlarim.repository.Repository
import com.muratgul.benimnotlarim.viewmodel.MainViewModel
import com.muratgul.benimnotlarim.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public interface OnClickListener {
        fun onClick(id: Int)
        fun onClickEdit(id: Int)
    }

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter(object : OnClickListener{
        override fun onClick(id: Int) {
            silme(id)
        }

        override fun onClickEdit(id: Int) {
            duzenle(id)
        }
    }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        setupRecyclersView()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)



        refreshData()


        swipeRefLayout.setOnRefreshListener {
            refreshData()
            swipeRefLayout.isRefreshing = false
        }

    }

    override fun onStart() {
        super.onStart()

        refreshData()
    }

    private fun refreshData(){
        veriYukleniyor.visibility = View.VISIBLE
        viewModel.getPosts()

        viewModel.myResponseAll.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    myAdapter.setData(it)
                }
            }


        })
        veriYukleniyor.visibility = View.GONE


    }



    private fun setupRecyclersView() {
        recyclersView.adapter = myAdapter
        recyclersView.layoutManager = LinearLayoutManager(this)
    }

    fun duzenle(id: Int){
        val intent = Intent(this, AddDataActivity::class.java)
        intent.putExtra("veri_id",id)
        startActivity(intent)
    }

    fun silme(id: Int){

        viewModel.delPost(id)
        viewModel.myResponseDefault.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    if(response.body()?.error == false){
                        refreshData()
                    }

                }
            }

        })


    }
}