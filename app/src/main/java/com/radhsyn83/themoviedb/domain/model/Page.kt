package com.radhsyn83.themoviedb.domain.model

data class Page<T>(
    val totalPage: Int,
    val page: Int,
    val result: T
)