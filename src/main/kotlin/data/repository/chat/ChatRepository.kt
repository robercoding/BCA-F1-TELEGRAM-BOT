package data.repository.chat

import data.Repository
import domain.model.Chat
import domain.model.dao.NotifyRaceWeekEntity
import domain.model.dto.ChatDTO

interface ChatRepository : Repository<ChatDTO> {
    fun saveChat(chat: Chat, notifyRaceWeekEntity: NotifyRaceWeekEntity): ChatDTO
    override fun update(chatDTO: ChatDTO): ChatDTO?
}