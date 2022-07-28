package com.udemycourse.triviaapp.repository

import com.udemycourse.triviaapp.data.DataOrException
import com.udemycourse.triviaapp.model.QuestionItem
import com.udemycourse.triviaapp.network.TriviaApiService
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val apiService: TriviaApiService) {

    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = apiService.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty())
                dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }

}