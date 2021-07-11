package domain.model.dto

data class GrandPrix(
    var id: Int = -1,
    val circuit: Circuit,
    val name: String
)