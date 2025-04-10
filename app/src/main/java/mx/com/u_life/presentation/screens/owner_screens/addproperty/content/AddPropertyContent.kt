package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.com.u_life.R
import mx.com.u_life.domain.models.generic.convertToGenericCatalog
import mx.com.u_life.presentation.components.ChipInputComponent
import mx.com.u_life.presentation.components.GenericDropDownMenu
import mx.com.u_life.presentation.components.GenericTextField

@Composable
fun AddPropertyContent(
    viewModel: AddPropertyViewModel = hiltViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FormHeader()
            PropertyForm(
                viewModel = viewModel,
                navController = navController
            )
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
            text = stringResource(id = R.string.add_property_description)
        )
    }
}

@Composable
fun FormTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text =  stringResource(id = R.string.add_property_title),
            style = MaterialTheme.typography.displaySmall,
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
fun PropertyForm(
    viewModel: AddPropertyViewModel,
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> PropertyFormBody(viewModel = viewModel)
                1 -> ExtraInfoForm(viewModel = viewModel)
            }
        }

        ButtonSection(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun PropertyFormBody(viewModel: AddPropertyViewModel) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        PropertyInfo(viewModel = viewModel)
    }
}

@Composable
fun ExtraInfoForm(viewModel: AddPropertyViewModel) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        ChipCategoryScreen(viewModel = viewModel)
        ImagesSection(viewModel = viewModel)
    }
}

@Composable
fun PropertyInfo(viewModel: AddPropertyViewModel) {
    val name by viewModel.propertyName.observeAsState("")
    val description by viewModel.propertyDescription.observeAsState("")
    val price by viewModel.propertyPrice.observeAsState("")
    val address by viewModel.propertyAddress.observeAsState("")
    val type by viewModel.propertyType.observeAsState("")
    val types by viewModel.types.observeAsState(initial = emptyList())
    val nameError by viewModel.propertyNameError.observeAsState("")
    val descriptionError by viewModel.propertyDescriptionError.observeAsState("")
    val typeError by viewModel.propertyTypeError.observeAsState(initial = "")
    val addressError by viewModel.propertyAddressError.observeAsState("")
    var isExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationHelper = remember { AddPropertyViewModel.LocationHelper(context) }

    var hasLocationPermission by remember {
        mutableStateOf(locationHelper.hasLocationPermission())
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if (isGranted) {
            scope.launch {
                val location = locationHelper.getLastKnownLocation()
                if (location != null) {
                    viewModel.onEvent(
                        AddPropertyFormEvent.PropertyLocationChanged(
                            location.latitude,
                            location.longitude
                        )
                    )
                    val addressResult = locationHelper.getAddressFromLocation(location)
                    if (addressResult != null) {
                        viewModel.onEvent(AddPropertyFormEvent.PropertyAddressChanged(addressResult))
                    }
                }
            }
        }
    }

    LaunchedEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            scope.launch {
                val location = viewModel.fetchLocation()
                if (location != null) {
                    viewModel.onEvent(
                        AddPropertyFormEvent.PropertyLocationChanged(location.latitude, location.longitude)
                    )
                    val addressResult = locationHelper.getAddressFromLocation(location)
                    if (addressResult != null) {
                        viewModel.onEvent(AddPropertyFormEvent.PropertyAddressChanged(addressResult))
                    }
                }
            }
        }
    }

    GenericTextField(
        value = name,
        onValueChange = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyNameChanged(it)
            )
        },
        placeholder = R.string.property_name,
        action = ImeAction.Next,
        errorMessage = nameError
    )

    GenericTextField(
        value = address,
        onValueChange = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyAddressChanged(it)
            )
        },
        placeholder = R.string.property_address,
        action = ImeAction.Next,
        errorMessage = addressError,
        maxLines = 6,
        minLines = 4
    )

    GenericTextField(
        value = description,
        onValueChange = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyDescriptionChanged(it)
            )
        },
        placeholder = R.string.property_description,
        action = ImeAction.Next,
        errorMessage = descriptionError,
        maxLines = 6,
        minLines = 4
    )

    GenericTextField(
        value = price,
        onValueChange = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyPriceChanged(it)
            )
        },
        leadingIcon = Icons.Filled.AttachMoney,
        placeholder = R.string.property_price,
        action = ImeAction.Next,
        keyboardType = KeyboardType.Number,
        errorMessage = descriptionError
    )

    GenericDropDownMenu(
        modifier = Modifier.fillMaxWidth(),
        label = R.string.property_type,
        selectedText = type,
        isExpanded = isExpanded,
        items = types.convertToGenericCatalog(),
        errorMessage = typeError,
        onSelectedItem = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyTypeChanged(it.value)
            )
            isExpanded = false
        },
        onShowRequestAction = {
            isExpanded = true
        },
        onDismissRequestAction = {
            isExpanded = false
        }
    )
}


@Composable
fun ChipCategoryScreen(
    viewModel: AddPropertyViewModel
) {
    val facilities by viewModel.propertyFacilities.observeAsState(initial = emptyList())
    val restrictions by viewModel.propertyRestrictions.observeAsState(initial = emptyList())
    val facilitiesError by viewModel.propertyFacilitiesError.observeAsState(initial = "")
    val restrictionsError by viewModel.propertyRestrictionsError.observeAsState(initial = "")

    ChipInputComponent(
        title = R.string.property_facilities_title,
        label = R.string.property_facilities_body,
        chipList = facilities,
        onChipListChanged = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyFacilitiesChanged(it)
            )
        },
        errorMessage = facilitiesError
    )

    ChipInputComponent(
        title = R.string.property_restrictions_title,
        label = R.string.property_restrictions_body,
        chipList = restrictions,
        onChipListChanged = {
            viewModel.onEvent(
                AddPropertyFormEvent.PropertyRestrictionsChanged(it)
            )
        },
        errorMessage = restrictionsError
    )
}

@Composable
fun ImagesSection(viewModel: AddPropertyViewModel) {

    var images by remember { mutableStateOf(listOf<Uri>()) }
    val errorImage by viewModel.propertyImagesError.observeAsState(initial = "")
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        images = images + uris
        viewModel.onEvent(
            AddPropertyFormEvent.PropertyImagesChanged(images.map { it })
        )
    }
    Text(text = stringResource(id = R.string.property_images))
    Column(
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        LazyRow {
            item {
                Column(
                    modifier = Modifier
                        .size(98.dp)
                        .padding(8.dp)
                        .border(
                            BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.primary
                            ),
                            shape = MaterialTheme.shapes.medium
                        ).clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            items(images) { imageUri ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
        errorImage?.let{
            Text(
                text = errorImage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Composable
fun ButtonSection(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    viewModel: AddPropertyViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage == 1) {
                        pagerState.animateScrollToPage(0)
                    } else {
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (pagerState.currentPage == 0) stringResource(id = R.string.property_cancel)
                else  stringResource(id = R.string.property_back)
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage == 0) {
                        pagerState.animateScrollToPage(1)
                    } else {
                        scope.launch {
                            viewModel.onEvent(
                                AddPropertyFormEvent.Submit
                            )
                        }
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (pagerState.currentPage == 0) stringResource(id = R.string.property_next)
                else   stringResource(id = R.string.property_save)
            )
        }
    }
}
