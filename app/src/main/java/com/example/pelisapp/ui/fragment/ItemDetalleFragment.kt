package com.example.pelisapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.pelisapp.databinding.FragmentItemDetalleBinding
import com.example.pelisapp.ui.activity.MainActivity
import com.example.pelisapp.viewmodel.ViewModelItems


class ItemDetalleFragment : Fragment() {

    private lateinit var binding: FragmentItemDetalleBinding
    private val viewModelItems: ViewModelItems by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentItemDetalleBinding.inflate(inflater, container, false)
        //(requireActivity() as MainActivity).ocultarImagenToobar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rellenarDatos()
    }

    private fun rellenarDatos() {
        if (viewModelItems.itemSeleccionado.value?.tituloPelicula != null) {
            binding.tituloItemDetalle.text = viewModelItems.itemSeleccionado.value?.tituloPelicula
            binding.fecha.text = viewModelItems.itemSeleccionado.value?.fechaLanzamientoPelicula
        }

        if (viewModelItems.itemSeleccionado.value?.tituloSerie != null) {
            binding.tituloItemDetalle.text = viewModelItems.itemSeleccionado.value?.tituloSerie
            binding.fecha.text = viewModelItems.itemSeleccionado.value?.fechaLanzamientoSerie
        }

        binding.textoresumen.text = viewModelItems.itemSeleccionado.value?.overview

        val imagenFondo = "https://image.tmdb.org/t/p/w500" + viewModelItems.itemSeleccionado.value?.backdropPath
        val imagenItem = "https://image.tmdb.org/t/p/w500" + viewModelItems.itemSeleccionado.value?.posterPath

        // Imagen de fondo
        Glide.with(this)
            .load(imagenFondo)
            .into(binding.imagenFondoDetalle)

        // Imagen de la pelicula
        Glide.with(this)
            .load(imagenItem)
            .into(binding.imagenItemDetalle)
    }
}
