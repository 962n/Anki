package com.example.a962n.anki.component.presentation.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * LiveDataへオブザーバー(データ変更通知)の登録処理
 * @param liveData データ
 * @param body オブザーバー
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer { body(it) })

/**
 * LiveDataへオブザーバー(データ変更通知)の登録処理
 * @param liveData データ
 * @param body オブザーバー
 */
fun <T : Any, L : LiveData<T?>> LifecycleOwner.observeNullable(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer { body(it) })
