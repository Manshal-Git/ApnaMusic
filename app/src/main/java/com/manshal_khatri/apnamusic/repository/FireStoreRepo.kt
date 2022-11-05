package com.manshal_khatri.apnamusic.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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
}