package com.example.android.shift_currency_converter.model

import com.google.gson.annotations.SerializedName

data class CurrencyList(

    @SerializedName("Valute")
    val valute: MutableMap<String, CurrencyParameters>

)


data class CurrencyParameters(

    @SerializedName("CharCode")
    val charCode: String,

    @SerializedName("Nominal")
    val nominal: Int,

    @SerializedName("Name")
    val name: String,

    @SerializedName("Value")
    val value: Float,

    @SerializedName("Previous")
    val previous: Float

)