package com.sential.discordbubbles

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.*
import android.content.IntentFilter

class OverlayService : Service() {
    companion object {
        lateinit var instance: OverlayService
    }

    lateinit var windowManager: WindowManager
    lateinit var chatHeads: ChatHeads

    override fun onCreate() {
        super.onCreate()

        instance = this

        var client = Client()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        chatHeads = ChatHeads(this)

        val innerReceiver = InnerReceiver()
        val intentFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        registerReceiver(innerReceiver, intentFilter)

        chatHeads.add("test")
        val msg = Message("test", "test", "test")
        chatHeads.topChatHead!!.messages.add(msg)
        chatHeads.changeContent()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

internal class InnerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS == action) {
            val reason = intent.getStringExtra("reason")
            if (reason != null) {
                // OverlayService.instance.hide()
            }
        }
    }
}