package mx.com.u_life.domain.useCases.catalogs

import mx.com.u_life.data.repository.catalogs.CatalogsRepository
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.properties.PropertyTypeModel
import javax.inject.Inject

class GetPropertiesType @Inject constructor(private val _repository: CatalogsRepository) {
    suspend fun invoke(): Response<List<PropertyTypeModel>> = _repository.getPropertiesType()
}