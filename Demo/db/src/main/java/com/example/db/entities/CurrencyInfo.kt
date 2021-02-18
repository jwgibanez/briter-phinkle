package com.example.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info")
data class CurrencyInfo(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String?,
    @ColumnInfo val symbol: String?
)