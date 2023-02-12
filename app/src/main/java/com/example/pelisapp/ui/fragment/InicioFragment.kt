package com.example.pelisapp.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.databinding.FragmentInicioBinding
import com.example.pelisapp.dominio.models.item.ResultItem
import com.example.pelisapp.ui.activity.MainActivity
import com.example.pelisapp.ui.adapter.AdapterInicio
import com.example.pelisapp.utils.OConstantes
import com.example.pelisapp.viewmodel.ViewModelItems

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding
    private lateinit var adapter: AdapterInicio

    private val viewModelItems: ViewModelItems by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(requireActivity() as MainActivity).muestraImagenToolbar()


        viewModelItems.listaPeliculas()
        viewModelItems.listaSeries()
        viewModelItems.listaRanking()
        viewModelItems.allGen()


        viewModelItems.listaGenero.observe(viewLifecycleOwner) { items ->
            // Recogemos las ids de los géneros deseados
            val generosSpinner = items.filter {
                it.name == "Acción" || it.name == "Comedia" || it.name == "Animación" || it.name == "Terror" || it.name == "Fantasía"
            }

            // Carga el spinner
            val spinner = ArrayAdapter.createFromResource(requireContext(), R.array.Generos, R.layout.spinner_color)
            binding.spinnerPeliculas.adapter = spinner
            spinner.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)

            binding.spinnerPeliculas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (binding.spinnerPeliculas.selectedItem.toString()) {
                        OConstantes.POPULARES -> {
                            configuracionReyclerView(viewModelItems.listaPeliculas.value!!, OConstantes.PELICULAS)
                            adapter.actualizarItems(viewModelItems.listaPeliculas.value!!)
                        }
                        OConstantes.ACCION    -> {
                            // Llamada
                            viewModelItems.listaPeliculasGenero(seleccionaId(OConstantes.ACCION)!!)
                        }
                        OConstantes.COMEDIA   -> {
                            viewModelItems.listaPeliculasGenero(seleccionaId(OConstantes.COMEDIA)!!)
                        }
                        OConstantes.ANIMACION -> {
                            viewModelItems.listaPeliculasGenero(seleccionaId(OConstantes.ANIMACION)!!)
                        }
                        OConstantes.TERROR    -> {
                            viewModelItems.listaPeliculasGenero(seleccionaId(OConstantes.TERROR)!!)
                        }
                        OConstantes.FANTASIA  -> {
                            viewModelItems.listaPeliculasGenero(seleccionaId(OConstantes.FANTASIA)!!)
                        }
                    }
                }

                // Función que devuelve el id del g´´enero seleccionado:
                private fun seleccionaId(id: String): Int? {
                    return generosSpinner.filter { it.name == id }.filter { it.id == it.id }[0].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "No hay nada seleccinado", Toast.LENGTH_SHORT).show()
                }

            }

            // Observe
            viewModelItems.listaPeliculasPorGenero.observe(viewLifecycleOwner) { listado ->
                configuracionReyclerView(listado, OConstantes.PELICULAS)
                adapter.actualizarItems(listado)
            }
        }

        viewModelItems.listaPeliculas.observe(viewLifecycleOwner) { items ->
            // Creo el adapter
            viewModelItems.listaPeliculas.value?.let { peliculas ->
                configuracionReyclerView(items, OConstantes.PELICULAS)
            }
            adapter.actualizarItems(items)
        }

        viewModelItems.listaSeries.observe(viewLifecycleOwner) { items ->
            // Creo el adapter
            viewModelItems.listaSeries.value?.let { series ->
                configuracionReyclerView(series, OConstantes.SERIES)
            }
            adapter.actualizarItems(items)
        }

        viewModelItems.listaRanking.observe(viewLifecycleOwner) { items ->
            // Asigno el tipo de lista para que sepa que es una lista de series
            viewModelItems.tipoItem.value = OConstantes.RANKING
            // Creo el adapter
            viewModelItems.listaRanking.value?.let { ranking ->
                configuracionReyclerView(ranking, OConstantes.RANKING)
            }
            adapter.actualizarItems(items)
        }

        viewModelItems.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun configuracionReyclerView(items: ArrayList<ResultItem>, type: String) {

        val llm = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val recyclerMovies = binding.RecyclerViewMovies
        val recyclerSeries = binding.RecyclerViewSeries
        val recyclerRanking = binding.RecyclerViewRanking

        // Dependiendo del tipo de lista que se esté mostrando, se configura el recyclerView
        when (type) {
            OConstantes.PELICULAS -> {
                recyclerMovies.layoutManager = llm

                CargaRecicler(items)

                recyclerMovies.adapter = adapter
            }

            OConstantes.SERIES    -> {
                recyclerSeries.layoutManager = llm

                CargaRecicler(items)

                recyclerSeries.adapter = adapter
            }

            OConstantes.RANKING   -> {
                recyclerRanking.layoutManager = llm

                CargaRecicler(items)

                recyclerRanking.adapter = adapter
            }
        }
    }

    // El adapter carga el recyclerView
    private fun CargaRecicler(items: ArrayList<ResultItem>) {
        adapter = AdapterInicio(viewModelItems)
        items.let { adapter.actualizarItems(it) }
    }

}

