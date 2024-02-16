package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.databinding.ItemAlbumBinding
import kotlin.math.roundToInt

class AlbumListAdapter(private val listener: OnClickListener):
    ListAdapter<Album, RecyclerView.ViewHolder>(AlbumDiffCallBack()) {

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = getItem(position)

        if (position == 0) {
            // Add padding 16dp to the first item
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins((16 * Resources.getSystem().displayMetrics.density).roundToInt(), 0, 0, 0)
            holder.itemView.layoutParams = layoutParams
        } else {
           val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(0, 0, 0, 0)
            holder.itemView.layoutParams = layoutParams
        }

        with(holder as ViewHolder) {
            setListener(album)

            if (album.titulo.length > 14) {
                val albumTitle = album.titulo.substring(0, 14).trim() + "..."
                binding.tvAlbumName.text = albumTitle
            } else {
                binding.tvAlbumName.text = album.titulo.trim()
            }

            if (album.artista.nombre.length > 24) {
                val artistName = album.artista.nombre.substring(0, 24).trim() + "..."
                binding.tvArtistName.text = artistName
            } else {
                binding.tvArtistName.text = album.artista.nombre.trim()
            }

            val storage = Firebase.storage
            val storageRef = storage.reference

            storageRef.child(album.imagen).downloadUrl.addOnSuccessListener {
                Glide.with(context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.ivAlbum)
            }.addOnFailureListener {
                Glide.with(context)
                    .load(Constants.DEFAULT_ALBUM_IMAGE)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.ivAlbum)
            }
        }
    }

    class AlbumDiffCallBack: DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}