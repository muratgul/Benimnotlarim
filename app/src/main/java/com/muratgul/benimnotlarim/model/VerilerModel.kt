package com.muratgul.benimnotlarim.model

import com.google.gson.annotations.SerializedName

class VerilerModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("konu")
    val konu: String,
    @SerializedName("detay")
    val detay: String,
    @SerializedName("tamam")
    val tamam: String
)