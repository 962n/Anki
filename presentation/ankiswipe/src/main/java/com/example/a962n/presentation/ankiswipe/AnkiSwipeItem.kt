package com.example.a962n.presentation.ankiswipe

import android.view.View
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.ankiswipe.databinding.ItemAnkiSwipeBinding
import com.xwray.groupie.viewbinding.BindableItem

data class AnkiSwipeItem(val entity:WordEntity) : BindableItem<ItemAnkiSwipeBinding>(entity.id.toLong()){

    override fun bind(viewBinding: ItemAnkiSwipeBinding, position: Int) {
        viewBinding.text.text = entity.name
    }

    override fun getLayout(): Int = R.layout.item_anki_swipe

    override fun initializeViewBinding(view: View): ItemAnkiSwipeBinding {
        return ItemAnkiSwipeBinding.bind(view)
    }
}