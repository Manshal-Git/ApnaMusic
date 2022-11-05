package com.manshal_khatri.apnamusic.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.manshal_khatri.apnamusic.databinding.ActivityMainBinding
import com.manshal_khatri.apnamusic.ui.fragment.PlayerFragment
import com.manshal_khatri.apnamusic.ui.fragment.SongsFragment
import com.manshal_khatri.apnamusic.util.NavAction
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var vm : SongViewModel
    lateinit var res : Resources

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[SongViewModel::class.java]
        replaceFragment(NavAction.SongsFragment)

        vm.currentSong.observe(this){
            replaceFragment(NavAction.PlayerFragment)
        }

    }

    fun replaceFragment(screen: NavAction){
        val fragment = if(screen==NavAction.SongsFragment) SongsFragment.newInstance(vm)
        else PlayerFragment.newInstance(vm)
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(binding.fragmentContainerView.id,
                fragment).commit()
    }

}