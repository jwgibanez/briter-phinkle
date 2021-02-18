package com.example.demo

import android.app.Application
import com.example.db.AppDatabase
import com.example.db.repositories.CurrencyInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DemoApp : Application() {

    private val applicationScope by lazy { CoroutineScope(SupervisorJob()) }

    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val repository by lazy { CurrencyInfoRepository(database.currencyInfoDao()) }
}