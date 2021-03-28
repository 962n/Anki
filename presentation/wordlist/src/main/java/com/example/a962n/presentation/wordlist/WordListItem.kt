package com.example.a962n.presentation.wordlist

import android.view.View
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.wordlist.databinding.ItemWordListBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

data class WordListItem(val wordEntity: WordEntity) :
    BindableItem<ItemWordListBinding>(wordEntity.id.toLong()) , ExpandableItem {

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {

    }

    override fun bind(viewBinding: ItemWordListBinding, position: Int) {
        viewBinding.textName.text = wordEntity.name
//        viewBinding.textMeaning.text = wordEntity.meaning
//        viewBinding.textExtra.text = wordEntity.extra
    }

    override fun getLayout(): Int = R.layout.item_word_list

    override fun initializeViewBinding(view: View): ItemWordListBinding {
        return ItemWordListBinding.bind(view)
    }
}