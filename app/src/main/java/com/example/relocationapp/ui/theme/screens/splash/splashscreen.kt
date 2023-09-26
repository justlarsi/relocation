package com.example.relocationapp.ui.theme.screens.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.relocationapp.R
import com.example.relocationapp.navigation.ROUTE_LOGIN
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun SplashScreen(navController: NavHostController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animate))
    val isPlaying by remember { mutableStateOf(true) }
    val startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        ), label = ""
    )

    LaunchedEffect(key1 = composition) {
        if (composition != null) {
            delay(3000)
            navController.popBackStack()
            navController.navigate(ROUTE_LOGIN)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(400.dp),
//            progress = alphaAnimation.value,
            isPlaying = isPlaying
        )
    }
}

//@SuppressLint("SuspiciousIndentation")
//@Composable
//fun SplashScreen(navController: NavHostController) {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animate))
//    var isPlaying by remember { mutableStateOf(true) }
//    val progress by animateLottieCompositionAsState(composition = composition)
//    var startAnimation by remember { mutableStateOf(false) }
//    val alphaAnimation = animateFloatAsState(
//        targetValue = if (startAnimation) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 2000
//        ), label = ""
//    )
//
//    LaunchedEffect(key1 = progress) {
//        if (progress == 0f) {
//            isPlaying = false
//        }
//        if (progress == 1f) {
//            isPlaying = true
//        }
//        startAnimation = true
//        delay(3000)
//        navController.popBackStack()
//        navController.navigate(ROUTE_LOGIN)
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        LottieAnimation(
//            composition = composition,
//            modifier = Modifier.size(400.dp),
//            progress = alphaAnimation.value
//            isPlaying = isPlaying
//        )
//    }
//}