package com.example.demo

import android.app.Application
import androidx.lifecycle.*
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import com.example.db.repositories.CurrencyInfoRepository
import com.example.demo.databinding.ActivityDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CurrencyInfoViewModel(
    application: Application,
    private val repository: CurrencyInfoRepository,
    private val binding: ActivityDemoBinding
) : AndroidViewModel(application) {

    init {
        // Start with empty db
        clearDbCurrencies()
    }

    private var orderBy: CurrencyInfoDao.OrderBy? = null

    private val allCurrencies = MutableLiveData<List<CurrencyInfo>>()

    fun getAllCurrencies(): LiveData<List<CurrencyInfo>> = allCurrencies

    fun loadDbCurrencies() {
        // Toggle between sort each time
        orderBy = when (orderBy) {
            CurrencyInfoDao.OrderBy.ASC -> CurrencyInfoDao.OrderBy.DES
            else -> CurrencyInfoDao.OrderBy.ASC
        }

        binding.orderBy = orderBy

        viewModelScope.launch(Dispatchers.IO) {
            if (allCurrencies.value?.isEmpty() == true) {
                repository.loadDefaultCurrencies(getApplication())
            }
            allCurrencies.postValue(repository.getAllCurrencies(orderBy!!).first())
        }
    }

    fun clearDbCurrencies() {
        // Reset sort
        orderBy = null
        binding.orderBy = orderBy

        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
            allCurrencies.postValue(repository.allCurrencies.first())
        }
    }
}