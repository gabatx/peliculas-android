package com.example.pelisapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.databinding.FragmentListaBinding
import com.example.pelisapp.dominio.models.item.ResultItem
import com.example.pelisapp.ui.activity.MainActivity
import com.example.pelisapp.ui.adapter.AdapterListado
import com.example.pelisapp.utils.OConstantes
import com.example.pelisapp.viewmodel.ViewModelItems


class ListaFragment : Fragment() {

    private lateinit var binding: FragmentListaBinding
    private val viewModelItems: ViewModelItems by activityViewModels()
    private lateinit var adapterItems: AdapterListado

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListaBinding.inflate(inflater, container, false)
        //(requireActivity() as MainActivity).ocultarImagenToobar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Esto para decirle a la actividad que el menu lo va a poner el fragment
        setHasOptionsMenu(true)

        when (viewModelItems.tipoItem.value) {

            OConstantes.PELICULAS   -> {
                // Cambiar la etiqueta del fragment
                (activity as MainActivity).supportActionBar?.title = "Películas"
                // Cambiar el icono del fragment
                (activity as MainActivity).supportActionBar?.setIcon(R.drawable.filmreel)

                // Descargo toda la información en el viewModel
                viewModelItems.listaPeliculas()

                viewModelItems.listaPeliculas.observe(viewLifecycleOwner) { items ->
                    // Asigno el tipo de lista para que sepa que es una lista de películas
                    viewModelItems.tipoItem.value = OConstantes.PELICULAS
                    // Creo el adapter
                    viewModelItems.listaPeliculas.value?.let { peliculas ->
                        configuracionReyclerView(peliculas)
                    }
                    adapterItems.actualizarItems(items)
                }
            }

            OConstantes.SERIES      -> {
                // Cambiar la etiqueta del fragment
                (activity as MainActivity).supportActionBar?.title = "Series"
                // Cambiar el icono del fragment
                (activity as MainActivity).supportActionBar?.setIcon(R.drawable.tv)

                // Descargo toda la información en el viewModel
                viewModelItems.listaSeries()

                viewModelItems.listaSeries.observe(viewLifecycleOwner) { items ->
                    // Asigno el tipo de lista para que sepa que es una lista de películas
                    viewModelItems.tipoItem.value = OConstantes.SERIES
                    // Creo el adapter
                    viewModelItems.listaPeliculas.value?.let { series ->
                        configuracionReyclerView(series)
                    }
                    adapterItems.actualizarItems(items)
                }
            }

            OConstantes.RANKING -> {
                // Cambiar la etiqueta del fragment
                (activity as MainActivity).supportActionBar?.title = "Raking"
                // Cambiar el icono del fragment
                (activity as MainActivity).supportActionBar?.setIcon(R.drawable.oldcamera)

                // Descargo toda la información en el viewModel
                viewModelItems.listaRanking()

                viewModelItems.listaRanking.observe(viewLifecycleOwner) { items ->
                    // Asigno el tipo de lista para que sepa que es una lista de películas
                    viewModelItems.tipoItem.value = OConstantes.RANKING
                    // Creo el adapter
                    viewModelItems.listaRanking.value?.let { ranking ->
                        configuracionReyclerView(ranking)
                    }
                    adapterItems.actualizarItems(items)
                }
            }

            OConstantes.BUSQUEDA ->{
                // Observer que muestra el resultado de la busqueda:
                viewModelItems.palabraBuscada.observe(viewLifecycleOwner) { palabra ->
                    // Asigno el tipo de lista para que sepa que es una lista de películas
                    viewModelItems.tipoItem.value = OConstantes.BUSQUEDA
                    if (palabra.isNotEmpty()) {
                        Toast.makeText(requireContext(), palabra, Toast.LENGTH_SHORT).show()
                        viewModelItems.buscarItem(palabra)
                        viewModelItems.listaBusqueda.observe(viewLifecycleOwner) { items ->
                            // Creo el adapter
                            viewModelItems.listaBusqueda.value?.let { busqueda ->
                                configuracionReyclerView(busqueda)
                            }
                            adapterItems.actualizarItems(items)
                        }
                    }
                }
            }
        }

        viewModelItems.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun configuracionReyclerView(items: ArrayList<ResultItem>) {
        binding.recyclerViewListado.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        // El adapter carga el recyclerView
        adapterItems = AdapterListado(viewModelItems)
        items.let { adapterItems.actualizarItems(it) }
        binding.recyclerViewListado.adapter = adapterItems
    }
}