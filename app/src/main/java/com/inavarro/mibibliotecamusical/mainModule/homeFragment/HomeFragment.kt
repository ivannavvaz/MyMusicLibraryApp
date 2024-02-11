package com.inavarro.mibibliotecamusical.mainModule.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.databinding.FragmentHomeBinding
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.AlbumsAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PlaylistsAdapter
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PodcastsAdapter

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
}