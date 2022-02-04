package com.example.android.shift_currency_converter.data.api

import com.example.android.shift_currency_converter.model.CurrencyList
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApi {

    @GET("./daily_json.js")
    fun getCurrencyList() : Single<CurrencyList>
}