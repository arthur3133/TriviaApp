package com.udemycourse.triviaapp.repository

import com.udemycourse.triviaapp.network.TriviaApiService
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val apiService: TriviaApiService) {

}