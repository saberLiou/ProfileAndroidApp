package saberliou.demo.profile

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import saberliou.demo.profile.sleepqualitytracker.SleepNight
import saberliou.demo.profile.sleepqualitytracker.SleepNightDao
import java.io.IOException

@MediumTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppDatabaseTest {
    private lateinit var sleepNightDao: SleepNightDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        // Using an in-memory database because the information stored here disappears when the process is killed.
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()   // Allowing main thread queries, just for testing.
            .build()
        sleepNightDao = appDatabase.sleepNightDao
    }

    @Test
    @Throws(IOException::class)
    fun test01_createAndGetTheNight() = runBlocking {
        val expectedNight = SleepNight()
        sleepNightDao.insert(expectedNight)
        val actualNight = sleepNightDao.get(1)
        Assert.assertTrue(actualNight == expectedNight)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }
}
