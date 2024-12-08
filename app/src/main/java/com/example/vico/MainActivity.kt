package com.example.vico

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize binding
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnChart1.setOnClickListener {
            val intent = Intent(this, Chart1Activity::class.java)
            startActivity(intent)
        }

        binding.btnChart2.setOnClickListener {
            val intent = Intent(this, Chart2Activity::class.java)
            startActivity(intent)
        }

        //chart10
        binding.btnChart3.setOnClickListener {
            val intent = Intent(this, Chart3Activity::class.java)
            startActivity(intent)
        }
        //chart4
        binding.btnChart4.setOnClickListener {
            val intent = Intent(this, Chart4Activity::class.java)
            startActivity(intent)
        }
        //chart5
        binding.btnChart5.setOnClickListener {
            val intent = Intent(this, Chart5Activity::class.java)
            startActivity(intent)
        }

    }
}