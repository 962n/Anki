package com.example.a962n.anki.component.presentation.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * LayoutInflaterを取得する。
 *
 * HACK:RecyclerView.Adapterクラスに対して、inflater生成のために毎回contextクラスを渡さなくて良いようにするため
 *
 * @return LayoutInflater LayoutInflaterクラス
 */
fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)

/**
 * ソフトキーボードを非表示にする
 */
fun View.hideIME() {
    val imm = this.context.getSystemService(InputMethodManager::class.java)
    imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}