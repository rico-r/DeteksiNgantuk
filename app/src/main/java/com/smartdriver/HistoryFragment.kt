package com.smartdriver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smartdriver.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        adapter = MyListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentHistoryBinding>(
            inflater, R.layout.fragment_history, container, false
        )
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter
        viewModel.firstN.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

}