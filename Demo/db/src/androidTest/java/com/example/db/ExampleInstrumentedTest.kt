package com.example.db

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.db.dao.CurrencyInfoDao
import com.example.db.entities.CurrencyInfo
import org.json.JSONArray
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.io.InputStream

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun currencyInfoDao() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

        val db = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "database-name"
        ).build()

        val currencyInfoDao = db.currencyInfoDao()

        currencyInfoDao.deleteAll()
        assertEquals(0, currencyInfoDao.getAll().size)

        prepareDb(appContext, currencyInfoDao)
        assertEquals(14, currencyInfoDao.getAll().size)

        val cro: CurrencyInfo? = currencyInfoDao.findById("CRO")
        assertNotNull(cro)
        assertEquals("CRO", cro!!.id)
        assertEquals("Crypto.com Chain", cro.name)
        assertEquals("CRO", cro.symbol)

        currencyInfoDao.delete(cro)
        assertNull(currencyInfoDao.findById("CRO"))
        assertEquals(13, currencyInfoDao.getAll().size)

        currencyInfoDao.deleteAll()
        assertEquals(0, currencyInfoDao.getAll().size)
    }

    private fun prepareDb(context: Context, currencyInfoDao: CurrencyInfoDao) {
        val data = JSONArray(loadJSONFromAsset(context))
        for (i in 0 until data.length()) {
            val info = data.getJSONObject(i)
            currencyInfoDao.insertAll(
                CurrencyInfo(
                    id = info.getString("id"),
                    name = info.getString("name"),
                    symbol = info.getString("symbol")
                ))
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