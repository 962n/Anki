package com.example.a962n.presentation.wordlist

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.component.presentation.ext.observe
import com.example.a962n.anki.domain.core.simpleUseCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.wordlist.databinding.FragmentWordListBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordListFragment : Fragment() {
    private lateinit var binding: FragmentWordListBinding

    @Inject
    lateinit var navigator: WordListNavigator

    @Inject
    lateinit var factory: WordListViewModelFactory

    lateinit var viewModel: WordListViewModel
    private var adapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initialize()
        viewModel.fetchAll()
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

    private fun initialize() {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this, factory)
            .get(WordListViewModel::class.java)
            .apply {
                this.event(this@WordListFragment, ::handleEvents)
                observe(this.items, ::handleItems)
            }
        adapter.setOnItemLongClickListener { item, view ->
            when (item) {
                is WordListItem -> {
                    showPopupMenu(item, view)
                    true
                }
                else -> false
            }
        }
    }

    private fun initializeView(binding: FragmentWordListBinding) {
        this.binding = binding
        binding.swipeRefresh.apply {
            this.setOnRefreshListener {
                viewModel.fetchAll()
            }
        }
        binding.recyclerView.apply {
            this.adapter = this@WordListFragment.adapter
            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    private fun handleEvents(event: BaseViewModel.ViewModelEvent) {
        when (event) {
            is Event.Loading -> {
                binding.swipeRefresh.isRefreshing = event.isLoading
            }
            is Event.DeletedItem -> {
                adapter.remove(WordListItem(event.entity))
            }
            else -> {
                // do nothing
            }
        }
    }

    private fun handleItems(items: List<WordEntity>) {
        simpleUseCase(items)
            .onExecute { list ->
                list.map { WordListItem(it) }
            }.onComplete {
                adapter.clear()
                adapter.addAll(it)
            }.run()
    }

    private fun showPopupMenu(item: WordListItem, anchor: View) {
        val context = this.context ?: return
        PopupMenu(context, anchor).apply {
            this.inflate(R.menu.fragment_word_list_popup)
            this.gravity = Gravity.END
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit_word -> {
                        true
                    }
                    R.id.menu_delete_word -> {
                        viewModel.deleteItem(item.wordEntity)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }


}