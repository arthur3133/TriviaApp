package com.udemycourse.triviaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.udemycourse.triviaapp.screens.QuestionScreen
import com.udemycourse.triviaapp.ui.theme.TriviaAppTheme
import com.udemycourse.triviaapp.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaAppTheme {
                TriviaHome()
            }
        }
    }
}

@Composable
fun TriviaHome(questionViewModel: QuestionViewModel = hiltViewModel()) {
    QuestionScreen(questionViewModel)
}