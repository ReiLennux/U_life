package mx.com.u_life.domain.models.properties

import mx.com.u_life.domain.models.generic.BaseCatalogModel

data class PropertyTypeModel(
        override val id: String,
        override val name: String,
        val description: String? = null,
): BaseCatalogModel()

