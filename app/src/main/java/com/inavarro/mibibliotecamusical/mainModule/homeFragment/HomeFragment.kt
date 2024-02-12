package com.inavarro.mibibliotecamusical.mainModule.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.databinding.FragmentHomeBinding
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.AlbumListAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PodcastListAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.services.HomeService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mPlaylistsListAdapter: PlaylistListAdapter
    private lateinit var mPodcastsListAdapter: PodcastListAdapter
    private lateinit var mAlbumsAdapter: AlbumListAdapter
    private lateinit var mLinearlayoutPlaylist: LinearLayoutManager
    private lateinit var mLinearlayoutPodcast: LinearLayoutManager
    private lateinit var mLinearlayoutAlbum: LinearLayoutManager

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mLayoutManager = LinearLayoutManager(requireContext())

        setupRecyclerViews()

        getPlaylist()
        getPodcasts()
        getAlbums()
    }

    private fun setupRecyclerViews() {
        mPlaylistsListAdapter = PlaylistListAdapter(this)
        mPodcastsListAdapter = PodcastListAdapter(this)
        mAlbumsAdapter = AlbumListAdapter(this)

        mLinearlayoutPlaylist = LinearLayoutManager(this.context)
        mLinearlayoutPodcast = LinearLayoutManager(this.context)
        mLinearlayoutAlbum = LinearLayoutManager(this.context)

        mBinding.rvPlaylists.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayoutPlaylist
            adapter = mPlaylistsListAdapter
        }

        mBinding.rvPodcasts.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayoutPodcast
            adapter = mPodcastsListAdapter
        }

        mBinding.rvAlbums.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayoutAlbum
            adapter = mAlbumsAdapter
        }
    }

    override fun onClick(albumEntity: Album) {
        TODO("Not yet implemented")
    }

    override fun onClick(playlistEntity: Playlist) {
        TODO("Not yet implemented")
    }

    override fun onClick(podcastEntity: Podcast) {
        TODO("Not yet implemented")
    }

    private fun getPlaylist() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HomeService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.getPlaylistUser(1) // Aquí debería ir el id del usuario

                Log.d("PLAYLISTS", result.toString())

                val playlists = result.body()!!

                Log.d("PLAYLISTS", playlists.toString())

                mPlaylistsListAdapter.submitList(playlists)

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    private fun getPodcasts() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HomeService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.getPodcastUser(1) // Aquí debería ir el id del usuario

                Log.d("PODCASTS", result.toString())

                val podcasts = result.body()!!

                Log.d("PODCASTS", podcasts.toString())

                mPodcastsListAdapter.submitList(podcasts)

            } catch (e: Exception) {
                Log.e("SET PODCAST ERROR", e.message.toString())
            }
        }
    }

    private fun getAlbums() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HomeService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.getAlbumstUser(1) // Aquí debería ir el id del usuario

                Log.d("ALBUMS", result.toString())

                val albums = result.body()!!

                Log.d("ALBUMS", albums.toString())

                mAlbumsAdapter.submitList(albums)

            } catch (e: Exception) {
                Log.e("SET ALBUM ERROR", e.message.toString())
            }
        }
    }
}