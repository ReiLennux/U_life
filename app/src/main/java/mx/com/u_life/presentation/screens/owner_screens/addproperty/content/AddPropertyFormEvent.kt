package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import android.net.Uri

sealed class AddPropertyFormEvent {
    data class PropertyNameChanged(val propertyName: String): AddPropertyFormEvent()
    data class PropertyDescriptionChanged(val propertyDescription: String): AddPropertyFormEvent()
    data class PropertyPriceChanged(val propertyPrice: Int): AddPropertyFormEvent()
    data class PropertyImagesChanged(val propertyImages: List<Uri>): AddPropertyFormEvent()
    data class PropertyRestrictionsChanged(val propertyRestrictions: List<String>): AddPropertyFormEvent()
    data class PropertyFacilitiesChanged(val propertyFacilities: List<String>): AddPropertyFormEvent()
    data class PropertyLocationChanged(val propertyLatitude: Double, val propertyLongitude: Double): AddPropertyFormEvent()
    data class PropertyTypeChanged(val propertyType: String): AddPropertyFormEvent()
    data class PropertyAddressChanged(val propertyAddress: String): AddPropertyFormEvent()
    data object Submit: AddPropertyFormEvent()

}