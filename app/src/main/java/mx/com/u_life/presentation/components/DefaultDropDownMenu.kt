package mx.com.u_life.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.com.u_life.domain.models.generic.GenericCatalogModel

@Composable
fun DefaultDropDownMenu(
    modifier: Modifier,
    label: String,
    selectedText: String,
    isExpanded: Boolean,
    isEnabled: Boolean = false, //tap
    isReadOnly: Boolean = true, //write
    items: List<GenericCatalogModel>,
    onValueChangeAction: (String) -> Unit,
    onSelectedItem: (GenericCatalogModel) -> Unit,
    onShowRequestAction: () -> Unit,
    onDismissRequestAction: () -> Unit,
) {
    val icon = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .clickable { onShowRequestAction() }
                    .weight(1f),
                value = selectedText,
                onValueChange = {
                    onValueChangeAction(it)
                },
                enabled = isEnabled,
                readOnly = isReadOnly,
                label = { Text(text = label) },
                trailingIcon = {
                    Icon(imageVector = icon, contentDescription = null)
                }
            )
        }

        DefaultItemsDropdownMenu(
            modifier = Modifier.fillMaxWidth(0.9f),
            isExpanded = isExpanded,
            items = items,
            onSelectedItem = { onSelectedItem(it) },
            onDismissRequestAction = { onDismissRequestAction() }
        )
    }
}

@Composable
fun DefaultItemsDropdownMenu(
    modifier: Modifier,
    isExpanded: Boolean,
    items: List<GenericCatalogModel>,
    onSelectedItem: (GenericCatalogModel) -> Unit,
    onDismissRequestAction: () -> Unit
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismissRequestAction() },
        modifier = modifier
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onSelectedItem(item)
                    onDismissRequestAction()
                },
                text = {
                    Text(
                        text = item.value,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            )
        }
    }
}
