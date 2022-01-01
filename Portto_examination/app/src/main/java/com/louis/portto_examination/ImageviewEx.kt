package com.louis.portto_examination

import android.content.Context
import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load

fun ImageView.mutiTypeLoad(context: Context, url: String) {
    val svgImageLoader = ImageLoader.Builder(context).componentRegistry {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder())
        } else {
            add(GifDecoder())
        }
        add(SvgDecoder(context))
    }.build()
    this.load(url, svgImageLoader)
}