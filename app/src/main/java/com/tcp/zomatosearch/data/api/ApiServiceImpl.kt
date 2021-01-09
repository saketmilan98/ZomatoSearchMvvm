package com.tcp.zomatosearch.data.api

import android.location.Location
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.tcp.zomatosearch.BuildConfig
import com.tcp.zomatosearch.app.MyApp
import com.tcp.zomatosearch.app.MyApp.Companion.BASE_URL
import com.tcp.zomatosearch.data.model.RootDataClass
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getRootData(q : String, loc : Location): Single<RootDataClass> {
        return Rx2AndroidNetworking.get("${BASE_URL}search")
            .addQueryParameter("q",q)
            .addQueryParameter("lat","${loc.latitude}")
            .addQueryParameter("lon","${loc.longitude}")
            .addHeaders("user-key",BuildConfig.API_KEY)
            .build()
            .getObjectSingle(RootDataClass::class.java)
    }

}