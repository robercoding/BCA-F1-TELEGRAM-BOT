package data.repository

import domain.model.NotifyRaceWeekEntity
import domain.model.dao.ChatTable
import org.jetbrains.exposed.sql.insert

class ChatRepository {
    fun createChat(notifyRaceWeekEntity: NotifyRaceWeekEntity) {
        ChatTable.insert {
            it[title] = "asdasd"
            it[type] = "asdasd"
            it[username] = "asdasdasd"
            it[alarmId] = notifyRaceWeekEntity.id
        }

    }
}