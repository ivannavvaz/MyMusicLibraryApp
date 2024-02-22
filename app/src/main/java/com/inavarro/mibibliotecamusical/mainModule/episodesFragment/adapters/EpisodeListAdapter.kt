package com.inavarro.mibibliotecamusical.mainModule.episodesFragment.adapters

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
import com.inavarro.mibibliotecamusical.common.entities.Capitulo
import com.inavarro.mibibliotecamusical.databinding.ItemListBinding
import com.inavarro.mibibliotecamusical.mainModule.episodesFragment.EpisodesFragment
import kotlin.math.roundToInt

class EpisodeListAdapter(podcast: EpisodesFragment):
    ListAdapter<Capitulo, RecyclerView.ViewHolder>(PlaylistDiffCallBack()) {

        private lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding = ItemListBinding.bind(view)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val episode = getItem(position)

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

                var imageRoute = episode.podcast.titulo.replace(" ", "-").lowercase().replace("á", "a")
                    .replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
                imageRoute = "/img/podcast/$imageRoute.png"

                val storageRef = Firebase.storage.reference

                storageRef.child(imageRoute).downloadUrl.addOnSuccessListener {
                    Glide.with(context)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.imageView)
                }

                binding.tvName.text = episode.titulo
                binding.tvType.text = episode.descripcion

            }
        }

        class PlaylistDiffCallBack: DiffUtil.ItemCallback<Capitulo>() {
            override fun areItemsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
                return oldItem == newItem
            }
        }
}