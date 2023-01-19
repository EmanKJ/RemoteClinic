package com.example.remoteclinic_firstdraft.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}