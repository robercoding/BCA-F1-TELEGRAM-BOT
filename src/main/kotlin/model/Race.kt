package model

import java.util.*

data class Race(
    val dateFp: Date = Date(),
    val nameGrandPrix: String = "Empty",
    val nameCircuit: String = "Empty",
    val layoutCircuitUrl: String = "",
    val country: String = "Empty",
    val dateQualifying: Date = Date(),
    val dateRace: Date = Date(),
    val  isSprintQualifying: Boolean = false,
    val dateSprintQualifying : Date = Date(),
)
