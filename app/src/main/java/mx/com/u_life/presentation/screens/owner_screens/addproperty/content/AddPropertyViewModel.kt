package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.properties.PropertyTypeModel
import mx.com.u_life.domain.useCases.catalogs.CatalogsUseCases
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertiesTypeUseCases: CatalogsUseCases
): ViewModel() {

    private val _types = MutableLiveData<List<PropertyTypeModel>>()
    val types: LiveData<List<PropertyTypeModel>> = _types


    private val _typesResponse = MutableStateFlow<Response<List<PropertyTypeModel>>?>(value = null)
    val typesResponse: MutableStateFlow<Response<List<PropertyTypeModel>>?> = _typesResponse

    init {
        getTypes()
    }

    private fun getTypes() {
        viewModelScope.launch {
            _typesResponse.value = Response.Loading
            val response = propertiesTypeUseCases.getPropertiesType.invoke()
            _typesResponse.value = response
        }
    }
    fun assignTypes(types: List<PropertyTypeModel>) {
        _types.value = types

    }

    fun resetInitialState(){
        _typesResponse.value = null
    }

}