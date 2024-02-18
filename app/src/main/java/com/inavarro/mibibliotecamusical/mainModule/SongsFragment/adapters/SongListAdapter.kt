package com.inavarro.mibibliotecamusical.mainModule.SongsFragment.adapters

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
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import kotlin.math.roundToInt

class SongListAdapter(private val listener: OnClickListener):
    ListAdapter<Song, RecyclerView.ViewHolder>(PlaylistDiffCallBack()) {

        private lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding = ItemListBinding.bind(view)

            fun setListener(song: Song) {
                with(binding.root) {
                    setOnClickListener { listener.onClick(song) }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val song = getItem(position)

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
                setListener(song)

                Glide.with(context)
                    .load(Constants.DEFAULT_PLAYLIST_IMAGE)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.imageView)

                if (song.titulo.length > 50) {
                    val titulo = song.titulo.substring(0, 50) + "..."
                    binding.tvName.text = titulo
                } else {
                    binding.tvName.text = song.titulo
                }

                binding.tvType.text = song.album.artista.nombre

            }
        }

        class PlaylistDiffCallBack: DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }
        }
    }
