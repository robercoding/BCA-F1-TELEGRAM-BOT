package model.dto

data class GrandPrixDTO(
    var id: Int = -1,
    val circuit: CircuitDTO,
    val name: String
)