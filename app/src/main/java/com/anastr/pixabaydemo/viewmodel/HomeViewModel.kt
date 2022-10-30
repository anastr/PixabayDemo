package com.anastr.pixabaydemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anastr.pixabaydemo.data.model.ImageInfo
import com.anastr.pixabaydemo.data.repository.ImageListRepository
import com.anastr.pixabaydemo.state.CallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val imageListRepository: ImageListRepository,
): ViewModel() {

    private val _imagesState = MutableStateFlow<CallState<List<ImageInfo>>>(CallState.Loading)
    val imagesState = _imagesState.asStateFlow()

    fun loadImages() {
        viewModelScope.launch {
            _imagesState.update { CallState.Loading }
            val remoteState = imageListRepository.getImages()
            _imagesState.update { remoteState }
        }
    }

    init {
        loadImages()
    }
}
