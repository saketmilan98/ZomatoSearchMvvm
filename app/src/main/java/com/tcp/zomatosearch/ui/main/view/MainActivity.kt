package com.tcp.zomatosearch.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.tcp.zomatosearch.R
import com.tcp.zomatosearch.app.MyApp
import com.tcp.zomatosearch.data.api.ApiHelper
import com.tcp.zomatosearch.data.api.ApiServiceImpl
import com.tcp.zomatosearch.data.model.CuisineRestaurantDataClass
import com.tcp.zomatosearch.data.model.Restaurant
import com.tcp.zomatosearch.data.model.RootDataClass
import com.tcp.zomatosearch.ui.base.ViewModelFactory
import com.tcp.zomatosearch.ui.main.adapter.MainAdapter
import com.tcp.zomatosearch.ui.main.viewmodel.MainViewModel
import com.tcp.zomatosearch.utils.GpsUtils
import com.tcp.zomatosearch.utils.Status
import com.tcp.zomatosearch.utils.Tools
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var userLocation: Location
    val PERMS_REQUEST_CODE = 200
    var isGPS = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForPermissions()
    }

    fun callInitialFunctions(){
        setupUI()
        setupViewModel()
        setupObserver()
        mainViewModel.fetchRootData("", userLocation)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        searchView1.onActionViewExpanded()
        searchView1.queryHint = resources.getString(R.string.query_hint)

        searchView1.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.fetchRootData(query!!,userLocation)
                return false
            }
        })
    }

    private fun setupObserver() {
        mainViewModel.getRootData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { rootData ->
                        if(rootData.restaurants.isNotEmpty()) {
                            renderList(rootData)
                            recyclerView.visibility = View.VISIBLE
                            status_tv.visibility = View.GONE
                        }
                        else{
                            recyclerView.visibility = View.GONE
                            status_tv.visibility = View.VISIBLE
                            status_tv.text = resources.getString(R.string.notfound_text)
                        }
                    }
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    status_tv.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    recyclerView.visibility = View.GONE
                    status_tv.visibility = View.VISIBLE
                    status_tv.text = resources.getString(R.string.error_text)
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(rootData: RootDataClass) {

        var restaurantList = ArrayList<Restaurant>()
        val cuisineList = ArrayList<String>()
        val cuisinesRestaurantList = ArrayList<CuisineRestaurantDataClass>()

        restaurantList = rootData.restaurants as ArrayList<Restaurant>

        restaurantList.forEach {
            cuisineList.add(it.restaurant.cuisines)
        }

        cuisineList.forEach { cuisines ->
            if (cuisinesRestaurantList.find { it.cuisines == cuisines } == null) {
                val restaurants = restaurantList.filter { it.restaurant.cuisines == cuisines }
                cuisinesRestaurantList.add(CuisineRestaurantDataClass(cuisines = cuisines, restaurants = restaurants))
            }
        }


        adapter.setData(cuisinesRestaurantList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    fun getCurrentLocation() {
        getLocationUpdates()
        startLocationUpdates()
    }

    private fun getLocationUpdates()
    {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
        locationRequest = LocationRequest()
        locationRequest.interval = 12000
        locationRequest.fastestInterval = 12000
        locationRequest.smallestDisplacement = 50f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    //get latest location
                    val location =
                        locationResult.lastLocation
                    userLocation = location
                    stopLocationUpdates()
                    callInitialFunctions()


                }


            }
        }
    }

    //start location updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Tools().showToast("Please grant location permission.", this)
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper() /* Looper */
        )
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Tools().showToast("Please grant permissions.", this)

            val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(this, perms, PERMS_REQUEST_CODE)
            return
        } else {

            if(Tools().isLocationEnabled(this)){
                getCurrentLocation()
            }
            else{
                //enablegps()
                GpsUtils(this).turnGPSOn(object : GpsUtils.onGpsListener {
                    override fun gpsStatus(isGPSEnable: Boolean) {
                        isGPS = isGPSEnable
                    }
                })
                Tools().showToast("Please turn on location to continue", this)
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyApp.GPS_REQUEST) {
                isGPS = true // flag maintain before get location
                if (isGPS) {
                    getCurrentLocation()
                }
            }
        }
        else{
            if (requestCode == MyApp.GPS_REQUEST) {
                checkForPermissions()
                Tools().showToast("location on request denied",this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PERMS_REQUEST_CODE -> if (grantResults.isNotEmpty()) {
                grantResults.forEach {
                    if (it == PackageManager.PERMISSION_GRANTED) {
                        if(Tools().isLocationEnabled(this)){
                            getCurrentLocation()
                            return
                        }
                        else{
                            //enablegps
                            GpsUtils(this).turnGPSOn(object : GpsUtils.onGpsListener {
                                override fun gpsStatus(isGPSEnable: Boolean) {
                                    isGPS = isGPSEnable
                                }
                            })
                            Tools().showToast("Please turn on location to continue", this)
                        }
                    } else {
                        ActivityCompat.requestPermissions(this, permissions, PERMS_REQUEST_CODE)
                        return
                    }
                }
            }
        }
    }

}
