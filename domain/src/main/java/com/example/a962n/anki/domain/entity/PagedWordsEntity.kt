package com.example.a962n.anki.domain.entity

data class PagedWordsEntity(
    val nextPageKey: String?,
    val words: List<WordEntity>
)