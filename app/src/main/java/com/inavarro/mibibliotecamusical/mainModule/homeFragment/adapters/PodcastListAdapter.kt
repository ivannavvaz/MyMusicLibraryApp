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
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.databinding.ItemPodcastBinding
import kotlin.math.roundToInt

class PodcastListAdapter(private val listener: OnClickListener):
    ListAdapter<Podcast, RecyclerView.ViewHolder>(PodcastDiffCallBack()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPodcastBinding.bind(view)

        fun setListener(podcastEntity: Podcast) {
            with(binding.root) {
                setOnClickListener { listener.onClick(podcastEntity) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_podcast, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val podcast = getItem(position)

        with(holder as ViewHolder) {
            setListener(podcast)

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

            binding.tvPodcastName.text = podcast.titulo

            var imageRoute = podcast.titulo.replace(" ", "-").lowercase().replace("á", "a")
                .replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            imageRoute = "/img/podcast/$imageRoute.png"


            val storage = Firebase.storage
            val storageRef = storage.reference

            storageRef.child(imageRoute).downloadUrl.addOnSuccessListener {
                Glide.with(context)
                    .load(it)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.ivPodcast)
            }.addOnFailureListener {
                Glide.with(context)
                    .load(Constants.DEFAULT_PODCAST_IMAGE)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.ivPodcast)
            }

        }
    }

    class PodcastDiffCallBack: DiffUtil.ItemCallback<Podcast>() {
        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem == newItem
        }
    }
}