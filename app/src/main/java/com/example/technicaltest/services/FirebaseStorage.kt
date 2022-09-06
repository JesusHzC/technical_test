package com.example.technicaltest.services

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay

class FirebaseStorage {

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    // Uploads a file to the storage reference
    suspend fun uploadImage(image: Uri, name: String, callback: (String) -> Unit) {
        val imageRef = storageRef.child("images/$name")
        var url = ""
        imageRef.putFile(image).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                url = it.toString()
            }
        }
        delay(2000)
        callback(url)
    }

    // Deletes a file from the storage reference
    fun deleteImage(name: String) {
        val imageRef = storageRef.child("images/$name")
        imageRef.delete()
    }

    // Downloads a file from the storage reference
    fun downloadImage(name: String, callback: (String) -> Unit) {
        val imageRef = storageRef.child("images/$name")
        imageRef.downloadUrl.addOnSuccessListener {
            callback(it.toString())
        }
    }

    // Updates a file from the storage reference
    fun updateImage(image: Uri, name: String, callback: (String) -> Unit) {
        val imageRef = storageRef.child("images/$name")
        imageRef.putFile(image).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                callback(it.toString())
            }
        }
    }

    // Gets the download url of a file from the storage reference
    fun getDownloadUrl(name: String, callback: (String) -> Unit) {
        val imageRef = storageRef.child("images/$name")
        imageRef.downloadUrl.addOnSuccessListener {
            callback(it.toString())
        }
    }

    // Get url for all images
    suspend fun getAllImages(): MutableList<String> {
        val gallery = mutableListOf<String>()
        val listRef = storageRef.child("images")
        listRef.listAll().addOnSuccessListener { result ->
            result.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener {
                    gallery.add(it.toString())
                }
            }
        }
        delay(2000)
        return gallery
    }

}