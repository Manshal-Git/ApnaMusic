package com.manshal_khatri.apnamusic.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.ViewModelProvider
import com.manshal_khatri.apnamusic.databinding.ActivityMainBinding
import com.manshal_khatri.apnamusic.ui.fragment.PlayerFragment
import com.manshal_khatri.apnamusic.ui.fragment.SongsFragment
import com.manshal_khatri.apnamusic.ui.fragment.UploadSongFragment
import com.manshal_khatri.apnamusic.util.Constants
import com.manshal_khatri.apnamusic.util.Functions
import com.manshal_khatri.apnamusic.util.NavAction
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var vm : SongViewModel
    lateinit var res : Resources
    private var songUri : Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[SongViewModel::class.java]
        replaceFragment(NavAction.SongsFragment)

        vm.currentSong.observe(this){
            replaceFragment(NavAction.PlayerFragment)
        }

        binding.btnAddNew.setOnClickListener {
            createInputDialog()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount==0) finish()
        makeUploadButtonVisible()
    }
    fun replaceFragment(screen: NavAction){
        val fragment = if(screen==NavAction.SongsFragment) SongsFragment.newInstance(vm)
        else if(screen==NavAction.PlayerFragment) PlayerFragment.newInstance(vm)
        else UploadSongFragment.newInstance(vm,this)
        if(screen!=NavAction.SongsFragment) Functions.makeViewGone(binding.btnAddNew)
        else makeUploadButtonVisible()
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(binding.fragmentContainerView.id,
                fragment).setTransition(TRANSIT_FRAGMENT_FADE).commit()
    }

    fun makeUploadButtonVisible(){
        if(supportFragmentManager.backStackEntryCount<=1) Functions.makeViewVisible(binding.btnAddNew)
    }

    private fun selectSongFromStorage(){
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constants.CHOOSE_SONG)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == Constants.CHOOSE_SONG){
                if(data!=null){
                    try{
                        songUri  = data.data!!
                        vm.uploadSongFile(vm.songToUpload.value!!.name,songUri!!)


                    }catch(e : Exception){
                        e.printStackTrace()
                    }

                }
            }
        }else{
            Toast.makeText(this, "Req canceled", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun createInputDialog(){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enter Song Name")
        val etName = EditText(this)
        dialog.setView(etName)
        dialog.setPositiveButton("Upload",){ p0, p1 ->
            if(etName.text.isEmpty()){
                Toast.makeText(this, "Can't upload with empty Name !", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            vm.songToUpload.value?.name = etName.text.toString()
            selectSongFromStorage()
            replaceFragment(NavAction.UploadSongFragment)
        }
        dialog.show()
    }

}