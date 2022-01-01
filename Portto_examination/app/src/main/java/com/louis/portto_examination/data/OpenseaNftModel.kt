package com.louis

class ModelOpenseaNft {
    var image_url: String = ""//收藏品圖片
    var name: String = ""//收藏品名稱
    var description: String = ""//收藏品描述
    var permalink: String = ""//按鈕連結
    var collection: Collection? = null

}

class Collection {
    var name: String = ""
}