package saberliou.demo.profile

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import saberliou.demo.profile.sleepqualitytracker.SleepNight
import saberliou.demo.profile.sleepqualitytracker.SleepNightDao
import java.io.IOException

@RunWith(AndroidJUnit4::class)
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
    fun createAndGetTheNight() {
        val expectedNight = SleepNight()
        sleepNightDao.insert(expectedNight)
        val actualNight = sleepNightDao.getNight(1)
        Assert.assertTrue(actualNight == expectedNight)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }
}