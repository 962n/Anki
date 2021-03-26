package com.example.a962n.anki.domain.entity

data class WordEntity(
    val id : Int,
    val name: String,
    val meaning: String,
    val extra: String
) {
    companion object
}