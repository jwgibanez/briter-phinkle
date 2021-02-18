package com.example.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db.entities.CurrencyInfo
import com.example.demo.databinding.FragmentCurrencyListBinding


class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        binding.currencies = ArrayList<CurrencyInfo>()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CurrencyListAdapter(binding.currencies!!)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return binding.root
    }

    fun update(currencies: List<CurrencyInfo>?) {
        binding.currencies?.apply {
            clear()
            currencies?.let {
                binding.currencies?.addAll(currencies)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    companion object {
        fun newInstance() : CurrencyListFragment {
            val fragment = CurrencyListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}