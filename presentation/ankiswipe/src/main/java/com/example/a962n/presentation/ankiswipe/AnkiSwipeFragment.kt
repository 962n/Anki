package com.example.a962n.presentation.ankiswipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a962n.presentation.ankiswipe.databinding.FragmentAnkiSwipeBinding

class AnkiSwipeFragment : Fragment() {

    private lateinit var binding: FragmentAnkiSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnkiSwipeBinding.inflate(inflater, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initialize() {

    }

    private fun initializeView(binding: FragmentAnkiSwipeBinding) {
        this.binding = binding
    }

}