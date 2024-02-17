package com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.LibraryFragment
import kotlin.math.roundToInt

class ListFormatPlaylistListAdapter(private val listener: LibraryFragment):
    ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallBack()) {

        private lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding = ItemListBinding.bind(view)

            fun setListener(playlistEntity: Playlist) {
                with(binding.root) {
                    setOnClickListener { listener.onClick(playlistEntity) }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val playlist = getItem(position)

            if (position == 0) {
                // Add padding 16dp to the first item
                val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
                layoutParams.setMargins(0, (16 * Resources.getSystem().displayMetrics.density).roundToInt(), 0, 0)
                holder.itemView.layoutParams = layoutParams
            } else {
                val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
                layoutParams.setMargins(0, 0, 0, 0)
                holder.itemView.layoutParams = layoutParams
            }

            with(holder as ViewHolder) {
                setListener(playlist)

                Glide.with(context)
                    .load(Constants.DEFAULT_PLAYLIST_IMAGE)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.imageView)

                if (playlist.titulo.length > 50) {
                    val titulo = playlist.titulo.substring(0, 50) + "..."
                    binding.tvName.text = titulo
                } else {
                    binding.tvName.text = playlist.titulo
                }

                binding.tvType.text = playlist.usuario.username

            }
        }

        class PlaylistDiffCallBack: DiffUtil.ItemCallback<Playlist>() {
            override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
                return oldItem == newItem
            }
        }
    }
