package com.example.a962n.presentation.wordlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a962n.presentation.wordlist.databinding.FragmentWordListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : Fragment() {

    private lateinit var binding: FragmentWordListBinding
    @Inject
    lateinit var navigator : WordListNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWordListBinding.inflate(inflater, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initializeView(binding: FragmentWordListBinding) {
        this.binding = binding
        this.binding.button.setOnClickListener {
            navigator.toWordEdit()
        }
    }

}