package com.example.az_travel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.az_travel.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.activity_main, null, false)

        binding.mainPager.adapter = object: FragmentStateAdapter(this) {
            val homeFragment = HomeFragment()
            val historyFragment = HistoryFragment()

            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when(position) {
                    0 -> homeFragment
                    else -> historyFragment
                }
            }

        }

        TabLayoutMediator(binding.mainTabs, binding.mainPager) {tabs, position ->
            tabs.text = when(position) {
                0 -> "Beranda"
                else -> "Reservasi"
            }
            tabs.setIcon(when(position) {
                0 -> R.drawable.logo_beranda
                else -> R.drawable.logo_histori
            })
        }.attach()

        setContentView(binding.root)
    }
}