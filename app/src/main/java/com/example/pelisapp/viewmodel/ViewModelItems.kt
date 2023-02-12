package com.example.pelisapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisapp.dominio.models.generos.ResultGenre
import com.example.pelisapp.dominio.models.item.ResultItem
import com.example.pelisapp.dominio.retrofit.Repositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ViewModelItems: ViewModel() {

    private val repositorio = Repositorio()

    // Guarda el objeto que se ha pulsado
    var itemSeleccionado = MutableLiveData<ResultItem>()
    // Listados de películas, series, rankings...
    val listaPeliculas = MutableLiveData<ArrayList<ResultItem>>()
    val listaRanking = MutableLiveData<ArrayList<ResultItem>>()
    val listaSeries = MutableLiveData<ArrayList<ResultItem>>()
    val listaBusqueda = MutableLiveData<ArrayList<ResultItem>>()
    val listaGenero = MutableLiveData<ArrayList<ResultGenre>>()
    val listaPeliculasPorGenero = MutableLiveData<ArrayList<ResultItem>>()
    // Guarda el tipo de item que se ha seleccionado
    var tipoItem = MutableLiveData<String>()
    // Guarda la palabra clave que se ha buscado
    val palabraBuscada = MutableLiveData<String>()
    // Cuando se cambie mostrará en la actividad el tipo de error que se haya indicado
    val error = MutableLiveData<String>()
    // Oculta la imagen del toolbar al entrar en detalle
    val ocultaImagenToolbar = MutableLiveData<Boolean>()

    // ------  TODOS LAS PELÍCULAS  ----- ---

    fun listaPeliculas() {
        CoroutineScope(IO).launch {
            val respuesta = repositorio.listaTodasLasPeliculas()
            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaPeliculas.postValue(it?.results as ArrayList<ResultItem>? ?: ArrayList())
                }
            } else {
                error.postValue("Error al obtener las películas")
            }
        }
    }

    // ------  TODAS LAS SERIES  ----- ---

    fun listaSeries(){
        CoroutineScope(IO).launch {
            val respuesta = repositorio.listaTodasLasSeries()

            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaSeries.postValue(it?.results as ArrayList<ResultItem>? ?: ArrayList())
                }
            } else {
                error.postValue("Error al obtener las series")
            }
        }
    }

    // ------  RAKING  -------

    fun listaRanking(){
        CoroutineScope(IO).launch {
            val respuesta = repositorio.listaRanking()
            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaRanking.postValue(it?.results as ArrayList<ResultItem> )
                }
            } else {
                error.postValue("Error al recibir el ranking")
            }
        }
    }

    // ------  BUSQUEDA  -------

    fun buscarItem(palabra: String) {
        CoroutineScope(IO).launch {
            val respuesta = repositorio.buscarItem(palabra)
            println("Entra")
            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaBusqueda.postValue(it?.results as ArrayList<ResultItem>? ?: ArrayList())
                }
            } else {
                error.postValue("Error al buscar la película")
            }
        }
    }

    //-----  Géneros  --------
    fun allGen(){
        CoroutineScope(IO).launch {
            val respuesta = repositorio.listageneros()
            println("Algo hace")
            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaGenero.postValue(it?.genres as ArrayList<ResultGenre>? ?: ArrayList())
                }
            } else {
                error.postValue("Error al recibir el Género")
            }
        }
    }

    //-----  Películas género  --------

    fun listaPeliculasGenero(id: Int) {
        CoroutineScope(IO).launch {
            val respuesta = repositorio.listadoPeliculaGenero(id)
            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaPeliculasPorGenero.postValue(it?.results as ArrayList<ResultItem>? ?: ArrayList())
                }
            } else {
                error.postValue("Error al devolver listado de películas por género")
            }
        }
    }
}