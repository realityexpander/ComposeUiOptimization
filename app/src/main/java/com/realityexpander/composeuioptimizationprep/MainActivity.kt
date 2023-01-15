package com.realityexpander.composeuioptimizationprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.realityexpander.composeuioptimizationprep.optimization1.MainViewModel
import com.realityexpander.composeuioptimizationprep.optimization1.RgbSelector
import com.realityexpander.composeuioptimizationprep.optimization2.Optimization3
import com.realityexpander.composeuioptimizationprep.optimization3.CustomGrid
import com.realityexpander.composeuioptimizationprep.optimization3.FeedViewModel
import com.realityexpander.composeuioptimizationprep.ui.theme.ComposeUiOptimizationPrepTheme
import com.plcoding.external_module.ExternalUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUiOptimizationPrepTheme {

                // One at a time:

                // Problem 1: not using a @Stable object
//                Optimization1()

                // ???
//                Optimization2()

                // Problem 3: Using a class from an external module
                Optimization3(
                    ExternalUser(
                        id = "1",
                    email = "a@demo.com",
                    username = "John2"
                    )
                )

            }
        }
    }
}

@Composable
fun Optimization1() {
    val viewModel = viewModel<MainViewModel>()

    // option 1 - use a remember block for the lambda
    val changeColorLambda = remember<(Color) -> Unit> {
        {
            viewModel.changeColor(it)
        }
    }

    // Option 2 - use a remember block for the value
    var color by remember {
        mutableStateOf(Color.Red)
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RgbSelector(
            color = viewModel.color,
            onColorClick =
                // Option 0
                viewModel::changeColor // use a method reference
//            {
//                // problem (causes recomposition)
//                // viewModel.changeColor(it)
//
//                // Option 1
//                //changeColorLambda(it)  // use a remembered lambda stored in a variable
//
//                // option 2
//                //color = it  // use a remembered value
//            }
        )
    }
}

@Composable
fun Optimization2() {
    val viewModel = viewModel<FeedViewModel>()
    val feeds = viewModel.feeds

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomGrid(
            feeds = feeds,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = viewModel::rearrangeFeeds) {
            Text(text = "Shuffle feeds")
        }
    }
}