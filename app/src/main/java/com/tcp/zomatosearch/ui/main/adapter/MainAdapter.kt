package com.tcp.zomatosearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tcp.zomatosearch.R
import com.tcp.zomatosearch.data.model.CuisineRestaurantDataClass
import com.tcp.zomatosearch.data.model.Restaurant
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private var mData: ArrayList<CuisineRestaurantDataClass>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listCuisineRestaurant: List<CuisineRestaurantDataClass>) {
            itemView.textViewCuisine.text = "Restaurants serving "+listCuisineRestaurant[adapterPosition].cuisines

            val adapter = InternalAdapter(arrayListOf())
            itemView.rv1_il.layoutManager = LinearLayoutManager(itemView.rv1_il.context, RecyclerView.VERTICAL,false)
            itemView.rv1_il.adapter = adapter

            adapter.setData(listCuisineRestaurant[adapterPosition].restaurants as ArrayList<Restaurant>)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(mData)

    fun setData(list: List<CuisineRestaurantDataClass>) {
        mData = ArrayList()
        mData.addAll(list)
    }

}