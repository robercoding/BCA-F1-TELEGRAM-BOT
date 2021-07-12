package presentation.functionality.action.automatic

import com.github.kotlintelegrambot.entities.ChatId
import domain.model.Chat

sealed class AutomaticActionEvent(open val chatId: ChatId) {
    data class SetNotifyRaceWeek(override val chatId: ChatId, val chat: Chat, val alarmValues: String) :
        AutomaticActionEvent(chatId)

    data class UnsetNotifyRaceWeek(override val chatId: ChatId, val chat: Chat) :
        AutomaticActionEvent(chatId)

    data class EditRemindRaceWeek(override val chatId: ChatId, val chat: Chat, val alarmValues: String) :
        AutomaticActionEvent(chatId)

    data class DisableRemindRaceWeek(override val chatId: ChatId) : AutomaticActionEvent(chatId)
}
