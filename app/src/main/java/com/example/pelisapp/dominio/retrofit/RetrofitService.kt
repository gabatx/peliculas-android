package com.example.pelisapp.dominio.retrofit

import com.example.pelisapp.dominio.models.generos.Generos
import com.example.pelisapp.dominio.models.item.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

// https://api.themoviedb.org/3/
const val TOKEN = "5bca4916212fdace5198cbc5984023c5"

interface RetrofitService {
    // RECIBIR TODOS LAS PELÍCULAS
    @GET("movie/popular?api_key=$TOKEN&language=es-ES")
    suspend fun listaTodasLasPeliculas() :Response <Item>

    // RECIBIR TODOS LAS SERIES
    @GET("tv/popular?api_key=$TOKEN&language=es-ES")
    suspend fun listaTodasLasSeries(): Response <Item>

    // RECIBIR RANKING
    @GET("movie/top_rated?api_key=$TOKEN&language=es-ES")
    suspend fun listaRanking(): Response <Item>

    // Busqueda de Item
    @GET("search/movie?api_key=$TOKEN&language=es-ES")
    suspend fun buscarItem(@Query("query") palabra: String): Response <Item>

    // RECIBIR LISTA DE GENEROS
    @GET("genre/list?api_key=$TOKEN&language=es-ES")
    suspend fun listageneros(): Response <Generos>

    // RECIBIR ITEMS POR GÉNERO
    @GET("movie/{id}/similar?api_key=$TOKEN&language=es-ES")
    suspend fun listadoPeliculaGenero(@Path("id") id: Int): Response <Item>


}