package mx.com.u_life.presentation.screens.owner_screens.addproperty

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.owner_screens.addproperty.content.AddPropertyContent
import mx.com.u_life.presentation.screens.owner_screens.addproperty.content.AddPropertyPostView
import mx.com.u_life.presentation.screens.owner_screens.addproperty.content.AddPropertyView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPropertyScreen(
    navController: NavController
){
    Scaffold (
        content = {
            AddPropertyContent(
                navController = navController
            )
        }
    )
    AddPropertyView(
        modifier = Modifier.fillMaxSize(),
    )
    AddPropertyPostView(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}