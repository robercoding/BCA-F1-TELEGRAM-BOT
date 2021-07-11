package presentation.functionality.action.automatic

import com.github.kotlintelegrambot.entities.ChatId
import domain.model.Chat

sealed class AutomaticActionEvent(open val chatId: ChatId) {
    data class RemindRaceWeek(override val chatId: ChatId, val chat: Chat, val alarmValues: String) :
        AutomaticActionEvent(chatId)

    data class DisableRemindRaceWeek(override val chatId: ChatId) : AutomaticActionEvent(chatId)
}
