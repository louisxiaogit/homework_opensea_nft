package com.louis.portto_examination.ui

import androidx.recyclerview.widget.RecyclerView

class RecycleviewUtil(var recyclerView: RecyclerView) {
    private var enable = true
    private var loadMoreListener: LoadMoreListener? = null

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
        this.loadMoreListener = loadMoreListener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom(recyclerView)) {
                    if (enable && null != loadMoreListener) {
                        loadMoreListener.onLoadMore()
                    }
                }
            }
        })
    }

    fun isBottom(recyclerView: RecyclerView): Boolean {
        recyclerView?.apply {
            return (computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange())
        }
        return false
    }

    fun setLoadMore(enable: Boolean) {
        this.enable = enable
    }

}

interface LoadMoreListener {
    fun onLoadMore()
}