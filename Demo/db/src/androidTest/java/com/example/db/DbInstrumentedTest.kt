package com.example.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.db.entities.CurrencyInfo
import com.example.db.repositories.CurrencyInfoRepository
import junit.framework.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DbInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: CurrencyInfoRepository

    @Before
    fun init() {
        val applicationScope = CoroutineScope(SupervisorJob())
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        database = AppDatabase.getDatabase(appContext, applicationScope)
        repository = CurrencyInfoRepository(database.currencyInfoDao())
        AppDatabase.prepareDb(appContext, database.currencyInfoDao())
    }

    @Test
    fun currencyInfoDao() = runBlocking {

        val test = repository.allCurrencies.first()

        assertEquals(14, test.size)

        val cro: CurrencyInfo? = repository.get("CRO").first()
        assertNotNull(cro)
        assertEquals("CRO", cro!!.id)
        assertEquals("Crypto.com Chain", cro.name)
        assertEquals("CRO", cro.symbol)

        repository.remove(cro)
        assertNull(repository.get("CRO").first())
        assertEquals(13, repository.allCurrencies.first().size)

        repository.clear()
        assertEquals(0, repository.allCurrencies.first().size)
    }

    @After
    fun tearDown() {
        database.close()
    }
}