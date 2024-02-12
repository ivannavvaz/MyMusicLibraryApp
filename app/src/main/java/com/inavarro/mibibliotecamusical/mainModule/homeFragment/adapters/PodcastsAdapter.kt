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
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.databinding.ItemPodcastBinding

class PodcastsAdapter(private var podcasts: MutableList<Podcast>, private val listener: OnClickListener): RecyclerView.Adapter<PodcastsAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPodcastBinding.bind(view)

        fun setListener(podcastEntity: Podcast) {
            with(binding.root) {
                setOnClickListener { listener.onClick(podcastEntity) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastsAdapter.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_podcast, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val podcast = podcasts.get(position)

        with(holder) {
            setListener(podcast)

            binding.tvPodcastName.text = podcast.titulo

            Glide.with(context)
                .load(podcast.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.ivPodcast)
        }
    }

    fun setPodcasts(podcasts: List<Podcast>) {
        this.podcasts = podcasts as MutableList<Podcast>
        notifyDataSetChanged()
    }
}