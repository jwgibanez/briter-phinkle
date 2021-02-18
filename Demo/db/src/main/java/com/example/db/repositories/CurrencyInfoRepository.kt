package com.example.db.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.db.AppDatabase
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import kotlinx.coroutines.flow.Flow

class CurrencyInfoRepository(private val dao: CurrencyInfoDao) {

    var allCurrencies: Flow<List<CurrencyInfo>> = dao.getAll(1)

    @WorkerThread
    fun getAllCurrencies(orderBy: CurrencyInfoDao.OrderBy): Flow<List<CurrencyInfo>> {
        return when(orderBy) {
            CurrencyInfoDao.OrderBy.DES -> dao.getAll(0)
            CurrencyInfoDao.OrderBy.ASC -> dao.getAll(1)
        }
    }

    @WorkerThread
    fun loadDefaultCurrencies(context: Context) {
        AppDatabase.prepareDb(context, dao)
    }

    @WorkerThread
    fun get(id: String): Flow<CurrencyInfo?> {
        return dao.findById(id)
    }

    @WorkerThread
    fun insert(currencyInfo: CurrencyInfo) {
        return dao.insertAll(currencyInfo)
    }

    @WorkerThread
    fun remove(currencyInfo: CurrencyInfo) {
        dao.delete(currencyInfo)
    }

    @WorkerThread
    fun clear() {
        dao.deleteAll()
    }
}