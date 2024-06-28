package mx.com.u_life.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.R

/**
 * Componente genérico para un botón personalizable con borde en Jetpack Compose.
 *
 * @param text Texto que se mostrará en el botón.
 * @param icon Icono que se mostrará junto al texto del botón.
 * @param shape Forma del borde del botón (por defecto, redondeado).
 * @param onClick La acción que se ejecutará cuando se haga clic en el botón.
 * @param backgroundColor Color de fondo del botón.
 * @param contentColor Color del contenido del botón (texto e icono).
 * @param modifier Modificador opcional para personalizar la apariencia del botón.
 */

@Composable
fun GenericOutlinedButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int? = null,
    @DrawableRes icon: Int? = null,
    shape: Shape = RoundedCornerShape(50.dp),
    onClick: () -> Unit,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = Color.Black,
) {
    OutlinedButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        // Icono del botón, si está presente
        icon?.let {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.width(24.dp)
            )
            if (text != null){
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        text?.let {
            // Texto del botón
            Text(
                text = stringResource(id = text),
            )
        }
    }
}

@Preview
@Composable
fun PreviewGenericOutlinedButton() {
    GenericOutlinedButton(
        icon = R.drawable.send_message,
        onClick = { /* Acción cuando se hace clic */ },
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    )
}