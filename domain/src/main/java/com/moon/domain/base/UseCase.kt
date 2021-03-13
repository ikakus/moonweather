package com.moon.domain.base

import kotlinx.coroutines.flow.Flow

interface UseCase<Arg, R> {
    operator fun invoke(arg: Arg): Flow<R>
}

interface NoArgUseCase<R> {
    operator fun invoke(): Flow<R>
}

interface NoArgSyncUseCase<R> {
    operator fun invoke(): R
}

interface SyncUseCase<Arg, R> {
    operator fun invoke(arg: Arg): R
}