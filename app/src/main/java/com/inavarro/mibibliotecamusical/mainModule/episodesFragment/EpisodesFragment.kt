package com.inavarro.mibibliotecamusical.mainModule.episodesFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.databinding.FragmentEpisodesBinding
import com.inavarro.mibibliotecamusical.mainModule.episodesFragment.adapters.EpisodeListAdapter
import com.inavarro.mibibliotecamusical.mainModule.episodesFragment.service.EpisodesService
import com.inavarro.mibibliotecamusical.mainModule.songsFragment.service.SongsService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EpisodesFragment : Fragment() {

    private lateinit var mBinding: FragmentEpisodesBinding
    private lateinit var mEpisodesListAdapter: EpisodeListAdapter

    private lateinit var mLinearlayout: LinearLayoutManager

    private var idPodcast = arguments?.getLong("idPodcast")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentEpisodesBinding.inflate(inflater, container, false)
        idPodcast = arguments?.getLong("idPodcast")

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        if (idPodcast != null) {
            getPodcast(idPodcast!!)
            getEpisodes(idPodcast!!)
        }
    }

    private fun setupRecyclerView() {
        mEpisodesListAdapter = EpisodeListAdapter(this)

        mLinearlayout = LinearLayoutManager(this.context)

        mBinding.rvSongs.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayout
            adapter = mEpisodesListAdapter
        }
    }

    private fun getPodcast(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(EpisodesService::class.java)

        lifecycleScope.launch {

            try {

                val result = service.getPodcast(id)

                val podcast = result.body()!!

                var titulo = podcast.titulo
                var image = Constants.DEFAULT_PLAYLIST_IMAGE

                mBinding.tvPlaylist.text = titulo
                mBinding.tvUser.text = podcast.usuario.username

                context?.let {
                    Glide.with(it)
                        .load(image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(mBinding.ivPlaylistSf)
                }

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getEpisodes(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(EpisodesService::class.java)

        lifecycleScope.launch {

            try {

                val result = service.getEpisodes(id) //Aquí va id

                val episodes = result.body()!!

                mEpisodesListAdapter.submitList(episodes)
                mBinding.tvCanciones.text = "${episodes.size} capítulos"

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }
}