package data.repository.chat

import domain.model.Chat
import domain.model.dao.ChatEntity
import domain.model.dao.NotifyRaceWeekEntity
import domain.model.dao.toChatDTO
import domain.model.dto.ChatDTO
import org.jetbrains.exposed.sql.transactions.transaction

class ChatRepositoryImpl : ChatRepository {
    override fun findById(id: Long): ChatDTO? = ChatEntity.findById(id)?.toChatDTO()

    override fun saveChat(chat: Chat, notifyRaceWeekEntity: NotifyRaceWeekEntity): ChatDTO {
        return transaction {
            return@transaction ChatEntity.new(chat.id) {
                this.title = chat.title ?: ""
                this.description = chat.description ?: ""
                this.username = chat.username ?: ""
                this.notifyRaceWeek = notifyRaceWeekEntity
            }.toChatDTO()
        }
    }
}