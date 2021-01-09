package com.tcp.zomatosearch.data.repository


import android.location.Location
import com.tcp.zomatosearch.data.api.ApiHelper
import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getRootData(q : String, loc : Location): Single<RootDataClass> {
        return apiHelper.getRootData(q,loc)
    }

}