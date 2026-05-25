package com.example.fishingtrackerapp

import android.app.Application
import androidx.room.Room
import com.example.fishingtrackerapp.data.local.FishingTrackerDatabase
import com.example.fishingtrackerapp.data.repository.CatchRepository
import com.example.fishingtrackerapp.data.repository.RoomCatchRepository
import com.example.fishingtrackerapp.data.worker.CatchReminderScheduler
import com.example.fishingtrackerapp.data.notification.NotificationHelper
import com.example.fishingtrackerapp.data.local.CatchEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Application class that initializes the Room database and repositories.
 */
class FishingTrackerApplication : Application() {

    private val database: FishingTrackerDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            FishingTrackerDatabase::class.java,
            "fishing_tracker_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    val catchRepository: CatchRepository by lazy {
        RoomCatchRepository(database.catchDao())
    }

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createNotificationChannel(this)
        CatchReminderScheduler.schedule(this)
        seedSampleCatchesIfNeeded()
    }

    private fun seedSampleCatchesIfNeeded() {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            val catchDao = database.catchDao()

            if (catchDao.getCatchCount() > 0) {
                return@launch
            }

            val now = System.currentTimeMillis()
            val day = 24L * 60L * 60L * 1000L

            val sampleCatches = listOf(
                CatchEntity(
                    fishType = "Kapor obyčajný",
                    weight = 7.4f,
                    length = 68f,
                    location = "VN Hričov",
                    bait = "Boilies",
                    date = now - day,
                    released = false,
                    note = "Ranný záber pri brehu."
                ),
                CatchEntity(
                    fishType = "Šťuka severná",
                    weight = 4.2f,
                    length = 82f,
                    location = "Váh č. 14",
                    bait = "Wobler",
                    date = now - 2 * day,
                    released = true,
                    note = "Ulovená na prívlač."
                ),
                CatchEntity(
                    fishType = "Pstruh potočný",
                    weight = 0.8f,
                    length = 36f,
                    location = "Varínka",
                    bait = "Muška",
                    date = now - 3 * day,
                    released = true,
                    note = "Pekne sfarbený pstruh."
                ),
                CatchEntity(
                    fishType = "Amur biely",
                    weight = 6.9f,
                    length = 74f,
                    location = "VN Divinka",
                    bait = "Kukurica",
                    date = now - 5 * day,
                    released = false,
                    note = "Záber počas teplého popoludnia."
                ),
                CatchEntity(
                    fishType = "Zubáč veľkoústy",
                    weight = 2.6f,
                    length = 58f,
                    location = "VN Hričov",
                    bait = "Gumená nástraha",
                    date = now - 7 * day,
                    released = false,
                    note = "Záber po zotmení."
                ),
                CatchEntity(
                    fishType = "Jalec hlavatý",
                    weight = 1.1f,
                    length = 42f,
                    location = "Kysuca č. 1",
                    bait = "Rotačka",
                    date = now - 9 * day,
                    released = true,
                    note = "Aktívny pri prúde."
                ),
                CatchEntity(
                    fishType = "Lipeň tymianový",
                    weight = 0.7f,
                    length = 34f,
                    location = "Rajčanka č. 1",
                    bait = "Suchá muška",
                    date = now - 11 * day,
                    released = true,
                    note = "Jemný záber na hladine."
                ),
                CatchEntity(
                    fishType = "Sumec veľký",
                    weight = 12.5f,
                    length = 116f,
                    location = "Váh č. 13",
                    bait = "Rybička",
                    date = now - 14 * day,
                    released = true,
                    note = "Najväčší úlovok v zozname."
                ),
                CatchEntity(
                    fishType = "Plotica červenooká",
                    weight = 0.3f,
                    length = 24f,
                    location = "Materiálová jama Celulózka",
                    bait = "Červík",
                    date = now - 16 * day,
                    released = false,
                    note = "Menší úlovok počas testovania plávanej."
                ),
                CatchEntity(
                    fishType = "Ostriež zelenkavý",
                    weight = 0.6f,
                    length = 31f,
                    location = "Štrkovisko Brodno",
                    bait = "Twister",
                    date = now - 20 * day,
                    released = true,
                    note = "Viac menších záberov za sebou."
                )
            )

            sampleCatches.forEach { catchEntity ->
                catchDao.insertCatch(catchEntity)
            }
        }
    }
}