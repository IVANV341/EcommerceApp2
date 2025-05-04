package co.edu.unab.oscar.ecommerceapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.unab.oscar.ecommerceapp.validateConfirmPassword
import co.edu.unab.oscar.ecommerceapp.validateEmail
import co.edu.unab.oscar.ecommerceapp.validateName
import co.edu.unab.oscar.ecommerceapp.validatePassword
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.platform.LocalView
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegisterScreen(onClickBack :() -> Unit = {}, onSuccessfulRegister:()->Unit = {}) {

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    //ESTADO DE LOS INPUT
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputPasswordConfirmation by remember { mutableStateOf("") }



    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordConfirmationError by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }



    Scaffold(
        topBar ={
            TopAppBar(
                title ={
                },
                navigationIcon={
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector=Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "icon register"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Register",
                modifier = Modifier.size(150.dp)
            )

            Text(text = "Registrar Usuario",
                fontSize = 24.sp,
                color = Color(0xFFFF9900),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = inputName,
                onValueChange = {inputName = it},
                label = {
                    Text("Nombre Completo")
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Name",
                        tint = Color(0xFFFF9900)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                supportingText = {
                    if (nameError.isNotEmpty()){
                        Text(
                            text = nameError,
                            color = Color.Red
                        )
                    }
                }
            )


            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = inputEmail,
                onValueChange = {inputEmail = it},
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(0xFFFF9900)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                supportingText = {
                    if (emailError.isNotEmpty()){
                        Text(
                            text = emailError,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputPassword,
                onValueChange = {inputPassword = it},
                label = {
                    Text("Contraseña")
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Contraseña",
                        tint = Color(0xFFFF9900)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                supportingText = {
                    if (passwordError.isNotEmpty()){
                        Text(
                            text = passwordError,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputPasswordConfirmation,
                onValueChange = {inputPasswordConfirmation = it},
                label = {
                    Text("Confirmar Contraseña")
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirmar Contraseña",
                        tint = Color(0xFFFF9900)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                supportingText = {
                    if (passwordError.isNotEmpty()){
                        Text(
                            text = passwordError,
                            color = Color.Red
                        )
                    }
                }
            )

            if(registerError.isNotEmpty()){
                Text(registerError, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    val isValidName = validateName(inputName).first
                    val isValidEmail = validateEmail(inputEmail).first
                    val isValidPassword = validatePassword(inputPassword).first
                    val isaValidConfirmPassword = validateConfirmPassword(inputPassword, inputPasswordConfirmation).first

                    nameError = validateName(inputName).second
                    emailError = validateEmail(inputEmail).second
                    passwordError = validatePassword(inputPassword).second
                    passwordConfirmationError = validateConfirmPassword(inputPassword, inputPasswordConfirmation).second

                    if (isValidName && isValidEmail && isValidPassword && isaValidConfirmPassword){
                        auth.createUserWithEmailAndPassword(inputEmail, inputPassword).
                                addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful){
                                        onSuccessfulRegister()
                                    }else {
                                        registerError = when (task.exception) {
                                            is FirebaseAuthInvalidCredentialsException -> "Correo invalido"
                                            is FirebaseAuthUserCollisionException -> "Correo ya registrado"
                                            else -> "Error al registrarse"
                                        }
                                    }
                                }

                    }else{
                        registerError = "Hubo un error en el register"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9900)
                ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Registrarse",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    EcommerceAppTheme {
        //RegisterScreen()
    }
}