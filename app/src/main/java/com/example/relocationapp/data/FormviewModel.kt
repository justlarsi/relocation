package com.example.relocationapp.data

import android.widget.Toast
import androidx.navigation.NavController
import com.example.relocationapp.MainActivity
import com.example.relocationapp.modules.Form
import com.example.relocationapp.navigation.ROUTE_DETAILS
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class FormViewModel(var navController: NavController, var context: MainActivity) {
    var pickupLocation: String = ""
    var dropOffLocation: String = ""
    var selectedDate: Date? = null
    var selectedVehicleType: String = ""
    fun formSubmit(pickupLocation: String, dropOffLocation: String, selectedDate: Date?, selectedVehicleType: String) {
        if (pickupLocation.isNotEmpty() && dropOffLocation.isNotEmpty() && selectedDate != null && selectedVehicleType.isNotEmpty()) {
            val formsRef = FirebaseDatabase.getInstance().getReference("Forms")
            val formKey = formsRef.push().key
            val form = Form(pickupLocation, dropOffLocation, selectedDate, selectedVehicleType)
            formsRef.child(formKey!!).setValue(form).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Form submitted successfully", Toast.LENGTH_SHORT).show()

                    navController.navigate(
                        "$ROUTE_DETAILS?pickupLocation=$pickupLocation&dropOffLocation=$dropOffLocation&selectedDate=${selectedDate?.time ?: 0}&selectedVehicleType=$selectedVehicleType"
                    )
                } else {
                    Toast.makeText(context, "Error submitting form", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
