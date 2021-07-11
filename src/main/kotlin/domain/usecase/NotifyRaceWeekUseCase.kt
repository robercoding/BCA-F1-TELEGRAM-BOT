package domain.usecase

import com.github.kotlintelegrambot.entities.ChatId
import data.repository.ChatRepository
import data.repository.NotifyRaceWeekRepository

class NotifyRaceWeekUseCase(
    val chatRepository: ChatRepository,
    val notifyRaceWeekRepository: NotifyRaceWeekRepository
) {
    fun setOnNotifyRaceWeek(chatId: ChatId) {
        //Chat data.repository get the chat (Create if doesn't exist)
        //chatRepository
    }
}