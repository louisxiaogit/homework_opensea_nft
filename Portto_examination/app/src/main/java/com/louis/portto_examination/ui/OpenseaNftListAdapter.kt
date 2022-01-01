package com.louis.portto_examination.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.louis.ModelOpenseaNft
import com.louis.portto_examination.R
import com.louis.portto_examination.mutiTypeLoad

class OpenseaNftListAdapter(
    context: Context,
    private val dataList: MutableList<ModelOpenseaNft>
) :
    RecyclerView.Adapter<OpenseaNftListAdapter.NftViewHolder>() {
    private var mContext: Context = context
    private var listener: OnRecyclerViewClickListener? = null

    fun setItemClickListener(itemClickListener: OnRecyclerViewClickListener) {
        listener = itemClickListener
    }

    fun addData(items: List<ModelOpenseaNft>) {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(postion: Int): ModelOpenseaNft {
        return dataList[postion]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenseaNftListAdapter.NftViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_opensea_nft, parent, false)

        if (listener != null) {
            view.setOnClickListener { v -> listener!!.onItemClickListener(v) }
        }
        return NftViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: NftViewHolder, position: Int) {
        val item = dataList[position]
        holder.bindHolder(item = item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class NftViewHolder(itemView: View, var viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var tvnftName: TextView? = null
        var ivNft: ImageView? = null


        fun bindHolder(item: ModelOpenseaNft) {
            tvnftName = itemView.findViewById(R.id.tv_nft_name);
            ivNft = itemView.findViewById(R.id.iv_nft)
            item.apply {
                tvnftName?.text = this.name
                ivNft?.mutiTypeLoad(mContext, this.image_url)
            }
        }

    }
}

interface OnRecyclerViewClickListener {
    fun onItemClickListener(view: View?)
}