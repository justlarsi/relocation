package com.example.relocationapp.data

import android.widget.Toast
import androidx.navigation.NavController
import com.example.relocationapp.MainActivity
import com.example.relocationapp.modules.Form
import com.example.relocationapp.navigation.ROUTE_DETAILS
import com.google.firebase.database.FirebaseDatabase
import java.util.Date
import com.example.relocationapp.ui.theme.screens.home.MoveDetailsScreen

class FormViewModel(var navController: NavController, var context: MainActivity) {
    val pickupLocation: String = ""
    val dropOffLocation: String = ""
    val selectedDate: Date? = null
    val selectedVehicleType: String = ""
    fun formSubmit(
        pickupLocation: String, dropOffLocation: String, selectedDate: Date?, selectedVehicleType: String
    ) {
        if (pickupLocation.isNotEmpty() && dropOffLocation.isNotEmpty() && selectedDate != null && selectedVehicleType.isNotEmpty()) {
            try {
                val formsRef = FirebaseDatabase.getInstance().getReference("Forms")
                val formKey = formsRef.push().key

                if (formKey == null) {
                    Toast.makeText(context, "Error: Could not generate form key", Toast.LENGTH_SHORT).show()
                    return
                }

                val form = Form(pickupLocation, dropOffLocation, selectedDate, selectedVehicleType)
                formsRef.child(formKey).setValue(form).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Form submitted successfully", Toast.LENGTH_SHORT).show()

                        navController.navigate(
                            "$ROUTE_DETAILS?pickupLocation=$pickupLocation&dropOffLocation=$dropOffLocation&selectedDate=${selectedDate.time}&selectedVehicleType=$selectedVehicleType"
                        )
                    } else {
                        val exception = task.exception
                        Toast.makeText(context, "Error submitting form: ${exception?.message}", Toast.LENGTH_LONG).show()
                        exception?.printStackTrace() // Log the detailed error
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                    exception.printStackTrace() // Log the detailed error
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Unexpected error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace() // Log any unexpected exception
            }
        } else {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
