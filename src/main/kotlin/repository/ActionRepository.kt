package repository

import com.github.kotlintelegrambot.entities.ChatId
import model.CalendarRaceYearEntity
import model.RaceEntity
import model.dto.CalendarRaceYearDTO
import model.dto.RaceDTO
import model.toDTO
import org.jetbrains.exposed.sql.transactions.transaction
import utils.CalendarRaceYearNotFound
import utils.RaceDetailsNotFound
import java.util.*

class ActionRepository {
    fun saveRemindRaceWeek(timerTask: TimerTask, chatId: ChatId): Boolean {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        //return db.saveReminderRaceWeek(timerTask, chatId)
        return true
    }

    fun getCalendarRace(season: Int = 2021): CalendarRaceYearDTO {
        return transaction {
            val calendarRaceYearEntity = CalendarRaceYearEntity.findById(season)
            return@transaction calendarRaceYearEntity?.toDTO() ?: throw CalendarRaceYearNotFound()
        }
    }

    fun getRaceDetails(raceId: Long): RaceDTO {
        return transaction {
            val raceEntity = RaceEntity.findById(raceId)
            return@transaction raceEntity?.toDTO() ?: throw RaceDetailsNotFound()
        }
    }
}