package com.louis.portto_examination.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.louis.ModelOpenseaNft
import com.louis.portto_examination.RepositoryOpensea
import com.louis.portto_examination.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.core.DefaultBlockParameterName

import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger


class MainViewModel : ViewModel() {
    internal var liveDataNftList = MutableLiveData<List<ModelOpenseaNft>>()
    internal var liveDataNetworkError = MutableLiveData<String>()
    internal var liveDataEthBalance = MutableLiveData<BigDecimal>()
    private var web3: Web3j? =
        Web3j.build(HttpService("https://mainnet.infura.io/v3/4bbdff77715a4c7789db07277f8135e8"))


    fun getEthBalance() {
        CoroutineScope(Dispatchers.IO).launch {
            var balance: EthGetBalance? = null
            web3?.apply {
                balance = ethGetBalance(
                    "0x19818f44Faf5A217F619AFF0FD487CB2a55cCa65",
                    DefaultBlockParameterName.LATEST
                ).sendAsync().get()
            }
            withContext(Dispatchers.Main) {
                balance?.apply {
                    var balance = getBalance().toString()
                    liveDataEthBalance.postValue(Convert.fromWei(balance,Convert.Unit.ETHER))
                }
            }
        }

    }

    fun queryOpenseaNftList(offset: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = RepositoryOpensea().getNftList(offset)
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        liveDataNftList.postValue(result.data)
                    }
                    is Result.Error -> {
                        liveDataNetworkError.postValue(result.exception.message)
                    }
                }
            }
        }
    }
}