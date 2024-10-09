package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

sealed class PropertyFormEvent{
    data class Name(val name: String): PropertyFormEvent()
    data class PropertyType(val propertyType: String): PropertyFormEvent()
    data class AdditionalInfo(val additionalInfo: String): PropertyFormEvent()
    data class Price (val additionalInfo: String): PropertyFormEvent()
    data class Location(val additionalInfo: String): PropertyFormEvent()
    data class Facilities (val facilities: ArrayList<String>): PropertyFormEvent()
    data object Submit: PropertyFormEvent()
}
