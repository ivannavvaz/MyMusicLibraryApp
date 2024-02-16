package com.inavarro.mibibliotecamusical.mainModule.homeFragment

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
import kotlin.math.roundToInt

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
    ): View {
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

        // Set the scroll listener to the scroll view for Sticky Header

        mBinding.svHome.setOnScrollChangeListener(View.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            val scrolled = scrollY - oldScrollY
            val layoutparams = mBinding.vStatusBar.layoutParams as ViewGroup.MarginLayoutParams

            if (scrollY > oldScrollY) { // Scroll up
                if (layoutparams.topMargin - scrolled > 0) // Avoid negative values
                    layoutparams.setMargins(0, layoutparams.topMargin - scrolled, 0, 0)
                else
                    layoutparams.setMargins(0, 0, 0, 0)

            } else if (scrollY < oldScrollY) { // Scroll down
                if (scrollY <= 32 * Resources.getSystem().displayMetrics.density) {
                    if (scrollY > 0) // Avoid negative values
                        layoutparams.setMargins(0, layoutparams.topMargin - scrolled, 0, 0)
                    else { // Set the margin to 32dp when the scroll is 0 or negative
                        layoutparams.setMargins(
                            0,
                            (32 * Resources.getSystem().displayMetrics.density).roundToInt(),
                            0,
                            0
                        )
                    }
                }
            }

            mBinding.vStatusBar.layoutParams = layoutparams // Apply the new margin
        })


        Glide.with(requireContext())

            .load(Constants.DEFAULT_ALBUM_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(mBinding.ivUser)


    }

    private fun setupRecyclerViews() {
        mPlaylistsListAdapter = PlaylistListAdapter(this)
        mPodcastsListAdapter = PodcastListAdapter(this)
        mAlbumsAdapter = AlbumListAdapter(this)

        mLinearlayoutPlaylist = LinearLayoutManager(this.context)
        mLinearlayoutPlaylist.orientation = LinearLayoutManager.HORIZONTAL

        mLinearlayoutPodcast = LinearLayoutManager(this.context)
        mLinearlayoutPodcast.orientation = LinearLayoutManager.HORIZONTAL

        mLinearlayoutAlbum = LinearLayoutManager(this.context)
        mLinearlayoutAlbum.orientation = LinearLayoutManager.HORIZONTAL

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

                val playlists = result.body()!!

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

                val podcasts = result.body()!!

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
                val result = service.getAlbumsUser(1) // Aquí debería ir el id del usuario

                val albums = result.body()!!

                mAlbumsAdapter.submitList(albums)

            } catch (e: Exception) {
                Log.e("SET ALBUM ERROR", e.message.toString())
            }
        }
    }
}