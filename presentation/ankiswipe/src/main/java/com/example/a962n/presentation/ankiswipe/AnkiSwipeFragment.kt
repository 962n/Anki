package com.example.a962n.presentation.ankiswipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.component.presentation.ext.observe
import com.example.a962n.anki.domain.entity.WordEntity
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_anki_swipe, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_shuffle_word -> {
                viewModel.shuffle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initialize() {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this, factory)
            .get(AnkiSwipeViewModel::class.java)
            .apply {
                this.event(this@AnkiSwipeFragment, ::handleEvent)
                observe(this.item, ::handleItems)
            }
    }

    private fun initializeView(binding: FragmentAnkiSwipeBinding) {
        this.binding = binding
    }

    private fun handleEvent(event: BaseViewModel.ViewModelEvent) {

    }

    private fun handleItems(items: List<WordEntity>) {
        items.forEach {
            Log.d("kurokawa", it.id.toString() + " " + it.name)
        }

    }

}