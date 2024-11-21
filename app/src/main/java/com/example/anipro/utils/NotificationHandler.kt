package com.example.anipro.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.anipro.R
import com.example.anipro.ui.activity.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class NotificationHandler : BroadcastReceiver() {
    companion object {
        const val MY_CHANNEL_ID = "animeNotifications"

        fun createChannel(@ApplicationContext context: Context) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "animeNotifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "animes pendientes"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        fun scheduleNotification(context: Context,animeId :Int,animeName:String, time: Long, repeat : Boolean,endDate: LocalDate) {
            val intent = Intent(context, NotificationHandler::class.java)
            var repeatNotification = repeat
            val date = LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(time), ZoneId.systemDefault())
            if (repeatNotification) {
                repeatNotification = !date.toLocalDate().isEqual(endDate)
            }


            intent.putExtras(Bundle().apply {
                putInt("animeId", animeId)
                putBoolean("repeat", repeatNotification)
                putString("endDate", endDate.toString())
                putString("animeName", animeName)
            })

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                animeId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )



            Log.d("NotificationHandler", "date: $date")

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                when {
                    alarmManager.canScheduleExactAlarms() -> {
                        Log.d("NotificationHandler", "Alarm scheduled")
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                             time,
                            pendingIntent
                        )
                    }

                    else -> {
                        Log.d("NotificationHandler", "Requesting exact alarm permission")
                        startActivity(
                            context,
                            Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                            null
                        )
                    }
                }
            } else {
                Log.d("NotificationHandler", "Alarm scheduled for pre-S devices")
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                )
            }

        }

    }


    private fun createNotification(context: Context, textTitle: String, textContent: String , notificationId:Int) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_app)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotificationHandler", "Notification received")
        val animeId = intent?.extras?.getInt("animeId")
        val animeName = intent?.extras?.getString("animeName")
        val repeat = intent?.extras?.getBoolean("repeat")
        val animeText = if (repeat == true) "Nuevo capitulo de $animeName" else "Termino de $animeName"
        createNotification(context!!, "AnimePro", animeText, animeId!!)
        if (repeat == true) {
            scheduleNotification(
                context,
                animeId,
                animeName!!,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusHours(9).plusDays(7).toInstant().toEpochMilli(),
                true,
                LocalDate.parse(intent.extras?.getString("endDate"))
                )
        }

    }
}