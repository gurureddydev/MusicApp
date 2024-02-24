package com.otpindiain.musicapp

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("id")
    val id: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("user_created")
    val userCreated: String,

    @SerializedName("date_created")
    val dateCreated: String,

    @SerializedName("user_updated")
    val userUpdated: String,

    @SerializedName("date_updated")
    val dateUpdated: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("accent")
    val accent: String,

    @SerializedName("cover")
    val cover: String,

    @SerializedName("top_track")
    val topTrack: Boolean,

    @SerializedName("url")
    val url: String
)
