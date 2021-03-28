package com.example.a962n.presentation.wordedit

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.ext.hideIME
import com.example.a962n.presentation.wordedit.databinding.FragmentWordEditBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordEditFragment : Fragment() {

    @Inject
    lateinit var factory: WordEditViewModelFactory
    private lateinit var viewModel: WordEditViewModel
    private lateinit var binding: FragmentWordEditBinding

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

    override fun onDestroyView() {
        this.view?.hideIME()
        super.onDestroyView()
    }

    private fun initialize() {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this, factory)
            .get(WordEditViewModel::class.java).apply {
                this.event(this@WordEditFragment) {

                }
            }
    }

    private fun initializeView(binding: FragmentWordEditBinding) {
        this.binding = binding
        this.binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_word_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_done -> {
                viewModel.editWord()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}