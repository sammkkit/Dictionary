package com.example.dictionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DictionaryViewModel:ViewModel() {
    var wordResult by mutableStateOf<List<WordResult>?>(null)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    fun getMeaning(word:String){
        viewModelScope.launch {
            isLoading=true
            val response = try {
                RetrofitInstance.dictionaryAPI.getMeaning(word)
            }catch (e: Exception){
                errorMessage = "Network error. Please try again."
                isLoading = false
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                wordResult = response.body()
                errorMessage = null
            } else {
                errorMessage = "Word not found."
            }
            isLoading = false
        }
    }
}