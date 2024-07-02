package mx.com.u_life.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.R

/**
 * Componente genérico para un campo de texto personalizable en Jetpack Compose.
 *
 * @param value Valor actual del campo de texto.
 * @param onValueChange Callback para manejar cambios en el valor del campo de texto.
 * @param leadingIcon Icono que se mostrará a la izquierda del campo de texto.
 * @param keyboardType Tipo de teclado para el campo de texto.
 * @param placeholder Texto de marcador de posición para el campo de texto.
 * @param action Acción IME (por ejemplo, "Hecho", "Siguiente") para el teclado.
 * @param errorMessage Mensaje de error opcional que se mostrará debajo del campo de texto si hay un error.
 *                     Si es null, el campo de texto no mostrará ningún mensaje de error.
 * @param modifier Modificador opcional para personalizar la apariencia del campo de texto.
 */

@Composable
fun GenericTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @DrawableRes leadingIcon: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    @StringRes placeholder: Int,
    action: ImeAction = ImeAction.Default,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        placeholder = { Text(text = stringResource(id = placeholder)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = action
        ),
        singleLine = true,
        maxLines = 1,
        leadingIcon = leadingIcon?.let {
            {
                Icon(painter = painterResource(id = leadingIcon), contentDescription = "")
            }
        },
        isError = errorMessage != null,
    )

    errorMessage?.let {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewTextFieldGeneric() {
    var textFieldValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column {
        GenericTextField(
            value = textFieldValue,
            leadingIcon = R.drawable.send_message,
            onValueChange = {
                textFieldValue = it
                errorMessage = if (it.isEmpty()) "Este campo no puede estar vacío" else null
            },
            keyboardType = KeyboardType.Text,
            placeholder = R.string.app_name,
            action = ImeAction.Done,
            errorMessage = errorMessage
        )
    }
}