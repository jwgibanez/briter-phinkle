package com.example.demo

import android.app.Application
import androidx.lifecycle.*
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import com.example.db.repositories.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CurrencyInfoViewModel(
    application: Application,
    private val repository: CurrencyInfoRepository,
) : AndroidViewModel(application) {

    var orderBy = MutableLiveData<CurrencyInfoDao.OrderBy?>()

    val allCurrencies = MutableLiveData<List<CurrencyInfo>>()

    init {
        // Start with empty db
        clearDbCurrencies()
    }

    fun loadDbCurrencies() {
        // Toggle between sort each time
        orderBy.value = when (orderBy.value) {
            CurrencyInfoDao.OrderBy.ASC -> CurrencyInfoDao.OrderBy.DES
            else -> CurrencyInfoDao.OrderBy.ASC
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (allCurrencies.value?.isEmpty() == true) {
                repository.loadDefaultCurrencies(getApplication())
            }
            allCurrencies.postValue(repository.getAllCurrencies(orderBy.value!!).first())
        }
    }

    fun clearDbCurrencies() {
        // Reset sort
        orderBy.value = null

        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
            allCurrencies.postValue(repository.allCurrencies.first())
        }
    }
}