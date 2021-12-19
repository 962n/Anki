package com.example.a962n.anki.domain.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface SimpleExecutorPrepare<I> {
    fun <R> onExecute(onExecute: (I) -> R): SimpleExecutor<I, R>
}

private class SimpleExecutorPrepareImpl<Input> constructor(
    private val input: Input,
) : SimpleExecutorPrepare<Input> {

    override fun <R> onExecute(onExecute: (Input) -> R): SimpleExecutor<Input, R> {
        return SimpleExecutorImpl(input, onExecute)
    }
}

interface SimpleExecutor<Input, Result> {
    fun onComplete(onComplete: (Result) -> Unit): SimpleExecutor<Input, Result>
    fun run()
}

private class SimpleExecutorImpl<Input, Result>
constructor(
    private val input: Input,
    private var onExecute: ((Input) -> Result)
) : SimpleExecutor<Input, Result> {

    private var onComplete: ((Result) -> Unit)? = null

    override fun onComplete(onComplete: (Result) -> Unit): SimpleExecutor<Input, Result> {
        this.onComplete = onComplete
        return this
    }


    override fun run() {
        val onExecute = this.onExecute
        val job = GlobalScope.async(Dispatchers.IO) {
            onExecute(input)
        }
        GlobalScope.launch(Dispatchers.Main) {
            val result = job.await()
            onComplete?.invoke(result)
        }
    }
}

fun <P> simpleUseCase(input: P): SimpleExecutorPrepare<P> {
    return SimpleExecutorPrepareImpl(input)
}