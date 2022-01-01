package com.louis.portto_examination.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.louis.portto_examination.R

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: MainViewModel
    private var progressBar: ProgressBar? = null
    private var rv_openseanft: RecyclerView? = null
    private var tv_balance: TextView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var openseaNftListAdapter: OpenseaNftListAdapter? = null
    private var recycleviewUtil: RecycleviewUtil? = null
    private var dataList_Offeset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataList_Offeset = 0
        progressBar = view.findViewById(R.id.pg)
        tv_balance = view.findViewById(R.id.tv_balance)
        gridLayoutManager = GridLayoutManager(activity, 2)
        rv_openseanft = view.findViewById(R.id.rv_openseanft)
        openseaNftListAdapter = OpenseaNftListAdapter(requireContext(), arrayListOf())

        rv_openseanft?.apply {
            recycleviewUtil = RecycleviewUtil(this)
            recycleviewUtil?.apply {
                setLoadMoreListener(object : LoadMoreListener {
                    override fun onLoadMore() {
                        setLoadMore(false)
                        setProgressBar(true)
                        viewModel.queryOpenseaNftList(dataList_Offeset)
                    }
                })
            }
            layoutManager = gridLayoutManager
            adapter = openseaNftListAdapter
        }

        openseaNftListAdapter?.setItemClickListener(object : OnRecyclerViewClickListener {
            override fun onItemClickListener(view: View?) {
                view?.apply {
                    val position: Int = rv_openseanft!!.getChildAdapterPosition(this)
                    val openseaNft = openseaNftListAdapter?.getItem(position)
                    openseaNft?.apply {
                        val action = MainFragmentDirections.actionMainToDetial(Gson().toJson(this))
                        view.findNavController().navigate(action)
                    }
                }
            }
        })

        viewModel.apply {
            liveDataNftList.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    dataList_Offeset += it.size
                    openseaNftListAdapter?.addData(it)
                    recycleviewUtil?.setLoadMore(true)
                }
                setProgressBar(false)

            })

            liveDataNetworkError.observe(viewLifecycleOwner, {
                setProgressBar(false)
            })
            liveDataEthBalance.observe(viewLifecycleOwner,{
                tv_balance?.text = "eth 餘額：$it"
            })

            setProgressBar(true)
            queryOpenseaNftList(dataList_Offeset)
            getEthBalance()
        }

    }

    private fun setProgressBar(show: Boolean) {
        progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }
}
