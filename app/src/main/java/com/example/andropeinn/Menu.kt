package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class Menu : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val viewPager:ViewPager=findViewById(R.id.viewPager)
        val tabLayout:TabLayout=findViewById(R.id.tabLayout)

        val adapter=PagerAdapter(supportFragmentManager)
        adapter.addFragment(Comida(), "Comida")
        adapter.addFragment(Vegetariana(), "Comida vegetariana")
        adapter.addFragment(Bebidas(), "Bebidas")

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)

        val btnIr=findViewById<FloatingActionButton>(R.id.btnIr)
        btnIr.setOnClickListener {
            startActivity(Intent(this, resumen_reserva::class.java))
        }

    }
}