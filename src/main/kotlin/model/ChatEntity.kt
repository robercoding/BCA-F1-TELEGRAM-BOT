package model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object ChatTable : LongIdTable("chat", "chat_id") {
    var alarmId = reference("alarm_id", AlarmTable, onDelete = ReferenceOption.CASCADE)

    var type = varchar("type", 10)
    var username = varchar("username", 255)
    var title = varchar("title", 10)
}

class ChatEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ChatEntity>(ChatTable)
    var alarm by AlarmEntity referencedOn ChatTable.alarmId

    var type by ChatTable.type
    var title by ChatTable.title
    var username by ChatTable.username
}

enum class CHAT_TYPE {
    PRIVATE, GROUP
}