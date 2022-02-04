package com.example.android.shift_currency_converter.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.shift_currency_converter.R
import com.example.android.shift_currency_converter.screens.currencyList.CurrencyListFragment
import com.example.android.shift_currency_converter.screens.currencyList.CurrencyListViewModel

class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CurrencyListFragment())
            .commit()

    }
}