package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}