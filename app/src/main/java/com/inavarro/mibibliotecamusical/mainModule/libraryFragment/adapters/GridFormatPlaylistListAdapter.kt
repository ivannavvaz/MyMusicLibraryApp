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
import com.inavarro.mibibliotecamusical.databinding.ItemPlaylistBinding
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.LibraryFragment
import kotlin.math.roundToInt

class GridFormatPlaylistListAdapter(private val listener: LibraryFragment):
    ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallBack()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlaylistBinding.bind(view)

        fun setListener(playlistEntity: Playlist) {
            with(binding.root) {
                setOnClickListener { listener.onClick(playlistEntity) }
                setOnLongClickListener { listener.onLongClick(playlistEntity)
                    true }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playlist = getItem(position)

        if (position == 0 || isEven(position)) {
            // Add padding 16dp to left items

            //val parentWidth = (R.layout.fragment_library as View).width
            //val rvWidth = ( View.).width
            //val margin = (parentWidth - rvWidth) / 2

            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins((16 * Resources.getSystem().displayMetrics.density).roundToInt(), 0, 0, 0)
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
                .into(binding.ivPlaylist)

            if (playlist.titulo.length > 11) {
                val titulo = playlist.titulo.substring(0, 11) + "..."
                binding.tvPlaylistName.text = titulo
            } else {
                binding.tvPlaylistName.text = playlist.titulo
            }
            binding.tvPlaylistUser.text = playlist.usuario.username

        }
    }

    private fun isEven(pos: Int): Boolean {

        return pos % 2 == 0
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