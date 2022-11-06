package com.manshal_khatri.apnamusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manshal_khatri.apnamusic.R
import com.manshal_khatri.apnamusic.databinding.FragmentUploadSongBinding
import com.manshal_khatri.apnamusic.util.Functions
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel
import kotlinx.coroutines.*


class UploadSongFragment : Fragment() {

    private lateinit var binding: FragmentUploadSongBinding
    lateinit var mVM: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_song, container, false)
        binding = FragmentUploadSongBinding.bind(view)

        with(binding) {
            btnToHome.setOnClickListener { activity?.finish() }

            mVM.serverResponse.observe(viewLifecycleOwner) {
                if (it) {
                    binding.animSuccess.apply {
                        visibility = VISIBLE
                        playAnimation()
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(3000)
                        withContext(Dispatchers.Main) {
                            with(Functions) {
                                makeViewGone(progressLayout)
                                makeViewVisible(resultLayout)
                            }
                        }
                    }
                }
            }
            return binding.root
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(vm : SongViewModel) =
            UploadSongFragment().apply {
                arguments = Bundle().apply {
                    mVM = vm
                }
            }
    }
}