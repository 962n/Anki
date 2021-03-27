package com.example.a962n.presentation.wordedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.presentation.wordedit.databinding.FragmentWordEditBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordEditFragment : Fragment() {

    @Inject
    lateinit var factory: WordEditViewModelFactory
    private lateinit var  viewModel : WordEditViewModel
    private lateinit var binding :FragmentWordEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWordEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_edit, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this,factory).get(WordEditViewModel::class.java)
        viewModel.event(this) {

        }
    }

    private fun initializeView(binding:FragmentWordEditBinding) {
        this.binding = binding
        this.binding.viewModel = viewModel

    }
}