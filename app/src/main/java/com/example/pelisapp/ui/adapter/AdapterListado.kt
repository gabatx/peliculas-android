package com.example.pelisapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisapp.R
import com.example.pelisapp.databinding.HolderListadoBinding
import com.example.pelisapp.dominio.models.item.ResultItem
import com.example.pelisapp.utils.OConstantes
import com.example.pelisapp.viewmodel.ViewModelItems

class AdapterListado (private val viewModelItems: ViewModelItems) : RecyclerView.Adapter<AdapterListado.ViewHolderListado>()  {

    private var listado = ArrayList<ResultItem>()

    inner class ViewHolderListado(val binding: HolderListadoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListado.ViewHolderListado {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderListadoBinding.inflate(layoutInflater, parent, false)
        return ViewHolderListado(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderListado, position: Int) {
       val item = listado[position]

        rellenaDatos(holder, item)

        holder.binding.contentHolderListado.setOnClickListener {
            viewModelItems.itemSeleccionado.value = item
            it.findNavController().navigate(R.id.action_listaFragment_to_itemDetalleFragment)
            // Oculta la imagen del toolbar al entrar en detalle
            viewModelItems.ocultaImagenToolbar.value = true
        }
    }

    // Rellena los datos del item
    private fun rellenaDatos(holder: ViewHolderListado, item: ResultItem) {
        // Dependiendo del tipo de item, se carga los datos en el holder
        when (viewModelItems.tipoItem.value) {
            OConstantes.PELICULAS -> {
                holder.binding.tituloListadoHolder.text = item.tituloPelicula
                holder.binding.ratinBarListadoHolder.rating = item.voteAverage!!.toFloat() / 2
            }
            OConstantes.SERIES    -> {
                holder.binding.tituloListadoHolder.text = item.tituloSerie
                holder.binding.ratinBarListadoHolder.rating = item.voteAverage!!.toFloat() / 2
            }

            OConstantes.RANKING -> {
                holder.binding.tituloListadoHolder.text = item.tituloPelicula
                holder.binding.ratinBarListadoHolder.rating = item.voteAverage!!.toFloat() / 2
            }
            OConstantes.BUSQUEDA -> {
                holder.binding.tituloListadoHolder.text = item.tituloPelicula
                holder.binding.ratinBarListadoHolder.rating = item.voteAverage!!.toFloat() / 2
            }
        }

        holder.binding.descripcionItemHolder.text = descripcionRecortada(item.overview)

        val imagen = "https://image.tmdb.org/t/p/w500" + item.posterPath
        Glide.with(holder.itemView.context)
            .load(imagen)
            .into(holder.binding.imagenListadoHolder)
    }

    private fun descripcionRecortada(overview: String?): CharSequence? {
        return if (overview != null && overview.length > 100) {
            overview.substring(0, 100) + "..."
        } else {
            overview
        }
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