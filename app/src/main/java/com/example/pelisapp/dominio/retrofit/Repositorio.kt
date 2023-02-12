package com.example.pelisapp.dominio.retrofit


class Repositorio {

    private val retrofitServiceTodasPeliculas = RetrofitHelper.getRetrofit()
    private val retrofitServiceTodasSeries = RetrofitHelper.getRetrofit()
    private val retrofitServiceRanking = RetrofitHelper.getRetrofit()
    private val retrofitServiceBusqueda = RetrofitHelper.getRetrofit()
    private val retrofitServiceListaGeneros = RetrofitHelper.getRetrofit()
    private val retrofitServiceListaPeliculasGenero = RetrofitHelper.getRetrofit()

    // TODOS LAS PELÍCULAS
    suspend fun listaTodasLasPeliculas() = retrofitServiceTodasPeliculas.listaTodasLasPeliculas()
    // TODOS LAS SERIES
    suspend fun listaTodasLasSeries() = retrofitServiceTodasSeries.listaTodasLasSeries()
    // TODAS LAS TENDENCIAS
    suspend fun listaRanking() = retrofitServiceRanking.listaRanking()
    // BUSCAR PELÍCULAS
    suspend fun buscarItem(query: String) = retrofitServiceBusqueda.buscarItem(query)
    //TODOS LOS GENEROS
    suspend fun listageneros() = retrofitServiceListaGeneros.listageneros()
    // LISTADO PELÍCULAS POR GÉNERO
    suspend fun listadoPeliculaGenero(id: Int) = retrofitServiceListaPeliculasGenero.listadoPeliculaGenero(id)
}
