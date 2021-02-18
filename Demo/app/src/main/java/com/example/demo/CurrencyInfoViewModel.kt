package com.example.demo

import android.app.Application
import androidx.lifecycle.*
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import com.example.db.repositories.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyInfoViewModel(
    application: Application,
    private val repository: CurrencyInfoRepository,
) : AndroidViewModel(application) {

    init {
        // Start with empty db
        clearDbCurrencies()
    }

    var orderBy = CurrencyInfoDao.OrderBy.ASC

    var allCurrencies: LiveData<List<CurrencyInfo>> = repository.getAllCurrencies(orderBy).asLiveData()

    fun loadDbCurrencies() {
        if (orderBy == CurrencyInfoDao.OrderBy.DES) orderBy = CurrencyInfoDao.OrderBy.ASC
        else if (orderBy == CurrencyInfoDao.OrderBy.ASC) orderBy = CurrencyInfoDao.OrderBy.DES
        viewModelScope.launch(Dispatchers.IO) {
            if (allCurrencies.value?.isEmpty() == true) {
                repository.loadDefaultCurrencies(getApplication())
            }
            allCurrencies
        }
    }

    fun clearDbCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
        }
    }
}