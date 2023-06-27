package com.radhsyn83.themoviedb.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getDetail(id.toInt())
        }
    }

    private fun getDetail(id: Int) {
        getDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailState(detail = result.data)
                }
                is Resource.Error -> {
                    _state.value = DetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}