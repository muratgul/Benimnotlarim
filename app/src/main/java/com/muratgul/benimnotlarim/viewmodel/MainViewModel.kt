package com.muratgul.benimnotlarim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratgul.benimnotlarim.api.DefaultResponse
import com.muratgul.benimnotlarim.model.VerilerModel
import com.muratgul.benimnotlarim.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<VerilerModel>> = MutableLiveData()
    val myResponseDefault: MutableLiveData<Response<DefaultResponse>> = MutableLiveData()
    val myResponseAll: MutableLiveData<Response<List<VerilerModel>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<VerilerModel>> = MutableLiveData()
    val myCustomPosts: MutableLiveData<Response<List<VerilerModel>>> = MutableLiveData()
    val myCustomPosts2: MutableLiveData<Response<List<VerilerModel>>> = MutableLiveData()
    val yukleniyor = MutableLiveData<Boolean>()


    fun delPost(id: Int){
        viewModelScope.launch {
            val response: Response<DefaultResponse> = repository.delPost(id)
            myResponseDefault.value = response
        }
    }

    fun putPost(id: Int, konu: String, detay: String, tamam: String){
        viewModelScope.launch {
            val response: Response<DefaultResponse> = repository.putPost(id,konu,detay,tamam)
            myResponseDefault.value = response
        }
    }


    fun pushPost(veriler: VerilerModel) {
        viewModelScope.launch {
            val response: Response<VerilerModel> = repository.pushPost(veriler)
            myResponse.value = response
        }
    }

    fun pushPost2(id: Int, konu: String, detay: String, tamam: String){
        viewModelScope.launch {
            val response: Response<DefaultResponse> = repository.pushPost2(id, konu, detay, tamam)
            myResponseDefault.value = response
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            val response: Response<List<VerilerModel>> = repository.getPosts()
            myResponseAll.value = response
        }
    }

    fun getPost(id: Int) {
        viewModelScope.launch {
            val response: Response<VerilerModel> = repository.getPost(id)
            myResponse.value = response
        }
    }

    fun getCustomPosts(id: Int, sort: String, order: String) {
        viewModelScope.launch {
            val response: Response<List<VerilerModel>> =
                repository.getCustomPosts(id, sort, order)
            myCustomPosts.value = response
        }
    }

    fun getCustomPosts2(id: Int, options: Map<String, String>) {
        viewModelScope.launch {
            val response: Response<List<VerilerModel>> = repository.getCustomPosts2(id, options)
            myCustomPosts2.value = response
        }
    }
}