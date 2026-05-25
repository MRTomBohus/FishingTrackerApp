package com.example.fishingtrackerapp.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fishingtrackerapp.data.notification.NotificationHelper

/**
 * Background worker prepared for periodic fishing catch reminders.
 */
class CatchReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        NotificationHelper.showCatchReminder(applicationContext)
        return Result.success()
    }
}