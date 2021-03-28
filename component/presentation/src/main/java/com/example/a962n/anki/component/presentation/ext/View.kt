package com.example.a962n.anki.component.presentation.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService


fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.INVISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.setVisibleOrGone(isVisible: Boolean) {
    when(isVisible){
        true -> visible()
        false -> gone()
    }
}

/**
 * ソフトキーボードを非表示にする
 */
fun View.hideIME() {
    val imm = this.context.getSystemService(InputMethodManager::class.java)
    imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}