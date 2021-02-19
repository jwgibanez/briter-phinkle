package com.example.demo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.db.repositories.CurrencyInfoRepository
import com.example.demo.databinding.ActivityDemoBinding

class CurrencyInfoViewModelFactory(
    private val application: Application,
    private val repository: CurrencyInfoRepository,
    private val binding: ActivityDemoBinding
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyInfoViewModel(application, repository, binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}