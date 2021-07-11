package domain.model

import domain.model.dao.CHAT_TYPE
import java.util.*

data class Chat(
    val id: Long,
    val username: String?,
    val description: String?,
    val title: String?,
    val timeZone: TimeZone?,
    val type: CHAT_TYPE,
)