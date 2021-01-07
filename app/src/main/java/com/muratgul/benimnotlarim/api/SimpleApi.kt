package com.muratgul.benimnotlarim.api

import com.muratgul.benimnotlarim.model.VerilerModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    @Headers(
        "Authorization: 1234566660",
        "Platform: Android"
    )
    @GET("data")
    suspend fun getPosts(): Response<List<VerilerModel>>

    @DELETE("data/{id}")
    suspend fun delPost(@Path("id") id: Int): Response<DefaultResponse>

    @GET("data/{id}")
    suspend fun getPost(
        @Path("id") id: Int
    ): Response<VerilerModel>

    @GET("data")
    suspend fun getCustomPost(
        @Query("id") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<VerilerModel>>

    @GET("data")
    suspend fun getCustomPost2(
        @Query("id") id: Int,
        @QueryMap options: Map<String, String>
    ): Response<List<VerilerModel>>

    @POST("data")
    suspend fun pushPost(
        @Body veriler: VerilerModel
    ): Response<VerilerModel>

    @FormUrlEncoded
    @POST("data")
    suspend fun pushPost2(
        @Field("id") id: Int,
        @Field("konu") konu: String,
        @Field("detay") detay: String,
        @Field("tamam") tamam: String
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @PUT("data/{id}")
    suspend fun putPost(
        @Path("id") id: Int,
        @Field("konu") konu: String,
        @Field("detay") detay: String,
        @Field("tamam") tamam: String
    ): Response<DefaultResponse>


}