package com.example.a962n.presentation.ankiswipe

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a962n.anki.component.presentation.ext.inflater
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.presentation.ankiswipe.databinding.ItemAnkiSwipeBinding
import kotlin.properties.Delegates

class AnkiSwipeAdapter : RecyclerView.Adapter<AnkiSwipeAdapter.ViewHolder>() {

    var collection: List<WordEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnkiSwipeBinding.inflate(parent.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun getItemCount(): Int = collection.size

    data class ViewHolder(val binding: ItemAnkiSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: WordEntity) {
            binding.text.text = entity.name
        }
    }
}