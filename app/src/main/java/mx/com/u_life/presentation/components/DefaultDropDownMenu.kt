package mx.com.u_life.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mx.com.u_life.domain.models.generic.GenericCatalogModel

/**
 * Componente personalizado de menú desplegable (dropdown)
 *
 * @param modifier Modificador de diseño Compose para personalizar la apariencia
 * @param label ID del recurso de string para la etiqueta del campo
 * @param selectedText Texto actualmente seleccionado que se muestra en el campo
 * @param isExpanded Controla si el menú desplegable está actualmente expandido
 * @param isEnabled Habilita o deshabilita la interacción con el componente (true por defecto)
 * @param items Lista de opciones disponibles para seleccionar (de tipo GenericCatalogModel)
 * @param onSelectedItem Callback que se ejecuta cuando se selecciona un ítem (recibe el modelo completo)
 * @param onShowRequestAction Callback para solicitar la expansión del menú
 * @param onDismissRequestAction Callback para solicitar el cierre del menú
 * @param errorMessage Mensaje de error opcional que se muestra debajo del campo (null por defecto)
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDropDownMenu(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    selectedText: String,
    isExpanded: Boolean,
    isEnabled: Boolean = true,
    items: List<GenericCatalogModel>,
    onSelectedItem: (GenericCatalogModel) -> Unit,
    onShowRequestAction: () -> Unit,
    onDismissRequestAction: () -> Unit,
    errorMessage: String? = null
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded && isEnabled,
        onExpandedChange = { if (isEnabled) onShowRequestAction() },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, isEnabled),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            enabled = isEnabled,
            label = { Text(stringResource(id = label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            isError = errorMessage != null
        )
        ExposedDropdownMenu(
            expanded = isExpanded && isEnabled,
            onDismissRequest = onDismissRequestAction
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.value) },
                    onClick = {
                        onSelectedItem(item)
                        onDismissRequestAction()
                    }
                )
            }
        }
    }
    errorMessage?.let {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
    )
}
