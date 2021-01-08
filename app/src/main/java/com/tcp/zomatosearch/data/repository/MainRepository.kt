package com.tcp.zomatosearch.data.repository


import com.tcp.zomatosearch.data.api.ApiHelper
import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getRootData(q : String): Single<RootDataClass> {
        return apiHelper.getRootData(q)
    }

}