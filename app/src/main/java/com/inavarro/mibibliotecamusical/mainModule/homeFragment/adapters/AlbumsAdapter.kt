package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.databinding.ItemAlbumBinding

class AlbumsAdapter(private var albums: MutableList<Album>, private val listener: OnClickListener): RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAlbumBinding.bind(view)

        fun setListener(albumEntity: Album) {
            with(binding.root) {
                setOnClickListener { listener.onClick(albumEntity) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums.get(position)

        with(holder) {
            setListener(album)

            binding.tvAlbumName.text = album.titulo

            Glide.with(context)
                .load(album.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.ivAlbum)
        }
    }

    fun setAlbums(albums: List<Album>) {
        this.albums = albums as MutableList<Album>
        notifyDataSetChanged()
    }
}