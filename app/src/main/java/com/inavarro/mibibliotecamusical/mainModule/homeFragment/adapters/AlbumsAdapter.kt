package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inavarro.mibibliotecamusical.common.entities.Album

class AlbumsAdapter(private var albums: MutableList<Album>, private val listener: OnClickListener): RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}