package com.manshal_khatri.apnamusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manshal_khatri.apnamusic.model.Song
import com.manshal_khatri.apnamusic.repository.FireStoreRepo

class SongViewModel : ViewModel(){

    private val repository = FireStoreRepo()

    private val _songsList = MutableLiveData<MutableList<Song>>(mutableListOf())
    val songsList : LiveData<MutableList<Song>> get() = _songsList

    fun getAllSongs(){
        repository.fetchAllSongs { list->
            for (element in list){
                val name = element.data?.get("name").toString()
                val url = element.data?.get("url").toString()
                _songsList.value?.add(Song(name,url))
            }
            _songsList.postValue(_songsList.value)
        }
    }

}