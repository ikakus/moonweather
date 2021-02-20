package com.moon.domain.base

import io.reactivex.Single

interface UseCase<Arg> {
    fun execute(arg: Arg): Single<DomainModel>
}

interface NoArgUseCase<R> {
    fun execute(): Single<R>
}

interface NoArgSyncUseCase<R> {
    fun execute(): R
}

interface SyncUseCase<Arg> {
    fun execute(arg: Arg)
}