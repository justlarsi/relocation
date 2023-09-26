@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.relocationapp.ui.theme.screens.home

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.relocationapp.R
import com.example.relocationapp.data.FormViewModel
import java.util.*

@Composable
fun TopBar() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 8.dp), // Add horizontal padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(48.dp) // Adjust the size of the logo
            )
        }
    }
}
@Composable
fun CustomDatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit
) {
    val calendar = Calendar.getInstance()
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        onDateSelected(calendar.time)
    }
    val context = LocalContext.current
    TextButton(
        onClick = {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context, dateSetListener, year, month, dayOfMonth).show()
        },
        modifier = Modifier
            .padding(bottom = 16.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(10))
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Text(text = selectedDate?.toString() ?: "Select Date")
    }
}

@Composable
fun MapPickerDialog(
    onPlaceSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Google Maps View", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = {
                // Simulate selecting a location
                onPlaceSelected("Selected Location")
                onDismiss()
            }
        ) {
            Text(text = "Select")
        }

        TextButton(
            onClick = {
                onDismiss()
            }
        ) {
            Text(text = "Cancel")
        }
    }
}

@Composable
fun OutlinedDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var hasSelection by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        BasicTextField(
            value = if (hasSelection) options[selectedIndex] else "Type of Vehicle",
            onValueChange = { /* Nothing to do here */ },
            textStyle = TextStyle(color = Color.Black), // Ensure text color is visible
            readOnly = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(10))
                        .padding(8.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                ) {
                    innerTextField()
                }
            }
        )

        if (expanded) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedIndex = index
                            onValueChange(option)
                            expanded = false
                            hasSelection = true // Update the state
                        }
                        .padding(8.dp)
                ) {
                    Text(text = option)
                }
            }
        }
    }
}
@Composable
fun MoveDetailsScreen(navController: NavHostController,formViewModel: FormViewModel) {
    var pickupLocation by remember { mutableStateOf("") }
    var dropOffLocation by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val vehicleTypes = listOf("Pickup truck", "Lorry", "Motorbike", "Car", "Trailer")
    var selectedVehicleType by remember { mutableStateOf(vehicleTypes[0]) }
    val options by remember { mutableStateOf(vehicleTypes) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    var isPickingPickupLocation by remember { mutableStateOf(false) }
    var isPickingDropOffLocation by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val onPlaceSelected: (String) -> Unit = { placeName ->
        if (isPickingPickupLocation) {
            pickupLocation = placeName
            isPickingPickupLocation = false
        } else if (isPickingDropOffLocation) {
            dropOffLocation = placeName
            isPickingDropOffLocation = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back), // Replace with your image resource
            contentDescription = null, // Provide a content description if needed
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp) // Adjust the size of the logo
            )
            Text(
                text = "Tell us more about your move",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = pickupLocation,
                onValueChange = { pickupLocation = it },
                label = { Text("Pickup Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = dropOffLocation,
                onValueChange = { dropOffLocation = it },
                label = { Text("Drop-off Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            CustomDatePicker(
                selectedDate = selectedDate,
                onDateSelected = { date -> selectedDate = date }
            )

            OutlinedDropdown(
                value = selectedVehicleType,
                onValueChange = { selectedVehicleType = it },
                options = vehicleTypes,
                label = { Text("Vehicle Type") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            val darkGreen = Color(0xFF006400)
            Button(
                onClick = {
                    formViewModel.formSubmit(
                        pickupLocation,
                        dropOffLocation,
                        selectedDate,
                        selectedVehicleType
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors( darkGreen, contentColor = Color.White)
            ){
                Text(text = "Continue")
            }

            if (isPickingPickupLocation || isPickingDropOffLocation) {
                MapPickerDialog(
                    onPlaceSelected = onPlaceSelected,
                    onDismiss = {
                        isPickingPickupLocation = false
                        isPickingDropOffLocation = false
                    }
                )
            }
        }
    }
}




