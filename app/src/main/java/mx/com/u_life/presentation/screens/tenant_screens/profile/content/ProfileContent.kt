package mx.com.u_life.presentation.screens.tenant_screens.profile.content


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.presentation.enums.Routes

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(

        modifier = modifier
            .verticalScroll(scrollState)
            .padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileHeader(
                viewModel = viewModel
            )
            BodyOptions(navController = navController)
            LogOut(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun ProfileHeader(viewModel: ProfileViewModel){

    val username by viewModel.userName.observeAsState(initial = "")
    val userType by viewModel.userType.observeAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageWithSetting()

            Spacer(modifier = Modifier.width(16.dp))

            AccountInfo(userName = username, userType = userType)
        }
    }
}

@Composable
fun ImageWithSetting(
    image: Int = R.drawable.fumo
){
    Box(modifier = Modifier){
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.align(Alignment.BottomEnd)) {

            IconButton(
                onClick = { /*TODO: Navegate to Account Setting*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Settings",
                    modifier = Modifier.size(30.dp)
                )
            }

        }
    }
}

@Composable
fun AccountInfo(
    userName: String,
    userType: String
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge,
            textDecoration = MaterialTheme.typography.titleLarge.textDecoration,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
        SimpleChip(text = userType)
    }
}

@Composable
fun SimpleChip(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun BodyOptions(navController: NavController) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            OptionItem(
                title = R.string.notification_option_title,
                body = R.string.notification_option_body,
                option = {
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        thumbContent = if (checked) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                },
                click = { checked = !checked }
            )
            OptionItem(
                title = R.string.verify_title,
                body = R.string.student_verify_body,
                option = {
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "Opcion para arrendadores",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                click = { navController.navigate(Routes.VERIFY_PROFILE.name) }
            )
            OptionItem(
                title = R.string.Info_option_title,
                body = R.string.Info_option_body,
                option = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Información de la aplicación",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                click = {}
            )
            OptionItem(
                title = R.string.Crash_Error_title,
                body = R.string.Crash_Error_body,
                option = {
                    Icon(
                        imageVector = Icons.Filled.Mail,
                        contentDescription = "Reportar problema",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                click = {context.sendMail(to = "22300101@uttt.edu.mx")}
            )
        }
    }
}

@Composable
fun OptionItem(
    @StringRes title: Int,
    @StringRes body: Int,
    option: @Composable (() -> Unit)? = null,
    click: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { click() }
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = body),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        option?.invoke()
    }
}


@Composable
fun LogOut(
    navController: NavController,
    viewModel: ProfileViewModel
){
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .height(32.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Cerrar Sesion",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable {
                    viewModel.logOut()
                    navController.navigate(Routes.HOME.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                }
                )
        }
    }

}


fun Context.sendMail(to: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(
            Intent.EXTRA_EMAIL, arrayOf(to),
        )
        intent.putExtra(
            Intent.EXTRA_SUBJECT, "Problemas con ULife"
        )
        intent.setPackage("com.google.android.gm")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "no se encontró la aplicacion de Gmail.", Toast.LENGTH_SHORT).show()
    } catch (t: Throwable) {
        Toast.makeText(this, "Ocurrió un error al intentar enviar el correo.", Toast.LENGTH_SHORT).show()
    }
}