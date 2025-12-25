package com.commondnd.data.sync

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.commondnd.data.core.Synchronizable
import com.commondnd.ui.sync.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val synchronizables: Set<@JvmSuppressWildcards Synchronizable>
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        ForegroundInfo(
            SYNC_NOTIFICATION_ID,
            syncWorkNotification(appContext)
        )

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val syncedSuccessfully = awaitAll(
            *synchronizables.map { async { it.synchronize() } }.toTypedArray()
        ).all { it }

        if (syncedSuccessfully) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun syncWorkNotification(context: Context): Notification {
        val channel = NotificationChannel(
            SYNC_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.sync_work_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = context.getString(R.string.sync_work_notification_channel_description)
        }
        // Register the channel with the system
        val notificationManager: NotificationManager? =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)


        return NotificationCompat.Builder(
            context,
            SYNC_NOTIFICATION_CHANNEL_ID,
        )
            .setSmallIcon(
                com.commondnd.ui.core.R.drawable.logo_bright_dawn_bw
            )
            .setContentTitle(context.getString(R.string.sync_work_notification_title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    companion object {

        private const val SYNC_NOTIFICATION_ID = 31120
        private const val SYNC_NOTIFICATION_CHANNEL_ID = "CommonDungeonSyncNotificationChannel"
        
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
    }
}
