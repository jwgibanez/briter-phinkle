package com.example.demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.db.entities.CurrencyInfo
import com.example.demo.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity(), CurrencyListAdapter.Listener {

    private lateinit var binding: ActivityDemoBinding

    private val currencyViewModel: CurrencyInfoViewModel by viewModels {
        CurrencyInfoViewModelFactory(application, (application as DemoApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo)
        binding.viewModel = currencyViewModel

        val fragment = CurrencyListFragment().apply {
            listener = this@DemoActivity
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()

        currencyViewModel.apply {
            allCurrencies.observe(this@DemoActivity, {
                if (it.isNotEmpty()) {
                    fragment.update(it)
                } else {
                    fragment.update(ArrayList())
                }
            })

            orderBy.observe(this@DemoActivity, { binding.orderBy = it })
        }
    }

    override fun onItemClicked(currency: CurrencyInfo) {
        Toast.makeText(this, "Clicked ${currency.symbol}", Toast.LENGTH_SHORT).show()
    }
}