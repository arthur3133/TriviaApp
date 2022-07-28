package com.udemycourse.triviaapp.network

import com.udemycourse.triviaapp.constants.Constants.END_POINT
import com.udemycourse.triviaapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TriviaApiService {

    @GET(END_POINT)
    suspend fun getAllQuestions(): Question
}