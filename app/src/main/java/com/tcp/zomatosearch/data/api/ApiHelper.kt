package com.tcp.zomatosearch.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getRootData(q : String) = apiService.getRootData(q)

}