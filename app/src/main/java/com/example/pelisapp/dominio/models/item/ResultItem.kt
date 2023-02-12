package com.example.pelisapp.dominio.models.item

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResultItem(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("genre_ids")
    val genero: List<Int?>?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val fechaLanzamientoPelicula: String?,
    @SerializedName("first_air_date")
    val fechaLanzamientoSerie: String?,
    @SerializedName("title")
    val tituloPelicula: String?,
    @SerializedName("name")
    val tituloSerie: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
) : Parcelable