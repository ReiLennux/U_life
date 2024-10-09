package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.domain.models.generic.convertToGenericCatalog
import mx.com.u_life.presentation.components.DefaultDropDownMenu

@Composable
fun AddPropertyContent(
    paddingValues: PaddingValues,
    viewModel: AddPropertyViewModel = hiltViewModel(),
    navController: NavController? = null

) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FormHeader()
            PropertyFormBody(viewModel = viewModel)
        }
    }
}

@Composable
fun FormHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FormTitle(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        PropertyImage()
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Text(
            text = "Cuentanos mas acerca de que es lo que rentas"
        )
    }
}

@Composable
fun FormTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier) { // Apply modifier here
        Text(
            text = "Registra una Propiedad",
            style = MaterialTheme.typography.displayLarge,
            textDecoration = MaterialTheme.typography.titleLarge.textDecoration
        )
    }
}

@Composable
fun PropertyImage(image: Int = R.drawable.png_property_add) {
    Column {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .size(160.dp)
        )
    }
}

@Composable
fun PropertyFormBody(viewModel: AddPropertyViewModel) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            PropertyInfo(viewModel = viewModel)
            ChipFacilities()
            SaveButton()
        }
}

@Composable
fun PropertyInfo(viewModel: AddPropertyViewModel){
    var propertyName by remember { mutableStateOf("") }
    var propertyDescription by remember { mutableStateOf("") }
    var propertyPrice by remember { mutableStateOf("") }
    var propertyLocation by remember { mutableStateOf("") }
    var propertyTypeSelected by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    val types by viewModel.types.observeAsState(initial = emptyList())

    OutlinedTextField(
        value = propertyName,
        onValueChange = { propertyName = it },
        label = { Text("Nombre de la propiedad") },
        modifier = Modifier.fillMaxWidth()
    )

    DefaultDropDownMenu(
        modifier = Modifier.fillMaxWidth(),
        label = "Tipo de propiedad",
        selectedText = propertyTypeSelected,
        isExpanded = isExpanded,
        items = types.convertToGenericCatalog(),
        onValueChangeAction = { },
        onSelectedItem = {
            propertyTypeSelected = it.value
            isExpanded = false
        },
        onShowRequestAction = {
            isExpanded = true
        },
        onDismissRequestAction = {
            isExpanded = false
        }
    )

    OutlinedTextField(
        value = propertyDescription,
        onValueChange = { propertyDescription = it },
        label = { Text("Informacion adicional") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 6,
        minLines = 4
    )

    OutlinedTextField(
        value = propertyPrice,
        onValueChange = { propertyPrice = it },
        label = { Text("Precio") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = propertyLocation,
        onValueChange = { propertyLocation = it },
        label = { Text("Ubicacion") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ChipFacilities(){
    // Lista de chips y un chip que el usuario puede agregar
    var chipInput by remember { mutableStateOf("") }
    val chipList = remember { mutableStateListOf<String>() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Center ,
    ) {
        Text(
            text = "Facilidades",
            style = MaterialTheme.typography.titleMedium
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chipList) { chip ->
            Row {
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text(chip) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Eliminar chip",
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .clickable { chipList.remove(chip) }
                        )
                    }
                )
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = chipInput,
            onValueChange = { chipInput = it },label = { Text("Agregar facilidades") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        IconButton(
            onClick = {
                if (chipInput.isNotBlank()) {
                    chipList.add(chipInput)
                    chipInput = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar facilidades"
            )
        }
    }
}

@Composable
fun SaveButton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            onClick = { /* Acción de guardar */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Save,
                contentDescription = "Settings",
                modifier = Modifier.padding(end = 2.dp)
            )
            Text("Guardar propiedad")
        }
    }
}

@Preview(widthDp = 500, heightDp = 2000, showBackground = true)
@Composable
fun Pw(navController: NavController? = null) {
    AddPropertyContent(paddingValues = PaddingValues(0.dp))
}
