package data.repository

import com.github.kotlintelegrambot.entities.ChatId
import common.utils.CalendarRaceYearNotFound
import common.utils.ChatAlreadyExist
import common.utils.ChatShouldExist
import common.utils.RaceDetailsNotFound
import domain.model.*
import domain.model.dao.*
import domain.model.dto.CalendarRaceYearDTO
import domain.model.dto.RaceDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class ActionRepository {

    fun createDefaultChatAndAlarm(chatId: ChatId): Int {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        val chat = ChatEntity.findById((chatId as ChatId.Id).id)
        if (chat != null) {
            throw ChatAlreadyExist()
        }
//
//        return AlarmTable.update({AlarmTable.id eq chat.alarm.id}) {
//            it[isActivated] = false
//                it[day] = alarmRaceWeek.day ?: alarm.day
//            it[hour] = alarmRaceWeek.hour ?: alarm.hour
//            it[minute] = alarmRaceWeek.minute ?: alarm.minute
//        }
        return 1
    }

    fun createChat() {

    }

    fun saveRemindRaceWeek(notifyRaceWeek: NotifyRaceWeek, chatId: ChatId): Int {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        val chat = ChatEntity.findById((chatId as ChatId.Id).id) ?: throw ChatShouldExist()
        val alarm = chat.notifyRaceWeek
        return NotifyRaceWeekTable.update({ NotifyRaceWeekTable.id eq alarm.id }) {
            it[isActivated] = notifyRaceWeek.isActivated
            it[day] = notifyRaceWeek.day
            it[hour] = notifyRaceWeek.hour
            it[minute] = notifyRaceWeek.minute
        }
    }

    fun getCalendarRace(season: Int = 2021): CalendarRaceYearDTO {
        return transaction {
            val calendarRaceYearEntity =
                CalendarRaceYearEntity.findById(season) ?: throw CalendarRaceYearNotFound(season)
            return@transaction calendarRaceYearEntity.toCalendarRaceYearDTO()
        }
    }

    fun getRaceDetails(raceId: Long): RaceDTO {
        return transaction {
            val raceEntity = RaceEntity.findById(raceId)
            return@transaction raceEntity?.toRaceDTO() ?: throw RaceDetailsNotFound()
        }
    }
}