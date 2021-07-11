package domain.model.dto

import domain.model.GrandPrix
import java.util.*

data class RaceDTO(
    val season: Int,
    val grandPrix: GrandPrix,
    val weekRace: Int,
    val dateQualifying: Date = Date(),
    val dateRace: Date = Date(),
    val isSprintQualifying: Boolean,
    val dateSprintQuailifying: Date,
    var id: Long = dateQualifying.time + dateRace.time,
)