package domain.model

data class GrandPrix(
    var id: Int = -1,
    val circuit: Circuit,
    val name: String
)