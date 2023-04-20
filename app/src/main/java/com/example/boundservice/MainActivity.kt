package com.example.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.boundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var boundService: BoundService
    private var isServiceConnected = false
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var myBinder:BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getMusic()
//            boundService.startMusic()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartService.setOnClickListener { startService() }
        binding.buttonStopService.setOnClickListener { stopService() }
    }

    private fun stopService() {
        if (isServiceConnected){
            unbindService(serviceConnection)
            isServiceConnected = false
        }
    }

    private fun startService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE )
    }
}