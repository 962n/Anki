package com.example.a962n.presentation.ankiswipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.presentation.ankiswipe.databinding.FragmentAnkiSwipeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AnkiSwipeFragment : Fragment() {

    private lateinit var binding: FragmentAnkiSwipeBinding

    @Inject
    lateinit var factory: AnkiSwipeViewModelFactory

    lateinit var viewModel: AnkiSwipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

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
        viewModel = ViewModelProvider(this, factory)
            .get(AnkiSwipeViewModel::class.java)
            .apply {
            }
    }

    private fun initializeView(binding: FragmentAnkiSwipeBinding) {
        this.binding = binding
    }

}