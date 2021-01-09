package com.tcp.zomatosearch.data.api

import android.location.Location

class ApiHelper(private val apiService: ApiService) {

    fun getRootData(q : String, loc : Location) = apiService.getRootData(q, loc)

}