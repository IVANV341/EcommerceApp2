package co.edu.unab.oscar.ecommerceapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import co.edu.unab.oscar.ecommerceapp.ui.theme.EcommerceAppTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(onClickLogout: () -> Unit = {}) {
    val auth = Firebase.auth
    val user = auth.currentUser

    Scaffold(
        topBar = {
            val scrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Bienvenido",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("HOME SCREEN", fontSize = 30.sp)
                if (user != null) {
                    Text(user.email ?: "No hay usuario")
                } else {
                    Text("No hay usuario")
                }
            }

            Button(
                onClick = {
                    auth.signOut()
                    onClickLogout()
                },
                modifier = Modifier
                    .align(Start)
                    .padding(start = 16.dp, top = 8.dp)
                    .width(150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffff9900)
                )
            ) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Promociones", modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    bottom = 8.dp
                )
            )
            val listUrls = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTt6p3jBsZXMywd-V_DheoS5OnEhaM-IclZNQ&s.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgwpgkOXl81FLdWaaFBi8oaeKOzhBwyC8N1w&s.jpg",
                "https://previews.123rf.com/images/stickerside/stickerside2211/stickerside221100260/195938432-etiqueta-de-venta-plantilla-de-promoci%C3%B3n-del-20-por-ciento-de-descuento-venta-de-marketing-de.jpg",
                "https://img.freepik.com/vector-premium/plantilla-diseno-fondo-banner-venta-color-azul-naranja_500223-389.jpg",
                "https://www.shutterstock.com/image-vector/summer-sale-promo-banner-clothes-260nw-1770029450.jpg"
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                for (url in listUrls) {
                    item { CardPromo(url) }



                }
            }
        }
    }
}

@Composable
fun CardPromo(url: String) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .width(300.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = "Promocion",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    EcommerceAppTheme {
        HomeScreen()
    }
}