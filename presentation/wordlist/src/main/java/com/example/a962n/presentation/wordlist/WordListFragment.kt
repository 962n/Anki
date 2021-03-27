package com.example.a962n.presentation.wordlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.a962n.presentation.wordlist.databinding.FragmentWordListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : Fragment() {

    private lateinit var binding: FragmentWordListBinding

    @Inject
    lateinit var navigator: WordListNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordListBinding.inflate(inflater, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initializeView(binding: FragmentWordListBinding) {
        this.binding = binding
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_word_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_word -> {
                navigator.toWordEdit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}