package com.example.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyInfoDao(): CurrencyInfoDao

    private class WordDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.currencyInfoDao())
                }
            }
        }

        fun populateDatabase(currencyInfoDao: CurrencyInfoDao) {
            //prepareDb(context, currencyInfoDao)
        }
    }

    companion object {
        // Singleton prevents multiple instances
        // of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "currency_info_database")
                    .addCallback(WordDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun prepareDb(context: Context, currencyInfoDao: CurrencyInfoDao) {
            val data = JSONArray(loadJSONFromAsset(context))
            for (i in 0 until data.length()) {
                val info = data.getJSONObject(i)
                currencyInfoDao.insertAll(
                    CurrencyInfo(
                        id = info.getString("id"),
                        name = info.getString("name"),
                        symbol = info.getString("symbol")
                    )
                )
            }
        }

        private fun loadJSONFromAsset(context: Context): String? {
            return try {
                val stream: InputStream = context.assets.open("currencies.json")
                val size: Int = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
        }
    }
}