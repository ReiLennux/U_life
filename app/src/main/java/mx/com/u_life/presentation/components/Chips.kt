package mx.com.u_life.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ChipInputComponent(
    @StringRes title: Int,
    @StringRes label: Int,
    chipList: List<String>,
    onChipListChanged: (List<String>) -> Unit,
    errorMessage: String? = null
) {
    var chipInput by remember { mutableStateOf("") }
    val mutableChipList = remember { chipList.toMutableStateList() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mutableChipList) { chip ->
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text(chip) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Remove chip",
                            modifier = Modifier.clickable {
                                mutableChipList.remove(chip)
                                onChipListChanged(mutableChipList)
                            }
                        )
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = chipInput,
                onValueChange = { chipInput = it },
                label = { Text(text = stringResource(id = label)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (chipInput.isNotBlank()) {
                            mutableChipList.add(chipInput)
                            chipInput = ""
                            onChipListChanged(mutableChipList)
                        }
                    }
                ),
                isError = errorMessage != null
            )

            IconButton(
                onClick = {
                    if (chipInput.isNotBlank()) {
                        mutableChipList.add(chipInput)
                        chipInput = ""
                        onChipListChanged(mutableChipList)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Chip"
                )
            }
        }
        errorMessage?.let{
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
