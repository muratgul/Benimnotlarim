package com.muratgul.benimnotlarim.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.muratgul.benimnotlarim.R
import com.muratgul.benimnotlarim.model.VerilerModel
import com.muratgul.benimnotlarim.repository.Repository
import com.muratgul.benimnotlarim.viewmodel.MainViewModel
import com.muratgul.benimnotlarim.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_add_data.view.*

class AddDataActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_data)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        val contextView = findViewById<View>(R.id.main_act)

        val konu = findViewById<EditText>(R.id.et_konu)
        val detay = findViewById<EditText>(R.id.et_detay)
        val tamam = findViewById<CheckBox>(R.id.checkBox)
        var tamam_txt = "0"

        var gelenid: Int = 0

        gelenid = intent.getIntExtra("veri_id", 0);

        if (gelenid > 0) {
            //Toast.makeText(this,"Düzenleme",Toast.LENGTH_LONG).show()

            button.setText("Güncelle")

            viewModel.getPost(gelenid)
            viewModel.myResponse.observe(this, Observer { response ->
                Log.d("veriler", response.body()?.konu.toString())
                if (response.isSuccessful) {
                    konu.setText(response.body()?.konu.toString())
                    detay.setText(response.body()?.detay.toString())
                    if (response.body()?.tamam.toString() == "1") {
                        tamam.isChecked = true
                    }
                }
            })
        }


        button.setOnClickListener {
            closeKeyBoard()
            val konu_txt = konu.text.toString()
            val detay_txt = detay.text.toString()

            if (tamam.isChecked) {
                tamam_txt = "1"
            }

            var kaydetmeMesaji = ""

            if(gelenid > 0){
                kaydetmeMesaji = "Veri Başarıyla Güncellendi"
                viewModel.putPost(gelenid, konu_txt, detay_txt, tamam_txt)

            }else{
                kaydetmeMesaji = "Veri Başarıyla Kaydedildi"
                viewModel.pushPost2(0, konu_txt, detay_txt, tamam_txt)
            }

            viewModel.myResponseDefault.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    response.body().let {
                        if (response.code() == 201) {
                            Snackbar.make(
                                contextView,
                                kaydetmeMesaji,
                                Snackbar.LENGTH_LONG
                            ).show()
                            button.isEnabled = false
                        }

                    }
                } else {
                    Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
                    Snackbar.make(
                        contextView,
                        "Hata Oluştu: " + response.code().toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })


        }


    }



    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}