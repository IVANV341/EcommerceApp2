package co.edu.unab.oscar.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.unab.oscar.ecommerceapp.ui.theme.EcommerceAppTheme
import co.edu.unab.oscar.ecommerceapp.ui.theme.NavigationApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme{
                NavigationApp()

            }
        }
    }
}

