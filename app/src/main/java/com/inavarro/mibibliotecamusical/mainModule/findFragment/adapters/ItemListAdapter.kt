package com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters

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
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import kotlin.math.roundToInt

class ItemListAdapter(private val listener: OnClickListener):
    ListAdapter<Any, RecyclerView.ViewHolder>(DiffCallBack()) {

    private lateinit var context: Context
    private lateinit var mBinging: ItemListBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListBinding.bind(view)

        fun setListener(entity: Any) {
            if (entity is com.inavarro.mibibliotecamusical.common.entities.Song) {
                val song = entity as com.inavarro.mibibliotecamusical.common.entities.Song
                with(binding.root) {
                    setOnClickListener { listener.onClick(song) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinging = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = getItem(position)

        if (position == 0) {
            // Add padding 16dp to the first item
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(0, (16 * Resources.getSystem().displayMetrics.density).roundToInt(),  0, 0)
            holder.itemView.layoutParams = layoutParams
        } else {
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(0, 0, 0, 0)
            holder.itemView.layoutParams = layoutParams
        }

        with(holder as ViewHolder) {
            setListener(entity)

            when (entity) {
                is com.inavarro.mibibliotecamusical.common.entities.Album -> {
                    // Cast entity to Album
                    val album = entity as com.inavarro.mibibliotecamusical.common.entities.Album
                    bindAlbum(album)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Podcast -> {
                    // Cast entity to Podcast
                    val podcast = entity as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    bindPodcast(podcast)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Song -> {
                    // Cast entity to Song
                    val song = entity as com.inavarro.mibibliotecamusical.common.entities.Song
                    bindSong(song)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Playlist -> {
                    // Cast entity to Playlist
                    val playlist = entity as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    bindPlaylist(playlist)
                }
            }
        }
    }

    private fun bindAlbum(album: com.inavarro.mibibliotecamusical.common.entities.Album) {
        with(mBinging) {
            tvName.text = album.titulo
            tvType.text = "Álbum"
        }

        Glide.with(context)
            .load(Constants.DEFAULT_PLAYLIST_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinging.imageView)
    }

    private fun bindPodcast(podcast: com.inavarro.mibibliotecamusical.common.entities.Podcast) {
        with(mBinging) {
            tvName.text = podcast.titulo
            tvType.text = "Podcast"
        }

        Glide.with(context)
            .load(Constants.DEFAULT_PODCAST_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinging.imageView)
    }

    private fun bindSong(song: com.inavarro.mibibliotecamusical.common.entities.Song) {
        with(mBinging) {
            tvName.text = song.titulo
            tvType.text = "Canción"
        }

        Glide.with(context)
            .load(Constants.DEFAULT_SONG_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinging.imageView)
    }

    private fun bindPlaylist(playlist: com.inavarro.mibibliotecamusical.common.entities.Playlist) {
        with(mBinging) {
            tvName.text = playlist.titulo
            tvType.text = "Playlist"
        }

        Glide.with(context)
            .load(Constants.DEFAULT_PLAYLIST_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinging.imageView)
    }

    class DiffCallBack: DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            //return oldItem == newItem
            return false
        }
    }
}