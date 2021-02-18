package com.example.db.dao

import androidx.room.*
import com.example.db.entities.CurrencyInfo

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info")
    fun getAll(): List<CurrencyInfo>

    @Query("SELECT * FROM currency_info WHERE id IN (:currencyInfoIds)")
    fun loadAllByIds(currencyInfoIds: Array<String>): List<CurrencyInfo>

    @Query("SELECT * FROM currency_info WHERE id LIKE :string LIMIT 1")
    fun findById(string: String): CurrencyInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg currencyInfos: CurrencyInfo)

    @Delete
    fun delete(currencyInfo: CurrencyInfo)

    @Query("DELETE FROM currency_info")
    fun deleteAll()
}