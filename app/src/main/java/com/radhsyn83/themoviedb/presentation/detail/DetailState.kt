package com.radhsyn83.themoviedb.presentation.detail

import com.radhsyn83.themoviedb.domain.model.Detail


data class DetailState(
    val isLoading: Boolean = false,
    val detail: Detail? = null,
    val error: String = ""
)