package com.example.a962n.presentation.ankiswipe

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.simpleUseCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.ankiswipe.databinding.FragmentAnkiSwipeBinding
import com.xwray.groupie.GroupieAdapter
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AnkiSwipeFragment : Fragment() {

    private lateinit var binding: FragmentAnkiSwipeBinding

    @Inject
    lateinit var factory: AnkiSwipeViewModelFactory

    private lateinit var viewModel: AnkiSwipeViewModel
//    private var adapter = GroupieAdapter()
    private var adapter = AnkiSwipeAdapter()
    private val bindItems = mutableListOf<AnkiSwipeItem>()
    private lateinit var cardStackLayoutManager: CardStackLayoutManager

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
//        adapter = GroupieAdapter().apply {
//            this.addAll(bindItems)
//        }
        binding.cardStackView.apply {
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
            cardStackLayoutManager = CardStackLayoutManager(this.context, listener).apply {
                this.setStackFrom(StackFrom.Top)
            }
            this.layoutManager = cardStackLayoutManager
            this.adapter = this@AnkiSwipeFragment.adapter
        }

        binding.buttonWrong.setOnClickListener {
            onClickSwipeButton(false)
        }

        binding.buttonCorrect.setOnClickListener {
            onClickSwipeButton(true)
        }

        binding.buttonTurnOver.setOnClickListener {
            when (adapter.itemCount > 0) {
                true -> {
//                    val item = adapter.getItem(0)
//                    if (item is AnkiSwipeItem) {
//                        item.turnOver()
//                    }
                }
                false -> {
                    // do nothing
                }
            }

        }
    }
    private fun onClickSwipeButton(correct:Boolean) {
        val direction = when(correct) {
            true -> Direction.Right
            false -> Direction.Left
        }
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        cardStackLayoutManager.setSwipeAnimationSetting(setting)
        binding.cardStackView.swipe()
    }

    private fun handleEvent(event: BaseViewModel.ViewModelEvent) {
        when (event) {
            is Event.Swiped -> {
                bindItems.removeAt(0)
            }
            is Event.Fetched -> handleFetchItems(event.items)
            else -> {
                //do nothing
            }
        }
    }

    private fun handleFetchItems(items: List<WordEntity>) {
        adapter.collection = items
//        simpleUseCase(items)
//            .onExecute { list ->
//                list.map { AnkiSwipeItem(it) }
//            }.onComplete {
//                bindItems.clear()
//                bindItems.addAll(it)
//                adapter.clear()
//                adapter.addAll(it)
//            }.run()
    }

}