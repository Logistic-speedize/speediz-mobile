package com.example.domain

abstract class BaseUseCase<IN, OUT> {

    abstract suspend operator fun invoke(params: IN): OUT
}
