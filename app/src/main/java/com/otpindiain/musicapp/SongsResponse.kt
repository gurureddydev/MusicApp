package com.otpindiain.musicapp

import com.google.gson.annotations.SerializedName

data class SongsResponse(
    @SerializedName("data")
    val data: List<Song>
)
