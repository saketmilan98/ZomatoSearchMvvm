package com.tcp.zomatosearch.data.api

import android.location.Location
import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

interface ApiService {

    fun getRootData(q : String, loc : Location): Single<RootDataClass>

}