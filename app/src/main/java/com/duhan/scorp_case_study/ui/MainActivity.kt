package com.duhan.scorp_case_study.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.duhan.scorp_case_study.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}