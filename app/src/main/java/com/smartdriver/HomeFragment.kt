package com.smartdriver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smartdriver.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private var currentTimer: Timer? = null
    private var currentTimer2: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.status.value = TEXT_AMAN
        viewModel.first.observe(viewLifecycleOwner) {
            if(it == null) return@observe

            currentTimer?.cancel()
            val givenDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(it.timestamp)!!
            val currentDate = Date()
            val delayTime = givenDate.time - currentDate.time + 5 * 1000
            if (delayTime > 0) {
                viewModel.status.value = it.status
                currentTimer = Timer()
                currentTimer!!.schedule(object: TimerTask() {
                    override fun run() {
                        GlobalScope.launch(Dispatchers.Main) {
                            viewModel.status.value = TEXT_AMAN
                        }
                        currentTimer = null
                    }
                }, delayTime)
            } else {
                viewModel.status.value = TEXT_AMAN
            }
        }

        viewModel.lastConnectTime.observe(viewLifecycleOwner) {value ->
            val upDate = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(value)!!
            val diff = Date().time - upDate.time + 15 * 1000
            if (diff > 0) {
                binding.connectStatus.text = "Status: Terhubung\n" + value
                currentTimer2?.cancel()
                currentTimer2 = Timer()
                currentTimer2!!.schedule(object: TimerTask() {
                    override fun run() {
                        GlobalScope.launch(Dispatchers.Main) {
                            binding.connectStatus.text = "Status: Tidak Terhubung\n" + value
                        }
                        currentTimer2 = null
                    }
                }, diff)
            } else {
                binding.connectStatus.text = "Status: Tidak Terhubung\n" + value
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            binding.gambar.setImageResource(if(it == TEXT_AMAN) R.drawable.centang else R.drawable.silang)
        }

        return binding.root
    }

}