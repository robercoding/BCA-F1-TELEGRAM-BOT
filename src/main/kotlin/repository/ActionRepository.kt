package repository

import com.github.kotlintelegrambot.entities.ChatId
import model.CalendarRaceYearEntity
import model.dto.CalendarRaceYearDTO
import model.toDTO
import org.jetbrains.exposed.sql.transactions.transaction
import utils.CalendarRaceYearNotAvailable
import java.util.*

class ActionRepository {
    fun saveRemindRaceWeek(timerTask: TimerTask, chatId: ChatId): Boolean {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        //return db.saveReminderRaceWeek(timerTask, chatId)
        return true
    }

    fun getCalendarRace(season: Int = 2021): CalendarRaceYearDTO {
        //TODO implement db to get raceCalendar saved in database
        return transaction {
            val calendarRaceYearEntity = CalendarRaceYearEntity.findById(season)
            return@transaction calendarRaceYearEntity?.toDTO() ?: throw CalendarRaceYearNotAvailable()
        }
    }
}