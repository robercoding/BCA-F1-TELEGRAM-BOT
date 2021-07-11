package functionality.action.manual

import com.github.kotlintelegrambot.entities.ChatId

sealed class ManualActionEvent(open val chatId: ChatId) {
    data class GetNextRace(override val chatId: ChatId) : ManualActionEvent(chatId)
    data class GetCalendar(override val chatId: ChatId, val season: Int? = null) : ManualActionEvent(chatId)
    data class GetRaceDetails(override val chatId: ChatId, val raceId: Long) : ManualActionEvent(chatId)
}