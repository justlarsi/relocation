package com.example.relocationapp.data



import android.app.ProgressDialog
import android.widget.Toast
import androidx.navigation.NavController
import com.example.relocationapp.MainActivity
import com.example.relocationapp.modules.User
import com.example.relocationapp.navigation.ROUTE_HOME
import com.example.relocationapp.navigation.ROUTE_LOGIN
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.GoogleAuthProvider

class AuthviewModel(private var navController: NavController, private var context: MainActivity){
    private var mAuth:FirebaseAuth
    private val progress:ProgressDialog
    init {
        mAuth= FirebaseAuth.getInstance()
        progress=ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    fun signup(name:String,email:String,password:String,confirmpass:String) {
//        progress.show()
        if (name.isEmpty() || email.isEmpty() || password.isBlank() || confirmpass.isBlank()) {
            Toast.makeText(context, "Please email and password cannot be blank", Toast.LENGTH_LONG)
                .show()
            return
        } else if (password != confirmpass) {
            Toast.makeText(context, "Password do not match", Toast.LENGTH_LONG).show()
            return
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userdata= User(name,email,password,mAuth.currentUser!!.uid)
                    val regRef=FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(context, "Registered sucessfully", Toast.LENGTH_LONG).show()

                        }
                    }
                    Toast.makeText(context, "Registered sucessfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_LOGIN)
                } else {
                    Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_LOGIN)
                }
            }

        }

    }
    fun login(email: String, pass: String){
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_LONG).show()
            return
        }
//        progress.show()
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if(it.isSuccessful)  {
                Toast.makeText(context,"logged in as ${mAuth.currentUser!!.displayName}",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
            }
        }

    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN)
        Toast.makeText(context,"logged out",Toast.LENGTH_LONG).show()
    }
    fun loggedin():Boolean{
        return mAuth.currentUser!=null
    }
    fun signInWithGoogle(credential: AuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser
                if (user != null) {
                    Toast.makeText(context,"logged in as ${mAuth.currentUser!!.displayName}",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_HOME)
                } else {
                    Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
