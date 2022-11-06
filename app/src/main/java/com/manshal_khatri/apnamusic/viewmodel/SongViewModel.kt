package com.manshal_khatri.apnamusic.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manshal_khatri.apnamusic.model.Song
import com.manshal_khatri.apnamusic.repository.FireStoreRepo

class SongViewModel : ViewModel(){

    private val repository = FireStoreRepo()

    private val _songsList = MutableLiveData<MutableList<Song>>(mutableListOf())
    val songsList : LiveData<MutableList<Song>> get() = _songsList
    private val _currentSong = MutableLiveData<Song>()
    val currentSong : LiveData<Song> get() = _currentSong
    private val _songToUpload = MutableLiveData<Song>(Song())
    val songToUpload : LiveData<Song> get() = _songToUpload
    private val _serverResponse = MutableLiveData<Boolean>()
    val serverResponse : LiveData<Boolean> get() = _serverResponse

    fun getAllSongs(){
        _songsList.value?.clear()
        repository.fetchAllSongs { list->
            for (element in list){
                val name = element.data?.get("name").toString()
                val url = element.data?.get("url").toString()
                val imgUrl = element.data?.get("imgUrl").toString()
                _songsList.value?.add(Song(name,url,imgUrl))
            }
            _songsList.postValue(_songsList.value)
        }
    }

    fun sendSong(song: Song){
        repository.uploadSongData(song){ isSuccess ->
            _serverResponse.postValue(isSuccess)
        }
    }

    fun uploadSongFile(name : String, songUri : Uri){
        repository.uploadSongFile(songUri) {
            sendSong(Song(name,it))
        }
    }

    fun makeCurrentSong(song : Song){
        _currentSong.postValue(song)
    }

}