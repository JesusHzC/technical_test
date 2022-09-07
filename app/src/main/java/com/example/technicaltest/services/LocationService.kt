package com.example.technicaltest.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationService : Service() {

    companion object {
        var isRunning = false
        const val TAG_SERVICE_LOCATION = "LocationService"
        const val NOTIFICATION_CHANNEL = "LocationServiceChannel"
        const val ACTION_STOP_LOCATION_SERVICE = "ACTION_STOP_LOCATION_SERVICE"
        const val ACTION_START_LOCATION_SERVICE = "ACTION_START_LOCATION_SERVICE"
        private const val CODE_FOREGROUND_SERVICE = 1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @Singleton
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.getStringExtra(TAG_SERVICE_LOCATION)) {
            ACTION_STOP_LOCATION_SERVICE -> {
                onDestroy()
            }
        }

        isRunning = true
        startLocation(this)
        showNotification()

        return START_STICKY
    }

    private fun startLocation(context: Context) {

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!hasAccessCoarseLocationPermission
            || !hasAccessFineLocationPermission
            || !isGpsEnabled) {
            return@startLocation
        } else {
            lateinit var locationCallbackVar: LocationCallback

            val locationRequestVar: LocationRequest = LocationRequest.create().apply {
                interval = 300000
                fastestInterval = 300000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }

            locationCallbackVar = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    Intent().also { intent ->
                        intent.action = "android.intent.action.NEW_LOCATION"
                        intent.putExtra("latitude", p0.lastLocation?.latitude.toString())
                        intent.putExtra("longitude", p0.lastLocation?.longitude.toString())
                        sendBroadcast(intent)
                    }
                }
            }

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.requestLocationUpdates(
                locationRequestVar,
                locationCallbackVar,
                Looper.getMainLooper()
            )
        }
    }

    private fun showNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                with(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL,
                        "Make it Easy",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                ) {
                    enableLights(false)
                    setShowBadge(false)
                    enableVibration(false)
                    setSound(null, null)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(this)
                }
            } catch (e: Exception) {
                Log.d("Error", "showNotification: ${e.localizedMessage}")
            }
        }

        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
        ) {
            setTicker(null)
            setContentTitle("Localizacion")
            setContentText("Servicio de ubicación activo")
            setAutoCancel(false)
            setOngoing(true)
            setWhen(java.lang.System.currentTimeMillis())
            startForeground(CODE_FOREGROUND_SERVICE, build())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        stopForeground(true)
        stopSelf()
    }

}