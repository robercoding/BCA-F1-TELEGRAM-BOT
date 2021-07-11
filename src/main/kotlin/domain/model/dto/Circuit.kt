package domain.model.dto

data class Circuit(
    var id: Int = -1,
    val name: String,
    val country: String,
    val city: String,
    val layoutCircuitUrl: String
)
