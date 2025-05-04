package co.edu.unab.oscar.ecommerceapp.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.unab.oscar.ecommerceapp.HomeScreen
import co.edu.unab.oscar.ecommerceapp.LoginScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun NavigationApp(){
    val myNavController = rememberNavController()
    var myStartDestination: String = "Login"

    val auth = Firebase.auth
    val currentUser = auth.currentUser

    if(currentUser != null){
        myStartDestination = "home"
    }else{
        myStartDestination = "login"
    }

    NavHost(
        navController = myNavController,
        startDestination = myStartDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable("Login") {
            LoginScreen(onClickRegister = {
                myNavController.navigate("register")
            }, onSuccessfullLogin = {
                myNavController.navigate("home"){
                    popUpTo("login"){inclusive = true}
                }
            })

        }
        composable("register") {
            RegisterScreen(onClickBack = {
                myNavController.popBackStack()
            }, onSuccessfulRegister ={
                myNavController.navigate("home"){
                    popUpTo(0)
                }
            })
        }
        composable("home"){
            HomeScreen(onClickLogout = {
                myNavController.navigate("login"){
                    popUpTo(0)
                }
            })
        }
    }
}