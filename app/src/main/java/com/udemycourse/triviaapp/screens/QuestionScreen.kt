package com.udemycourse.triviaapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udemycourse.triviaapp.model.QuestionItem
import com.udemycourse.triviaapp.ui.theme.*
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
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        val questions = questionViewModel.data.value.data
        val questionIndex = remember {
            mutableStateOf(0)
        }
        if (questions != null) {
            QuestionDisplay(
                questionViewModel = questionViewModel,
                questionItem = questions[questionIndex.value],
                questionIndex = questionIndex,
                onNextClicked = {
                    questionIndex.value += 1
                }
            )
        }
    }
}

@Composable
fun QuestionDisplay(
    questionViewModel: QuestionViewModel,
    questionItem: QuestionItem,
    questionIndex: MutableState<Int>,
    onNextClicked: (Int) -> Unit
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val choicesState = remember(questionItem) {
        questionItem.choices.toMutableList()
    }

    val answerState = remember(questionItem) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(questionItem) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(questionItem) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == questionItem.answer
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (questionIndex.value >= 3)
                ShowProgress(score = questionIndex.value)
            QuestionTracker(counter = questionIndex.value, totalQuestion = questionViewModel.data.value.data?.size)
            Spacer(modifier = Modifier.height(12.dp))
            DrawDottedLine(pathEffect = pathEffect)
            Spacer(modifier = Modifier.height(6.dp))
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .align(alignment = Alignment.Start),
                    text = questionItem.question,
                    color = mOffWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
                // Choices
                choicesState.forEachIndexed { index, optionText ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(vertical = 6.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        mLightPurple,
                                        mLightPurple
                                    )
                                ),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomStartPercent = 50,
                                    bottomEndPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 14.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && answerState.value == index) {
                                    Color.Green
                                } else {
                                    Color.Red
                                }
                            )
                        )
                        Text(
                            text = optionText,
                            color = if (correctAnswerState.value == true && answerState.value == index) {
                                Color.Green
                            } else  if (correctAnswerState.value == false && answerState.value == index){
                                Color.Red
                            } else {
                                mOffWhite
                            },
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = {
                          onNextClicked(questionIndex.value)
                },
                modifier = Modifier
                    .padding(3.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(34.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = mLightBlue
                )
            ) {
                Text(
                    text = "Next",
                    modifier = Modifier.padding(6.dp),
                    color = mOffWhite,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun QuestionTracker(counter: Int, totalQuestion: Int?) {
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

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(
            color = mLightGray,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun ShowProgress(score: Int) {
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95076), Color(0xFFBE6BE5)))
    val progressState by remember(score) {
        mutableStateOf(score*0.005f)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(3.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(colors = listOf(mLightPurple, mLightPurple)),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { },
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(progressState)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {

        }
    }
}