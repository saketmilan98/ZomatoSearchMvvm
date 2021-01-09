package com.tcp.zomatosearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tcp.zomatosearch.R
import com.tcp.zomatosearch.data.model.Restaurant
import kotlinx.android.synthetic.main.internal_recycler_item_layout.view.*

class InternalAdapter(
    private var mData: ArrayList<Restaurant>
) : RecyclerView.Adapter<InternalAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listRestaurant: List<Restaurant>) {
            if(listRestaurant[adapterPosition].restaurant.featured_image.isNotEmpty())
                Picasso.get().load(listRestaurant[adapterPosition].restaurant.featured_image).placeholder(R.drawable.placeholder1).error(R.drawable.placeholder1).into(itemView.iv1_iril)
            itemView.tv2_iril.text = listRestaurant[adapterPosition].restaurant.name
            itemView.tv3_iril.text = listRestaurant[adapterPosition].restaurant.location.address
            val ratingText = "<b>${listRestaurant[adapterPosition].restaurant.user_rating.aggregate_rating.toString()}</b>/5"
            itemView.tv4_iril.text = HtmlCompat.fromHtml(ratingText, FROM_HTML_MODE_LEGACY)
            itemView.tv5_iril.text = "₹${listRestaurant[adapterPosition].restaurant.average_cost_for_two} for two"

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