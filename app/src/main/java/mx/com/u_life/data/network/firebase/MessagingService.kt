package mx.com.u_life.data.network.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.presentation.MainActivity

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: "New Message"
        val body = message.notification?.body ?: "You have a new message"

        showNotification(title, body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToServer(token)
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "real_time_messages"
        val channelName = "Real-time Messages"

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Real-time messages notifications"
        }

        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            //.setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun sendTokenToServer(token: String) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser

        currentUser?.uid?.let { userId ->
            firestore
                .collection(Constants.USERS_COLLECTION)
                .document(userId)
                .update(Constants.USER_FIELD_FCMTOKEN, token)
                .addOnSuccessListener {
                    Log.d("MessagingService", "Token actualizado: $token")
                }
                .addOnFailureListener { e ->
                    Log.e("MessagingService", "Error al actualizar el token", e)
                }
        }
    }
}