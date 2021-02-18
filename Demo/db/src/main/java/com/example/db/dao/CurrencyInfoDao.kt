package com.example.db.dao

import androidx.room.*
import com.example.db.entities.CurrencyInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {

    enum class OrderBy {
        ASC, DES
    }

    @Query("SELECT * FROM currency_info")
    fun getAll(): Flow<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info ORDER BY CASE WHEN :isAsc = 1 THEN name END ASC, CASE WHEN :isAsc = 0 THEN name END DESC")
    fun getAll(isAsc: Int): Flow<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info WHERE id IN (:currencyInfoIds)")
    fun loadAllByIds(currencyInfoIds: Array<String>): Flow<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info WHERE id LIKE :string LIMIT 1")
    fun findById(string: String): Flow<CurrencyInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg currencyInfos: CurrencyInfo)

    @Delete
    fun delete(currencyInfo: CurrencyInfo)

    @Query("DELETE FROM currency_info")
    fun deleteAll()
}