package com.example.linksaveai.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.util.regex.Pattern

class LinkSafeNotificationService : NotificationListenerService() {

    companion object {
        private const val TAG = "LinkSafeNotification"
        private val URL_PATTERN = Pattern.compile(
            "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            Pattern.CASE_INSENSITIVE
        )
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn ?: return

        val extras = sbn.notification?.extras ?: return
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
            ?: extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
            ?: return

        val packageName = sbn.packageName ?: "Unknown"
        if (isSupportedApp(packageName)) {
            val matcher = URL_PATTERN.matcher(text)
            while (matcher.find()) {
                val url = matcher.group()
                Log.d(TAG, "Captured URL from $packageName: $url")
                SharedLinkHolder.onUrlCaptured(url, packageName)
            }
        }
    }

    private fun isSupportedApp(packageName: String): Boolean {
        val supported = listOf(
            "com.whatsapp",
            "com.google.android.apps.messaging",
            "com.facebook.orca",
            "com.telegram.messenger",
            "org.telegram.messenger",
            "com.android.mms"
        )
        return supported.any { packageName.contains(it, ignoreCase = true) }
    }
}

object SharedLinkHolder {
    var listener: ((String, String) -> Unit)? = null

    fun onUrlCaptured(url: String, source: String) {
        listener?.invoke(url, source)
    }
}
