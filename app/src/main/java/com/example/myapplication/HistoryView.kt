package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun ShowHistory(
    viewModel: CalculatorViewModel = viewModel(),
    navController: NavController
) {

    val historyOperations = viewModel.historyOperations
    val historyResults = viewModel.historyResults

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .safeDrawingPadding()
    ) {
        Button(
            onClick = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(colorResource(R.color.controlButtom)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .padding(12.dp)
                .size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calculate_24),
                contentDescription = stringResource(id = R.string.backToCalculator),
                modifier = Modifier.size(26.dp),
                tint = colorResource(id = R.color.white)
            )
        }
        if (viewModel.hasHistory) {
            LazyColumn(

                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                items(historyOperations.size) { index ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(
                            modifier =
                                Modifier
                                    .fillMaxWidth(0.95f)
                                    .height(2.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .alpha(0.1f)
                                    .background(colorResource(id = R.color.white))
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = historyOperations[index],
                            fontSize = 18.sp,
                            color = colorResource(id= R.color.gray),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            text = historyResults[index],
                            fontSize = 30.sp,
                            color = colorResource(id= R.color.white),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_schedule_24),
                    contentDescription = stringResource(id = R.string.history),
                    tint = colorResource(id= R.color.gray),
                    modifier = Modifier.size(52.dp)
                )
                Text(
                    text = stringResource(R.string.msgEmptyHistory),
                    color = colorResource(id= R.color.gray),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestViewHistory() {
    val navController = rememberNavController()
    ShowHistory(navController = navController)
}
