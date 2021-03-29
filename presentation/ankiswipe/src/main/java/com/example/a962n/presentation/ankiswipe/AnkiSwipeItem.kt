package com.example.a962n.presentation.ankiswipe

import android.view.View
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.ankiswipe.databinding.ItemAnkiSwipeBinding
import com.xwray.groupie.viewbinding.BindableItem

data class AnkiSwipeItem(val entity: WordEntity, var isTurnedOver: Boolean = false) :
    BindableItem<ItemAnkiSwipeBinding>(entity.id.toLong()) {

    private var viewBinding: ItemAnkiSwipeBinding? = null

    fun turnOver() {
        isTurnedOver = !isTurnedOver
        viewBinding?.apply {
            bind(this)
        }
    }

    override fun bind(viewBinding: ItemAnkiSwipeBinding, position: Int) {
        this.viewBinding = viewBinding
        bind(viewBinding)
    }

    private fun bind(viewBinding: ItemAnkiSwipeBinding) {
        when (isTurnedOver) {
            true -> viewBinding.text.text = (entity.meaning + entity.extra)
            false -> viewBinding.text.text = entity.name
        }
    }

    override fun getLayout(): Int = R.layout.item_anki_swipe

    override fun initializeViewBinding(view: View): ItemAnkiSwipeBinding {
        return ItemAnkiSwipeBinding.bind(view)
    }

}