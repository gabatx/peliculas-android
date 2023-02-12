package com.example.pelisapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisapp.R
import com.example.pelisapp.databinding.HolderItemInicioBinding
import com.example.pelisapp.dominio.models.item.ResultItem
import com.example.pelisapp.viewmodel.ViewModelItems

class AdapterInicio (val viewModelItems: ViewModelItems) : RecyclerView.Adapter<AdapterInicio.ViewHolderListado>()  {

    private var listado = ArrayList<ResultItem>()

    inner class ViewHolderListado(val binding:HolderItemInicioBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListado {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderItemInicioBinding.inflate(layoutInflater, parent, false)
        return ViewHolderListado(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderListado, position: Int) {

        val item = listado[position]

        // Introduzco el título de la película o de la serie
        if (item.tituloPelicula != null) holder.binding.tituloHolder.text = item.tituloPelicula
        if (item.tituloSerie != null) holder.binding.tituloHolder.text = item.tituloSerie
        // Introduzco la fecha de la película o de la serie
        if (item.fechaLanzamientoPelicula != null) holder.binding.fechaHolder.text = item.fechaLanzamientoPelicula
        if (item.fechaLanzamientoSerie != null) holder.binding.fechaHolder.text = item.fechaLanzamientoSerie



        holder.binding.contentItem.setOnClickListener {
            viewModelItems.ocultaImagenToolbar.value = true
            viewModelItems.itemSeleccionado.value = item
            it.findNavController().navigate(R.id.action_fragmentInicio_to_ItemDetalleFragment)
        }

        val imagen = "https://image.tmdb.org/t/p/w500" + item.posterPath
        Glide.with(holder.itemView.context)
            .load(imagen)
            .into(holder.binding.imagenHolder)

        holder.binding.ratingBar.rating = listado[position].voteAverage!!.toFloat() / 2
    }

    override fun getItemCount(): Int {
        return listado.size
    }

    fun actualizarItems(listaFavoritosActualizada: ArrayList<ResultItem>?) {
        listado.clear()
        listado.addAll(listaFavoritosActualizada!!)
        notifyDataSetChanged()
    }
}