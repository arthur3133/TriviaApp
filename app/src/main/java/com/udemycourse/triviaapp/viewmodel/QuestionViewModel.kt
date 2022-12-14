package com.udemycourse.triviaapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.triviaapp.data.DataOrException
import com.udemycourse.triviaapp.model.QuestionItem
import com.udemycourse.triviaapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository): ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }
    private fun getAllQuestions() {
        data.value.loading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = repository.getAllQuestions()
                if (data.value.data.toString().isNotEmpty())
                    data.value.loading = false
            } catch (e: Exception) {
                data.value.e = e
            }
        }
    }
}