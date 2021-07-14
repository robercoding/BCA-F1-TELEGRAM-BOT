package data.repository.chat

import domain.interfaces.ChatRepository
import domain.model.Chat
import domain.model.dao.*
import domain.model.dto.ChatDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ChatRepositoryImpl : ChatRepository {
    override fun findById(id: Long): ChatDTO? = transaction { return@transaction ChatEntity.findById(id)?.toChatDTO() }

    override fun update(chatDTO: ChatDTO): ChatDTO? {
        return transaction {
            ChatTable.update({ ChatTable.id eq chatDTO.id }) {
                it[timeZone] = chatDTO.timeZone
                it[description] = chatDTO.description
                it[title] = chatDTO.title
                it[username] = chatDTO.username
                it[type] = chatDTO.type.toString()
            }

            return@transaction findById(chatDTO.id)
        }
    }

    override fun saveChat(chat: Chat, notifyRaceWeekEntity: NotifyRaceWeekEntity): ChatDTO {
        return transaction {
            return@transaction ChatEntity.new(chat.id) {
                this.title = chat.title ?: ""
                this.description = chat.description ?: ""
                this.username = chat.username ?: ""
                this.type = chat.type.toString()
                this.timeZone = chat.timeZone?.toString()
                this.notifyRaceWeek = notifyRaceWeekEntity
            }.toChatDTO()
        }
    }
}