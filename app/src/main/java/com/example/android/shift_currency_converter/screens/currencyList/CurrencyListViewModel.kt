package com.example.android.shift_currency_converter.screens.currencyList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.shift_currency_converter.data.api.RetrofitInstance
import com.example.android.shift_currency_converter.model.CurrencyList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyListViewModel : ViewModel() {

    val currencyListLiveData = MutableLiveData<CurrencyList>()
    val amountInRubles = MutableLiveData<String>()
    var amountInCurrency = MutableLiveData<String>()
    var currencyCode = MutableLiveData<String>()

    init {
        perUpdateCurrencyList()
    }

    fun fetchCurrencyList() {
        RetrofitInstance.currencyApi.getCurrencyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currencyListLiveData.value = it
            }, {

            })
    }

    private fun perUpdateCurrencyList() {
        RetrofitInstance.currencyApi.getCurrencyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .repeatWhen { completed -> completed.delay(15, TimeUnit.SECONDS) }
            .subscribe({
                currencyListLiveData.value = it
            }, {

            })

    }

    fun convertCurrency(sum: String, code: String) {
        amountInRubles.value = sum
        currencyCode.value = code
        val nominal = currencyListLiveData.value?.valute?.get(code)?.nominal
        val rate = currencyListLiveData.value?.valute?.get(code)?.value
        if (nominal != null) {
            if (rate != null) {
                amountInCurrency.value = "%.4f".format(sum.toFloat() * nominal / rate)
            }
        }
    }
}
