package com.example.pelisapp.dominio.models.item


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class Item(
        @SerializedName("page")
    val page: Int?,
        @SerializedName("results")
    val results: List<ResultItem>?,
        @SerializedName("total_pages")
    val totalPages: Int?,
        @SerializedName("total_results")
    val totalResults: Int?
) : Parcelable