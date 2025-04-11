package mx.com.u_life.data.repository.catalogs

import com.google.firebase.firestore.QuerySnapshot
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.data.network.catalogs.CatalogsService
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.generic.GenericCatalogModel
import mx.com.u_life.domain.models.properties.PropertyTypeModel
import javax.inject.Inject

class CatalogsRepository @Inject constructor(
    private val _service: CatalogsService
) {

    suspend fun getPropertiesType(): Response<List<PropertyTypeModel>> {
        return try {
            val querySnapshot: QuerySnapshot = _service.getPropertiesType()

            val catalogList = querySnapshot.convertToGenericCatalog()

            Response.Success(data = catalogList)
        } catch (e: Exception) {
            Response.Error(exception = e)
        }
    }
}

private fun QuerySnapshot.convertToGenericCatalog(): List<PropertyTypeModel> {
    return this.documents.map { document ->
        val id = document.id
        val name = document.getString(Constants.RENT_FIELD_NAME) ?: "Unknown"
        PropertyTypeModel(id = id, name = name)
    }
}
