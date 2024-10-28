package com.nativeNomads.workhive

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nativeNomads.workhive.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var startBinding:ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startBinding=ActivityStartBinding.inflate(layoutInflater)

        setContentView(startBinding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        startBinding.startButton.setOnClickListener {
            val intent= Intent(this@StartActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}