package com.moon.domain.base

import io.reactivex.Single

interface UseCase<Arg, R> {
    operator fun invoke(arg: Arg): Single<R>
}

interface NoArgUseCase<R> {
    operator fun invoke(): Single<R>
}

interface NoArgSyncUseCase<R> {
    operator fun invoke(): R
}

interface SyncUseCase<Arg, R> {
    operator fun invoke(arg: Arg): R
}