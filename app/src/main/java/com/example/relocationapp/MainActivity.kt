package com.example.relocationapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.relocationapp.data.AuthviewModel
import com.example.relocationapp.data.FormViewModel
import com.example.relocationapp.navigation.AppNavHost
import com.example.relocationapp.navigation.ROUTE_HOME
import com.example.relocationapp.ui.theme.RelocationappTheme
import com.example.relocationapp.ui.theme.screens.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authviewModel = AuthviewModel(navController, this)
            val formViewModel = FormViewModel(navController, this)
            RelocationappTheme {
                AppNavHost(
                    navController = navController,
                    authviewModel = authviewModel,
                    formViewModel = formViewModel,
                    googleClientId = getString(R.string.web_client_id)
                )
                if (authviewModel.loggedin()) {
                    navController.navigate(ROUTE_HOME)
                }
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RelocationappTheme {
//        val context = LocalContext.current
//        val navController = rememberNavController()
//        val authviewModel = AuthviewModel(navController,MainActivity())
//        val formViewModel = FormViewModel(navController,MainActivity())
//        AppNavHost(navController = navController, authviewModel = authviewModel, formViewModel = formViewModel)
//    }
//
//}