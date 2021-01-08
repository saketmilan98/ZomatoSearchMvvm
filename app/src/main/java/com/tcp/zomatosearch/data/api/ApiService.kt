package com.tcp.zomatosearch.data.api

import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

interface ApiService {

    fun getRootData(q : String): Single<RootDataClass>

}