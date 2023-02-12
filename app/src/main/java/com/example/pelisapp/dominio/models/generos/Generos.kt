package com.example.pelisapp.dominio.models.generos


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Generos(
    @SerializedName("genres")
    val genres: List<ResultGenre>?
) : Parcelable