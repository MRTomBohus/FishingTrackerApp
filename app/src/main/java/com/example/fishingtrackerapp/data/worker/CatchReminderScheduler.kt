package com.example.fishingtrackerapp.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * Schedules periodic background work related to fishing catch reminders.
 */
object CatchReminderScheduler {

    private const val WORK_NAME = "catch_reminder_work"

    fun schedule(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<CatchReminderWorker>(
            1,
            TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}