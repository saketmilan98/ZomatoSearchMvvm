package com.tcp.zomatosearch.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcp.zomatosearch.R
import com.tcp.zomatosearch.data.api.ApiHelper
import com.tcp.zomatosearch.data.api.ApiServiceImpl
import com.tcp.zomatosearch.data.model.CuisineRestaurantDataClass
import com.tcp.zomatosearch.data.model.Restaurant
import com.tcp.zomatosearch.data.model.RootDataClass
import com.tcp.zomatosearch.ui.base.ViewModelFactory
import com.tcp.zomatosearch.ui.main.adapter.MainAdapter
import com.tcp.zomatosearch.ui.main.viewmodel.MainViewModel
import com.tcp.zomatosearch.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()

        bt1.setOnClickListener {
            mainViewModel.fetchRootData(et1.text.toString())
        }
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
    }

    private fun setupObserver() {
        mainViewModel.getRootData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { rootData -> renderList(rootData) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
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
            //diffHourList.add(getHourByCategory(diffHours).toString())
            //messages.add(MessageDC(smsId,smsType, number, body, Date(smsDate.toLong()), getHourByCategory(diffHours).toString(), smsDate, number))

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
}
