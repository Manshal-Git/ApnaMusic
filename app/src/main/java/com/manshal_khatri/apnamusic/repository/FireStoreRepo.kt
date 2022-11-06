package com.manshal_khatri.apnamusic.repository

import android.net.Uri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manshal_khatri.apnamusic.model.Song
import com.manshal_khatri.apnamusic.util.Constants

class FireStoreRepo {

    private val fireStore = FirebaseFirestore.getInstance()

    fun fetchAllSongs(setSongsData:(List<DocumentSnapshot>)->Unit){
        fireStore.collection(Constants.FIREBASE_COLLECTION_SONGS).get()
            .addOnSuccessListener {
                val records = it.documents
                if(!it.isEmpty){
                    setSongsData(records)
                }
            }.addOnFailureListener {
                println("ERROR $it")
            }
    }

    fun uploadSongData(song: Song, setServerResponse:(success : Boolean)->Unit) {
        fireStore.collection(Constants.FIREBASE_COLLECTION_SONGS)
            .document(song.name)
            .set(song, SetOptions.merge())
            .addOnSuccessListener {
                setServerResponse(true)
            }.addOnFailureListener{
                println("error while uploading song data: $it")
                setServerResponse(false)
            }
    }

    fun uploadSongFile(songUri : Uri,setSongUrl :(url : String) -> Unit) {
        val firebaseStorage: StorageReference =
            FirebaseStorage.getInstance().reference.child("Song" + System.currentTimeMillis())
        firebaseStorage.putFile(songUri)
            .addOnSuccessListener { it ->
                it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    setSongUrl("$it")
                }.addOnFailureListener {
                        println("Fail to get Url: $it")
                }
            }
            .addOnFailureListener {
                println("error while uploading song file: $it")
            }
    }
}