package saberliou.demo.profile.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AppDatabaseRule(val afterAppDatabaseCreated: (AppDatabase) -> Unit) : TestWatcher() {
    private lateinit var appDatabase: AppDatabase

    /**
     * Invoked when a test is about to start in @Before.
     */
    override fun starting(description: Description?) {
        super.starting(description)
        // Using an in-memory database because the information stored here disappears when the process is killed.
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()   // Allowing main thread queries, just for testing.
            .build()

        afterAppDatabaseCreated(appDatabase)
    }

    /**
     * Invoked when a test method finishes (whether passing or failing) in @After.
     */
    override fun finished(description: Description?) {
        super.finished(description)
        appDatabase.close()
    }
}