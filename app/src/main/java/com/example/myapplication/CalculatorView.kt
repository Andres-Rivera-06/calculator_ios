package com.example.myapplication
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ShowCalculator(
    viewModel: CalculatorViewModel = viewModel(),
    navController: NavController
) {
    val result = viewModel.result.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(
                onClick = { navController.navigate("history") },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier
                    .size(85.dp)
            ) {
                Text(
                    text = "H",
                    fontSize = 28.sp
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, start = 5.dp),
                text = "0",
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
                fontSize = 75.sp,
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
                    ShowButton("≪", colorResource(R.color.controlButtom), viewModel)
                    ShowButton("AC", colorResource(R.color.controlButtom), viewModel)
                    ShowButton("%", colorResource(R.color.controlButtom), viewModel)
                    ShowButton(stringResource(id= R.string.divideSymbol), colorResource(R.color.operatorButtom), viewModel)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("7", Color(0xFF2A2928), viewModel)
                    ShowButton("8", Color(0xFF2A2928), viewModel)
                    ShowButton("9", Color(0xFF2A2928), viewModel)
                    ShowButton(stringResource(id= R.string.multiplySymbol), Color(0xFFFFA70E), viewModel)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("4", Color(0xFF2A2928), viewModel)
                    ShowButton("5", Color(0xFF2A2928), viewModel)
                    ShowButton("6", Color(0xFF2A2928), viewModel)
                    ShowButton(stringResource(id= R.string.subtractionSymbol), Color(0xFFFFA70E), viewModel)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("1", Color(0xFF2A2928), viewModel)
                    ShowButton("2", Color(0xFF2A2928), viewModel)
                    ShowButton("3", Color(0xFF2A2928), viewModel)
                    ShowButton(stringResource(id= R.string.addSymbol), Color(0xFFFFA70E), viewModel)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton("⁺/₋", Color(0xFF2A2928), viewModel)
                    ShowButton("0", Color(0xFF2A2928), viewModel)
                    ShowButton(",", Color(0xFF2A2928), viewModel)
                    ShowButton("=", Color(0xFFFFA70E), viewModel)
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
            fontSize = 28.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TestView(){
    val navController = rememberNavController()
    ShowCalculator(navController = navController)
}
