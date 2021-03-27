package com.example.a962n.anki.domain.entity

data class AddWordEntity(
    val name: String,
    val meaning: String,
    val extra: String
) {
    companion object
}