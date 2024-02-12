package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.databinding.ItemPlaylistBinding

class PlaylistsAdapter(private var playlists: MutableList<Playlist>, private val listener: OnClickListener): RecyclerView.Adapter<PlaylistsAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlaylistBinding.bind(view)

        fun setListener(playlistEntity: Playlist) {
            with(binding.root) {
                setOnClickListener { listener.onClick(playlistEntity) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsAdapter.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = playlists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = playlists.get(position)

        with(holder) {
            setListener(playlist)

            binding.tvPlaylistName.text = playlist.titulo

            Glide.with(context)
                .load("https://cdn.icon-icons.com/icons2/3001/PNG/512/default_filetype_file_empty_document_icon_187718.png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.ivPlaylist)
        }
    }

    fun setPlaylists(playlists: List<Playlist>) {
        this.playlists = playlists as MutableList<Playlist>
        notifyDataSetChanged()
    }
}