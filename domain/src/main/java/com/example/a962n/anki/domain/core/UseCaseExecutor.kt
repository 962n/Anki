package com.example.a962n.anki.domain.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface UseCasePrepare<I> {
    fun <F, S> onExecute(onExecute: (I) -> Either<F, S>): UseCaseExecutor<I, F, S>
}

private class UseCasePrepareImpl<Input> constructor(
    private val input : Input,
): UseCasePrepare<Input> {
    override fun <F, S> onExecute(onExecute: (Input) -> Either<F, S>): UseCaseExecutor<Input, F, S> {
        return UseCaseExecutorImpl(input,onExecute)
    }
}

interface UseCaseExecutor<Input, Failure, Success> {
    fun onFailure(onFailure: (Failure) -> Unit) : UseCaseExecutor<Input, Failure, Success>
    fun onSuccess(onSuccess: (Success) -> Unit) : UseCaseExecutor<Input, Failure, Success>
    fun onFinally(onFinally: () -> Unit) : UseCaseExecutor<Input, Failure, Success>    fun run()
}

private class UseCaseExecutorImpl<Input, Failure, Success>
constructor(
    private val input : Input,
    private var onExecute: ((Input) -> Either<Failure, Success>)
) : UseCaseExecutor<Input, Failure, Success> {

    private var onFailure: ((Failure) -> Unit)? = null
    private var onSuccess: ((Success) -> Unit)? = null
    private var onFinally: (() -> Unit)? = null

    override fun onFailure(onFailure: (Failure) -> Unit) : UseCaseExecutor<Input, Failure, Success>{
        this.onFailure = onFailure
        return this
    }

    override fun onSuccess(onSuccess: (Success) -> Unit) : UseCaseExecutor<Input, Failure, Success>{
        this.onSuccess = onSuccess
        return this
    }
    override fun onFinally(onFinally: () -> Unit): UseCaseExecutor<Input, Failure, Success> {
        this.onFinally = onFinally
        return this
    }

    override fun run() {
        val onExecute = this.onExecute
        val job = GlobalScope.async(Dispatchers.IO) {
            onExecute(input)
        }
        GlobalScope.launch(Dispatchers.Main) {
            val either = job.await()
            onFinally?.invoke()
            either.onSuccess {
                this@UseCaseExecutorImpl.onSuccess?.invoke(it)
            }.onFailure {
                this@UseCaseExecutorImpl.onFailure?.invoke(it)
            }
        }
    }
}

fun <P> useCase(input: P): UseCasePrepare<P> {
    return UseCasePrepareImpl(input)
}
