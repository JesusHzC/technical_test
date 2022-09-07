package com.example.technicaltest.presentation.ui.fragments.location

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.App
import com.example.technicaltest.domain.location.Location
import com.google.android.material.snackbar.Snackbar

class LocationViewModel : ViewModel() {

    private val _location = MutableLiveData(Location())
    val location = _location

    private val _allLocations = MutableLiveData<List<Location>>(listOf())
    val locations = _allLocations

    fun setLocation(location: Location) {
        _location.postValue(location)
    }

    fun saveLocation(location: Location, view: View) {
        App.firebaseCloud.addLocation(
            location = location,
            failure = {
                showSnackBar(it, view)
            },
            success = {
                showSnackBar("Ubicacion agregada exitosamente", view)
                _allLocations.postValue(_allLocations.value?.plus(location))
            }
        )
    }

    fun getAllLocations(view: View) {
        App.firebaseCloud.getLocations(
            failure = {
                showSnackBar(it, view)
            },
            callback = {
                _allLocations.value = it
            }
        )
    }

    fun getCurrentDate(): String {
        val date = java.util.Calendar.getInstance().time
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }


    private fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

}