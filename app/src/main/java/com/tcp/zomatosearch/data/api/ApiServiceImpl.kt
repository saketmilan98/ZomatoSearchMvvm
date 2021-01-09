package com.tcp.zomatosearch.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.tcp.zomatosearch.BuildConfig
import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getRootData(q: String): Single<RootDataClass> {
        return Rx2AndroidNetworking.get("https://developers.zomato.com/api/v2.1/search")
            .addQueryParameter("q",q)
            .addQueryParameter("lat","22.5726")
            .addQueryParameter("lon","88.3639")
            .addHeaders("user-key",BuildConfig.API_KEY)
            .build()
            .getObjectSingle(RootDataClass::class.java)
    }

}