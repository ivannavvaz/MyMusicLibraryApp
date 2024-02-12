package com.inavarro.mibibliotecamusical.mainModule.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.authModule.services.LoginService
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.databinding.FragmentHomeBinding
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.AlbumsAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PlaylistsAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PodcastsAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.services.HomeService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mPlaylistsAdapter: PlaylistsAdapter
    private lateinit var mPodcastsAdapter: PodcastsAdapter
    private lateinit var mAlbumsAdapter: AlbumsAdapter

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
        getPlaylist()
    }

    private fun setupRecyclerViews() {
        mPlaylistsAdapter = PlaylistsAdapter(mutableListOf(), this)
        mPodcastsAdapter = PodcastsAdapter(mutableListOf(), this)
        mAlbumsAdapter = AlbumsAdapter(mutableListOf(), this)
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

            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }

        }
    }
}