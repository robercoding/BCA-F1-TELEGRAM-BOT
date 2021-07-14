package domain.model.dao

import domain.model.dto.ChatDTO
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

const val defaultTimeZone = "Europe/Madrid"

object ChatTable : LongIdTable("chat", "chat_id") {
    var alarmId = reference("alarm_id", NotifyRaceWeekTable, onDelete = ReferenceOption.CASCADE)

    var type = varchar("type", 10)
    var username = varchar("username", 255)
    var title = varchar("title", 10)
    var description = varchar("description", 255)
    var timeZone = varchar("timezone", 255).nullable()
}

class ChatEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ChatEntity>(ChatTable)

    var notifyRaceWeek by NotifyRaceWeekEntity referencedOn ChatTable.alarmId

    var type by ChatTable.type
    var title by ChatTable.title
    var username by ChatTable.username
    var description by ChatTable.description
    var timeZone by ChatTable.timeZone
}

fun ChatEntity.toChatDTO() = ChatDTO(
    id = this.id.value,
    notifyRaceWeek = this.notifyRaceWeek.toNotifyRaceWeek(),
    title = this.title,
    description = this.description,
    username = this.username,
    type = CHAT_TYPE.valueOf(this.type),
    timeZone = this.timeZone
)

enum class CHAT_TYPE {
    CHANNEL, PRIVATE, GROUP, SUPERGROUP
}