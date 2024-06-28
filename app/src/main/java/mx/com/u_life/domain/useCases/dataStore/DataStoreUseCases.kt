package mx.com.u_life.domain.useCases.dataStore

data class DataStoreUseCases(
    val setDataString: SetDataString,
    val getDataString: GetDataString,
    val setDataBoolean: SetDataBoolean,
    val getDataBoolean: GetDataBoolean,
    val setDataInt: SetDataInt,
    val getDataInt: GetDataInt
)