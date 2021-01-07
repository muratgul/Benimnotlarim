package com.muratgul.benimnotlarim.repository

import com.muratgul.benimnotlarim.api.DefaultResponse
import com.muratgul.benimnotlarim.api.RetrofitInstance
import com.muratgul.benimnotlarim.model.VerilerModel
import retrofit2.Response

class Repository {

    suspend fun getPosts(): Response<List<VerilerModel>> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun getPost(id: Int): Response<VerilerModel> {
        return RetrofitInstance.api.getPost(id)
    }

    suspend fun getCustomPosts(id: Int, sort: String, order: String): Response<List<VerilerModel>> {
        return RetrofitInstance.api.getCustomPost(id, sort, order)
    }

    suspend fun getCustomPosts2(id: Int, options: Map<String, String>) : Response<List<VerilerModel>> {
        return RetrofitInstance.api.getCustomPost2(id, options)
    }

    suspend fun pushPost(veriler: VerilerModel) : Response<VerilerModel> {
        return RetrofitInstance.api.pushPost(veriler)
    }

    suspend fun pushPost2(id: Int, konu: String, detay: String, tamam: String): Response<DefaultResponse> {
        return RetrofitInstance.api.pushPost2(id, konu, detay, tamam)
    }

    suspend fun putPost(id: Int, konu: String, detay: String, tamam: String): Response<DefaultResponse> {
        return RetrofitInstance.api.putPost(id, konu, detay, tamam)
    }

    suspend fun delPost(id: Int): Response<DefaultResponse>{
        return RetrofitInstance.api.delPost(id)
    }
}