package com.tcp.zomatosearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcp.zomatosearch.R
import com.tcp.zomatosearch.data.model.Restaurant
import kotlinx.android.synthetic.main.internal_recycler_item_layout.view.*

class InternalAdapter(
    private var mData: ArrayList<Restaurant>
) : RecyclerView.Adapter<InternalAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: List<Restaurant>) {

            itemView.tv2_iril.text = user[adapterPosition].restaurant.name
            itemView.tv3_iril.text = user[adapterPosition].restaurant.average_cost_for_two.toString()
            itemView.tv4_iril.text = user[adapterPosition].restaurant.timings.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.internal_recycler_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(mData)

    fun setData(list: List<Restaurant>) {
        mData = ArrayList()
        mData.addAll(list)
    }

}