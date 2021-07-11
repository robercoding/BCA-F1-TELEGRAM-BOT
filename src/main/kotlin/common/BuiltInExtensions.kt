package common

import com.github.kotlintelegrambot.entities.ChatId
import domain.model.Chat
import domain.model.dao.CHAT_TYPE
import domain.model.dto.ChatDTO


//ToDomainChat
fun com.github.kotlintelegrambot.entities.Chat.toChat(): Chat = Chat(
    this.id,
    this.username,
    this.description,
    this.title,
    timeZone = null,
    CHAT_TYPE.valueOf(this.type.toUpperCase())
)

//ToChatId
fun Chat.toChatId(): ChatId = ChatId.Id(this.id)
fun Long.toChatId() = ChatId.fromId(this)
fun com.github.kotlintelegrambot.entities.Chat.toChatId() = ChatId.fromId(this.id)
fun ChatDTO.toChatId() = ChatId.fromId(this.id)


//GetId
fun ChatId.getId(): Long = (this as ChatId.Id).id