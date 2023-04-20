package com.example.boundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService : Service() {
    private var mBinder = MyBinder()
    private val TAG = "BoundService"
    lateinit var mediaPlayer: MediaPlayer

    inner class MyBinder : Binder() {
        fun getMusic(): BoundService {
            return this@BoundService
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        mediaPlayer = MediaPlayer()
        startMusic()
    }


    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return mBinder;
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        mediaPlayer?.let {
            it.release()
        }
    }

    fun startMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.super_start)
        mediaPlayer.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }
}