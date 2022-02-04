package com.example.android.shift_currency_converter.screens.currencyList

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.shift_currency_converter.R
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener


class CurrencyListFragment : Fragment(R.layout.fragment_currency_list) {

    private var recyclerAdapter = RecyclerAdapter()
    private lateinit var currencyListViewModel: CurrencyListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var valueOfSelectedSpinnerItem: String
    private lateinit var currencyCodeList: List<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyListViewModel =
            ViewModelProvider(requireActivity())[CurrencyListViewModel::class.java]

        swipeRefreshLayout =
            view.findViewById(R.id.swipeRefreshLayout);
        val editTextSum: EditText = view.findViewById(R.id.et_sum_field_value)
        val editTextResult: EditText = view.findViewById(R.id.et_convert_result)
        val buttonConvert: Button = view.findViewById(R.id.bt_convert_currency)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_currency_list)
        val spinner: Spinner = view.findViewById(R.id.sp_currency_items)
        recyclerView.layoutManager = LinearLayoutManager(view.context)




        currencyListViewModel.currencyListLiveData.observe(viewLifecycleOwner, {
            recyclerAdapter.updateData(it)
            if (recyclerView.adapter == null) {
                recyclerView.adapter = recyclerAdapter
            }
            currencyCodeList = it.valute.keys.toList()
            val adapter =
                ArrayAdapter(view.context, android.R.layout.simple_spinner_item, currencyCodeList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            if (spinner.adapter == null) spinner.adapter = adapter

        })

        currencyListViewModel.amountInCurrency.observe(viewLifecycleOwner, {
            editTextResult.setText(it)
        })

        currencyListViewModel.amountInRubles.observe(viewLifecycleOwner, {
            editTextSum.setText(it)
        })

        currencyListViewModel.currencyCode.observe(viewLifecycleOwner, {
            valueOfSelectedSpinnerItem = it
            spinner.setSelection(currencyCodeList.indexOf(valueOfSelectedSpinnerItem))
        })



        buttonConvert.setOnClickListener {
            val sum = if (editTextSum.text.isEmpty()) "0" else editTextSum.text.toString()
            currencyListViewModel.convertCurrency(
                sum = sum,
                code = valueOfSelectedSpinnerItem
            )

        }


        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                valueOfSelectedSpinnerItem = currencyCodeList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            currencyListViewModel.fetchCurrencyList()
            swipeRefreshLayout.isRefreshing = false
        }

    }

}