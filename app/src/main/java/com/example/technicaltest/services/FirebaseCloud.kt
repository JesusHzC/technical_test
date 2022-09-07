package com.example.technicaltest.services

import com.example.technicaltest.domain.location.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class FirebaseCloud {

    private var db = Firebase.firestore

    fun initInstance() {
        val settings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
        db.firestoreSettings = settings
        db = FirebaseFirestore.getInstance()
    }

    fun getDb(): FirebaseFirestore {
        return db
    }

    fun addLocation(location: Location, success: () -> Unit, failure: (String) -> Unit) {
        db.collection("locations")
            .add(location)
            .addOnSuccessListener {
                success()
            }
            .addOnFailureListener { e ->
                failure(e.message.toString())
            }
    }

    fun getLocations(callback : (List<Location>) -> Unit, failure: (String) -> Unit) {
        db.collection("locations")
            .get()
            .addOnSuccessListener { result ->
                val locations = mutableListOf<Location>()
                for (document in result) {
                    val location = document.toObject(Location::class.java)
                    locations.add(location)
                }
                callback(locations)
            }
            .addOnFailureListener { exception ->
                failure(exception.message.toString())
            }
    }
}