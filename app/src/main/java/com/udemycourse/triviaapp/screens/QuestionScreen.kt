package com.udemycourse.triviaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udemycourse.triviaapp.ui.theme.mDarkPurple
import com.udemycourse.triviaapp.ui.theme.mLightGray
import com.udemycourse.triviaapp.ui.theme.mOffWhite
import com.udemycourse.triviaapp.viewmodel.QuestionViewModel

@Composable
fun QuestionScreen(questionViewModel: QuestionViewModel) {
    if (questionViewModel.data.value.loading == true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mDarkPurple),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = mOffWhite)
        }
    } else {
        QuestionDisplay()
    }
}

@Preview
@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }
    }
}

@Composable
fun QuestionTracker(counter: Int = 10, totalQuestion: Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(style = SpanStyle(
                color = mLightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp)) {
                append("Question $counter/")
            }
            withStyle(style = SpanStyle(
                color = mLightGray,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp)) {
                append("$totalQuestion")
            }
        }
    })
}