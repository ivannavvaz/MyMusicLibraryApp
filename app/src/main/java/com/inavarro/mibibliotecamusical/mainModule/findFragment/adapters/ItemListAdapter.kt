package com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters

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
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import kotlin.math.roundToInt

class ItemListAdapter(private val listener: OnClickListener):
    ListAdapter<Any, RecyclerView.ViewHolder>(DiffCallBack()) {

    private lateinit var context: Context
    private val storageRef = Firebase.storage.reference

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
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        //mBinding = ItemListBinding.bind(view)

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

        Log.e("ItemListAdapter", "onBindViewHolder: $entity")

        with(holder as ViewHolder) {
            setListener(entity)

            when (entity) {
                is com.inavarro.mibibliotecamusical.common.entities.Album -> {
                    Log.d("ItemListAdapter", "onBindViewHolder: Album")
                    // Cast entity to Album
                    val album = entity as com.inavarro.mibibliotecamusical.common.entities.Album
                    bindAlbum(album, binding)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Podcast -> {
                    Log.d("ItemListAdapter", "onBindViewHolder: Podcast")
                    // Cast entity to Podcast
                    val podcast = entity as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    bindPodcast(podcast, binding)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Song -> {
                    Log.d("ItemListAdapter", "onBindViewHolder: Song")
                    // Cast entity to Song
                    val song = entity as com.inavarro.mibibliotecamusical.common.entities.Song
                    bindSong(song, binding)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Playlist -> {
                    Log.d("ItemListAdapter", "onBindViewHolder: Playlist")
                    // Cast entity to Playlist
                    val playlist = entity as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    bindPlaylist(playlist, binding)
                }
            }
        }
    }

    private fun bindAlbum(album: com.inavarro.mibibliotecamusical.common.entities.Album, mBinding: ItemListBinding) {
        with(mBinding) {
            tvName.text = album.titulo
            tvType.text = "Álbum"
        }

        setImage(album.imagen, mBinding, Constants.DEFAULT_ALBUM_IMAGE)

    }

    private fun bindPodcast(podcast: com.inavarro.mibibliotecamusical.common.entities.Podcast, mBinding: ItemListBinding) {
        with(mBinding) {
            tvName.text = podcast.titulo
            tvType.text = "Podcast"
        }

        var imageRoute = podcast.titulo.replace(" ", "-").lowercase().replace("á", "a")
            .replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
        imageRoute = "/img/podcast/$imageRoute.png"

        setImage(imageRoute, mBinding, Constants.DEFAULT_PODCAST_IMAGE)
    }

    private fun bindSong(song: com.inavarro.mibibliotecamusical.common.entities.Song, mBinding: ItemListBinding) {
        with(mBinding) {
            tvName.text = song.titulo
            tvType.text = "Canción"
        }

        setImage("", mBinding, Constants.DEFAULT_SONG_IMAGE)
    }

    private fun bindPlaylist(playlist: com.inavarro.mibibliotecamusical.common.entities.Playlist, mBinding: ItemListBinding) {
        with(mBinding) {
            tvName.text = playlist.titulo
            tvType.text = "Playlist"
        }

        setImage("", mBinding, Constants.DEFAULT_PLAYLIST_IMAGE)
    }

    private fun setImage(imageRoute: String, mBinding: ItemListBinding, fallbackImage: String) {

        if (imageRoute.isEmpty()) {
            Glide.with(context)
                .load(fallbackImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imageView)
            return
        }

        storageRef.child(imageRoute).downloadUrl.addOnSuccessListener {
            Glide.with(context)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imageView)
        }.addOnFailureListener {
            Glide.with(context)
                .load(fallbackImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imageView)
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {

            when (oldItem) {
                is com.inavarro.mibibliotecamusical.common.entities.Album -> {
                    val oldAlbum = oldItem as com.inavarro.mibibliotecamusical.common.entities.Album
                    val newAlbum = newItem as com.inavarro.mibibliotecamusical.common.entities.Album
                    return oldAlbum.id == newAlbum.id
                }
                is com.inavarro.mibibliotecamusical.common.entities.Podcast -> {
                    val oldPodcast = oldItem as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    val newPodcast = newItem as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    return oldPodcast.id == newPodcast.id
                }
                is com.inavarro.mibibliotecamusical.common.entities.Song -> {
                    val oldSong = oldItem as com.inavarro.mibibliotecamusical.common.entities.Song
                    val newSong = newItem as com.inavarro.mibibliotecamusical.common.entities.Song
                    return oldSong.id == newSong.id
                }
                is com.inavarro.mibibliotecamusical.common.entities.Playlist -> {
                    val oldPlaylist = oldItem as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    val newPlaylist = newItem as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    return oldPlaylist.id == newPlaylist.id
                }
            }

            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {

            when (oldItem) {
                is com.inavarro.mibibliotecamusical.common.entities.Album -> {
                    val oldAlbum = oldItem as com.inavarro.mibibliotecamusical.common.entities.Album
                    val newAlbum = newItem as com.inavarro.mibibliotecamusical.common.entities.Album
                    return oldAlbum == newAlbum
                }
                is com.inavarro.mibibliotecamusical.common.entities.Podcast -> {
                    val oldPodcast = oldItem as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    val newPodcast = newItem as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    return oldPodcast == newPodcast
                }
                is com.inavarro.mibibliotecamusical.common.entities.Song -> {
                    val oldSong = oldItem as com.inavarro.mibibliotecamusical.common.entities.Song
                    val newSong = newItem as com.inavarro.mibibliotecamusical.common.entities.Song
                    return oldSong == newSong
                }
                is com.inavarro.mibibliotecamusical.common.entities.Playlist -> {
                    val oldPlaylist = oldItem as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    val newPlaylist = newItem as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    return oldPlaylist == newPlaylist
                }
            }
            return false
        }
    }
}