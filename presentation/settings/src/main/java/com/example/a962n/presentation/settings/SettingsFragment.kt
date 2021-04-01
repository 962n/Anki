package com.example.a962n.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.presentation.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var viewModel: SettingsViewModel

    @Inject
    lateinit var navigator: SettingsNavigator

    @Inject
    lateinit var factory: SettingsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this, factory)
            .get(SettingsViewModel::class.java).apply {
                this.event(this@SettingsFragment) {

                }
            }

    }

    private fun initializeView(binding: FragmentSettingsBinding) {
        this.binding = binding
    }

}