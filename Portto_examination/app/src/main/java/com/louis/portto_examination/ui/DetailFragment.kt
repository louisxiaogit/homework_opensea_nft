package com.louis.portto_examination.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.louis.ModelOpenseaNft
import com.louis.portto_examination.R
import com.louis.portto_examination.mutiTypeLoad


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment(R.layout.fragment_detial) {
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nftModel = Gson().fromJson(args.nftmodel, ModelOpenseaNft::class.java)
        val tv_collection_name = view.findViewById<TextView>(R.id.tv_collection_name)
        val tv_detial_description = view.findViewById<TextView>(R.id.tv_detial_description)
        val tv_detial_name = view.findViewById<TextView>(R.id.tv_detial_name)
        val iv_detial_nft = view.findViewById<ImageView>(R.id.iv_detial_nft)
        val bt_link = view.findViewById<Button>(R.id.bt_link)

        nftModel.apply {
            tv_collection_name.text = collection?.name
            tv_detial_name.text = name
            iv_detial_nft.mutiTypeLoad(requireContext(), image_url)
            tv_detial_description.text = description
            bt_link.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(permalink))
                startActivity(browserIntent)
            }
        }


    }
}