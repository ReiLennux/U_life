package mx.com.u_life.presentation.screens.tenant_screens.config.verification.content

import android.Manifest
import androidx.camera.core.Preview
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.core.di.camera.IDAnalyzer
import mx.com.u_life.presentation.components.MessageDialogWithIcon
import mx.com.u_life.presentation.components.GenericCard


@Composable
fun VerificationContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: VerificationViewModel = hiltViewModel(),
    navController: NavController
){
    val scrollState = rememberScrollState()
    val isVerifiedUser by viewModel.isVerifiedUser.observeAsState(false)

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(paddingValues)

    ) {
        if (!isVerifiedUser){
            VerificationHeader()
            IDScannerScreen( viewModel = viewModel)
        }else {
            VerifiedContent(navController = navController)
        }
    }
}

@Composable
fun VerifiedContent(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MessageDialogWithIcon(
            onDismissRequest = { navController.navigate("home") },
            onConfirmation = { navController.navigate("home") },
            dialogTitle = R.string.verification_dialog_title,
            dialogText = R.string.verification_dialog_description,
            icon = Icons.Filled.Verified
        )
    }
}

@Composable
fun VerificationHeader(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =  stringResource(id = R.string.verification_title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
        ) {
            Text(
                text = stringResource(id = R.string.verification_description),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}


@Composable
fun IDScannerScreen(
    viewModel: VerificationViewModel
) {
    val context = LocalContext.current
    val hasPermission by viewModel.hasCameraPermission.observeAsState(false)
    var recognizedText by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updatePermissionResult(isGranted)
    }

    //Permissions Checker
    LaunchedEffect(Unit) {
        viewModel.checkCameraPermission(context)
        if (!viewModel.hasCameraPermission.value!!) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasPermission) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp) // altura específica
                .padding(8.dp)
        ) {
            CameraPreview(
                onTextRecognized = { text ->
                    recognizedText = text
                },
                modifier = Modifier.matchParentSize()
            )

            Text(
                text = "Enfoca tu identificación",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            if (recognizedText.isNotEmpty()) {
                ScanResultDialog(
                    text = recognizedText,
                    onDismiss = { recognizedText = "" }
                )
            }
        }
    } else {
        CameraPermissions(permissionLauncher = permissionLauncher)
    }
}

@Composable
fun CameraPermissions(
    permissionLauncher :  ManagedActivityResultLauncher<String, Boolean>
) {
    GenericCard(
        title = R.string.permissions_camera_title,
        description = R.string.permissions_camera
    ) {
        Button(
            onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        ) {
            Text(text = stringResource(id = R.string.permissions_generic_button))
        }
    }
}

@Composable
fun CameraPreview(
    onTextRecognized: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val frameLayout = android.widget.FrameLayout(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val previewView = PreviewView(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }

            frameLayout.addView(previewView)

            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(executor, IDAnalyzer { text ->
                            onTextRecognized(text)
                        })
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageAnalysis
                    )
                } catch (exc: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", exc)
                }
            }, executor)

            frameLayout
        },
        modifier = modifier
            .height(240.dp)
            .fillMaxWidth()
    )
}



@Composable
fun ScanResultDialog(
    text: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Identificación escaneada") },
        text = {
            Column {
                Text("Contenido detectado:")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}