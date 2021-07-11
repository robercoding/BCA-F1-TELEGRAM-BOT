package domain.model.dto

import domain.model.NotifyRaceWeek
import domain.model.dao.CHAT_TYPE

data class ChatDTO(
    val id: Long,
    val notifyRaceWeek: NotifyRaceWeek,
    val username: String,
    val title: String,
    val description: String,
    val timeZone: String,
    val type: CHAT_TYPE,
)
