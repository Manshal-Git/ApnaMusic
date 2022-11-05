package com.manshal_khatri.apnamusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.manshal_khatri.apnamusic.R
import com.manshal_khatri.apnamusic.databinding.FragmentPlayerBinding
import com.manshal_khatri.apnamusic.model.Song
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class PlayerFragment : Fragment() {

    private lateinit var binding : FragmentPlayerBinding
    lateinit var mVM : SongViewModel
    private var player : ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private var lastSong : Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        binding = FragmentPlayerBinding.bind(view)
        Toast.makeText(requireContext(), "$", Toast.LENGTH_SHORT).show()
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            mVM.currentSong.value?.let { initializePlayer(it.url) }
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23 || player == null)) {
            mVM.currentSong.value?.let { initializePlayer(it.url) }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }


    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer(url : String){

        player = ExoPlayer.Builder(requireActivity())
            .build()
            .also { exoPlayer ->
                binding.mediaPlayer.player = exoPlayer

                val mediaItem = MediaItem.Builder()
                    .setUri(url)
                    .setMimeType(MimeTypes.AUDIO_MPEG)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }
    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            lastSong = mVM.currentSong.value
            exoPlayer.release()

        }
        player = null
    }

    companion object {

        @JvmStatic
        fun newInstance(vm : SongViewModel) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    mVM = vm
                }
            }
    }
}