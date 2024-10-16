package com.example.relocationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.relocationapp.data.AuthviewModel
import com.example.relocationapp.data.FormViewModel
import com.example.relocationapp.navigation.AppNavHost
import com.example.relocationapp.navigation.ROUTE_HOME
import com.example.relocationapp.ui.theme.RelocationappTheme
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MainActivity : ComponentActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE_PICKUP = 1
    private val AUTOCOMPLETE_REQUEST_CODE_DROPOFF = 2

    // State variables for locations
    var pickupLocation = mutableStateOf("")
    var dropOffLocation = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyB8mOAUXZJckm4CuPrX6vmRbsY3xRDf1ok") // Replace with your actual API key
        }

        setContent {
            val navController = rememberNavController()
            val authviewModel = AuthviewModel(navController, this)
            val formViewModel = FormViewModel(navController, this)

            RelocationappTheme {
                AppNavHost(
                    navController = navController,
                    authviewModel = authviewModel,
                    formViewModel = formViewModel,
                    googleClientId = getString(R.string.web_client_id),
                    mainActivity = this // Pass the current instance
                )
                if (authviewModel.loggedin()) {
                    navController.navigate(ROUTE_HOME)
                }
            }
        }
    }

    // Launch Autocomplete for Pickup
    fun launchPlaceAutocompletePickup() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_PICKUP)
    }

    // Launch Autocomplete for Drop-off
    fun launchPlaceAutocompleteDropoff() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_DROPOFF)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AUTOCOMPLETE_REQUEST_CODE_PICKUP -> {
                if (resultCode == RESULT_OK && data != null) {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.i("Pickup Place", "Place: ${place.name}, ${place.id}")
                    pickupLocation.value = place.name ?: ""
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.e("Pickup Error", "An error occurred: $status")
                }
            }
            AUTOCOMPLETE_REQUEST_CODE_DROPOFF -> {
                if (resultCode == RESULT_OK && data != null) {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.i("Dropoff Place", "Place: ${place.name}, ${place.id}")
                    dropOffLocation.value = place.name ?: ""
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.e("Dropoff Error", "An error occurred: $status")
                }
            }
        }
    }
}
