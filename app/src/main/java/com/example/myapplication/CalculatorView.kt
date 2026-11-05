package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource

@Composable
fun ShowCalculator(
    viewModel: CalculatorViewModel = viewModel(),
    navController: NavController
) {
    val error = viewModel.error.value
    val result = if (error != null) errorMessage(error) else viewModel.result.value
    val previousOperation = viewModel.previousOperation

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
    ) {

        Column(
        ) {
            Button(
                onClick = { navController.navigate("history") { launchSingleTop = true } },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.controlButtom)),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .size(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_schedule_24),
                    contentDescription = stringResource(id = R.string.history),
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, start = 5.dp),
                text = previousOperation.value,
                fontSize = 40.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                softWrap = false,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, start = 5.dp),
                text = result,
                fontSize = if (error != null) 36.sp else 75.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                softWrap = false,
                maxLines = 1
            )
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(
                        stringResource(id = R.string.deleteSymbol),
                        colorResource(R.color.controlButtom),
                        viewModel
                    )
                    ShowButton(
                        stringResource(id = R.string.clearSymbol),
                        colorResource(R.color.controlButtom),
                        viewModel
                    )
                    ShowButton(
                        stringResource(id = R.string.percentSymbol),
                        colorResource(R.color.controlButtom),
                        viewModel
                    )
                    ShowButton(
                        stringResource(id = R.string.divideSymbol),
                        colorResource(R.color.operatorButtom),
                        viewModel
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("7", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("8", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("9", colorResource(R.color.numberButtom), viewModel)
                    ShowButton(
                        stringResource(id = R.string.multiplySymbol),
                        colorResource(R.color.operatorButtom),
                        viewModel
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("4", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("5", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("6", colorResource(R.color.numberButtom), viewModel)
                    ShowButton(
                        stringResource(id = R.string.subtractionSymbol),
                        colorResource(R.color.operatorButtom),
                        viewModel
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("1", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("2", colorResource(R.color.numberButtom), viewModel)
                    ShowButton("3", colorResource(R.color.numberButtom), viewModel)
                    ShowButton(
                        stringResource(id = R.string.addSymbol),
                        colorResource(R.color.operatorButtom),
                        viewModel
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(
                        stringResource(id = R.string.toggleSignSymbol),
                        colorResource(R.color.numberButtom),
                        viewModel
                    )
                    ShowButton("0", colorResource(R.color.numberButtom), viewModel)
                    ShowButton(
                        stringResource(id = R.string.decimalSymbol),
                        colorResource(R.color.numberButtom),
                        viewModel
                    )
                    ShowButton(
                        stringResource(id = R.string.equalsSymbol),
                        colorResource(R.color.operatorButtom),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun ShowButton(text: String, color: Color, viewModel: CalculatorViewModel) {
    Button(
        onClick = { viewModel.manageClickButtons(text) },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier
            .size(85.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 28.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TestView() {
    val navController = rememberNavController()
    ShowCalculator(navController = navController)
}

@Composable
fun errorMessage(error: CalculatorError): String = when (error) {
    CalculatorError.DivisionByZero -> stringResource(R.string.msgDivisionByZero)
    CalculatorError.InvalidOperation -> stringResource(R.string.msgError)
}
