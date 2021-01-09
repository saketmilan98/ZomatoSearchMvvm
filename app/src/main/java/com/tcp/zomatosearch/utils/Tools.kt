package com.tcp.zomatosearch.utils

import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.core.location.LocationManagerCompat

class Tools{
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    fun showToast(str : String, ctx : Context){
        Toast.makeText(ctx, str, Toast.LENGTH_LONG).show()
    }
}