package com.louis.portto_examination

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import android.util.JsonReader
import com.louis.ModelOpenseaNft
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import com.google.gson.Gson
import com.google.gson.JsonElement


open class Result<out T : Any> {
    class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val exception: Exception) : Result<Nothing>()
}

open class RepositoryOpensea() {
    private val openseaUrl = "https://api.opensea.io/api/v1/assets"

    fun getNftList(
        offset: Int
    ): Result<List<ModelOpenseaNft>> {
        val parameter =
            "?owner=0x19818f44Faf5A217F619AFF0FD487CB2a55cCa65&order_direction=desc&offset=$offset&limit=20"
        val url = URL(openseaUrl + parameter)
        (url.openConnection() as? HttpURLConnection)?.run {
            try {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                val bR = BufferedReader(InputStreamReader(inputStream))

                var line: String? = ""
                val responseStrBuilder = StringBuilder()
                while (bR.readLine().also { line = it } != null) {
                    responseStrBuilder.append(line)
                }
                inputStream.close()
                val result = JSONObject(responseStrBuilder.toString())
                var jsonArray = result.getString("assets")
                val listType: Type = object : TypeToken<List<ModelOpenseaNft?>?>() {}.type
                val list: List<ModelOpenseaNft> = Gson().fromJson(jsonArray, listType)
                return Result.Success(list)
            } catch (e: java.lang.Exception) {
                return Result.Error(Exception("Cannot open HttpURLConnection"))
            }
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}
