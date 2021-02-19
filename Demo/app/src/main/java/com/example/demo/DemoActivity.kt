package com.example.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoBinding

    private val currencyViewModel: CurrencyInfoViewModel by viewModels {
        CurrencyInfoViewModelFactory(application, (application as DemoApp).repository, binding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo)
        binding.viewModel = currencyViewModel

        val fragment = CurrencyListFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()

        currencyViewModel.getAllCurrencies().observe(this, { currencies ->
            if (currencies.isNotEmpty()) {
                fragment.update(currencies)
            } else {
                fragment.update(ArrayList())
            }
        })
    }
}