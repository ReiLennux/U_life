package mx.com.u_life.presentation.screens.owner_screens.addproperty

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.owner_screens.addproperty.content.AddPropertyContent
import mx.com.u_life.presentation.screens.owner_screens.addproperty.content.AddPropertyView

@Composable
fun AddPropertyScreen(
    navController: NavController
){
    Scaffold (
        content = { innerPadding ->
            AddPropertyContent(
                paddingValues = innerPadding,
                navController = navController
            )
        }
    )
    AddPropertyView(
        modifier = Modifier.fillMaxSize(),
    )
}