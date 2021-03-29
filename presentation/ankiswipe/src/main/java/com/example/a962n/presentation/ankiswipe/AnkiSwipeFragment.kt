package com.example.a962n.presentation.ankiswipe

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.simpleUseCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.ankiswipe.databinding.FragmentAnkiSwipeBinding
import com.xwray.groupie.GroupieAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AnkiSwipeFragment : Fragment() {

    private lateinit var binding: FragmentAnkiSwipeBinding

    @Inject
    lateinit var factory: AnkiSwipeViewModelFactory

    lateinit var viewModel: AnkiSwipeViewModel
    private val adapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        viewModel.fetchAll()
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
            }
    }

    private fun initializeView(binding: FragmentAnkiSwipeBinding) {
        this.binding = binding
        this.binding.cardStackView.apply {
            val listener = object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {}
                override fun onCardRewound() {}
                override fun onCardCanceled() {}
                override fun onCardAppeared(view: View?, position: Int) {}
                override fun onCardDisappeared(view: View?, position: Int) {}
                override fun onCardSwiped(direction: Direction?) {
                    viewModel.swipe(true)
                }
            }
            this.layoutManager = CardStackLayoutManager(this.context, listener).apply {
                this.setStackFrom(StackFrom.Top)
            }
            this.adapter = this@AnkiSwipeFragment.adapter
        }
    }

    private fun handleEvent(event: BaseViewModel.ViewModelEvent) {
        when (event) {
            is Event.Swiped -> adapter.remove(AnkiSwipeItem(event.target))
            is Event.Fetched -> handleFetchItems(event.items)
            else -> {
                //do nothing
            }
        }
    }

    private fun handleFetchItems(items: List<WordEntity>) {
        simpleUseCase(items)
            .onExecute { list ->
                list.map { AnkiSwipeItem(it) }
            }.onComplete {
                adapter.clear()
                adapter.addAll(it)
            }.run()
    }

}