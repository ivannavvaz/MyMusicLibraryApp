package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

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
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.databinding.ItemFirstElementBinding
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.HomeFragment
import kotlin.math.roundToInt

class firstElementsListAdapter(private val listener: HomeFragment):
    ListAdapter<Any, RecyclerView.ViewHolder>(DiffCallBack()) {

    private lateinit var context: Context
    private val storageRef = Firebase.storage.reference

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFirstElementBinding.bind(view)

        fun setListener(entity: Any) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_first_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = getItem(position)

        with(holder as ViewHolder) {
            setListener(entity)

            when (entity) {
                is com.inavarro.mibibliotecamusical.common.entities.Album -> {
                    // Cast entity to Album
                    val album = entity as com.inavarro.mibibliotecamusical.common.entities.Album
                    bindAlbum(album, binding)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Podcast -> {
                    // Cast entity to Podcast
                    val podcast = entity as com.inavarro.mibibliotecamusical.common.entities.Podcast
                    bindPodcast(podcast, binding)
                }
                is com.inavarro.mibibliotecamusical.common.entities.Playlist -> {
                    // Cast entity to Playlist
                    val playlist = entity as com.inavarro.mibibliotecamusical.common.entities.Playlist
                    bindPlaylist(playlist, binding)
                }
            }
        }
    }
    private fun bindAlbum(album: com.inavarro.mibibliotecamusical.common.entities.Album, mBinding: ItemFirstElementBinding) {
        with(mBinding) {
            tvName.text = album.titulo
        }

        setImage(album.imagen, mBinding, Constants.DEFAULT_ALBUM_IMAGE)

    }

    private fun bindPodcast(podcast: com.inavarro.mibibliotecamusical.common.entities.Podcast, mBinding: ItemFirstElementBinding) {
        with(mBinding) {
            tvName.text = podcast.titulo
        }

        var imageRoute = podcast.titulo.replace(" ", "-").lowercase().replace("á", "a")
            .replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
        imageRoute = "/img/podcast/$imageRoute.png"

        setImage(imageRoute, mBinding, Constants.DEFAULT_PODCAST_IMAGE)
    }

    private fun bindPlaylist(playlist: com.inavarro.mibibliotecamusical.common.entities.Playlist, mBinding: ItemFirstElementBinding) {

        var titulo = playlist.titulo
        var image = Constants.DEFAULT_PLAYLIST_IMAGE

        // Replace "_" with " " in the title of the playlist if it is not the favorite playlist
        // If it is the favorite playlist, the title is "Canciones que te gustan"
        if (titulo != "favorita_1") {
            titulo = titulo.replace("lista_", "")
            titulo = titulo.replace("_", " ")
            titulo = titulo[0].uppercase() + titulo.substring(1)
        } else {
            image = Constants.FAVORITE_PLAYLIST_IMAGE
            titulo = "Canciones que te gustan"
        }

        mBinding.tvName.text = titulo

        Glide.with(context)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.imageView)

    }

    private fun setImage(imageRoute: String, mBinding: ItemFirstElementBinding, fallbackImage: String) {

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

    fun clearList() {
        submitList(null)
    }

    // Limit the number of items to 8
    override fun getItemCount(): Int {
        val limit = 8
        return if (currentList.size > limit) limit else currentList.size
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