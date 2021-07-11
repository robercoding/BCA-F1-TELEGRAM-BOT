@file:JvmName("NotifyRaceWeekRepositoryKt")

package data.repository.notifyraceweek

import common.utils.ChatShouldExist
import common.utils.NotifyRaceWeekShouldExist
import domain.model.NotifyRaceWeek
import domain.model.dao.ChatEntity
import domain.model.dao.NotifyRaceWeekEntity
import domain.model.dao.NotifyRaceWeekTable
import domain.model.dao.toNotifyRaceWeek
import org.jetbrains.exposed.sql.update

const val ALARM_RW_DEFAULT_DAY = 4
const val ALARM_RW_DEFAULT_HOUR = 12
const val ALARM_RW_DEFAULT_MINUTE = 0

class NotifyRaceWeekRepositoryImpl : NotifyRaceWeekRepository {

    fun createGenericDeactivatedAlarm(): NotifyRaceWeekEntity = NotifyRaceWeekEntity.genericDeactivatedAlarm()

    /***
     * Set alarm for the first time, setting the provided values or defaults ones.
     ***/
    fun setAlarmFirstTime(chatId: Long, day: Int? = null, hour: Int? = null, minute: Int? = null) {
        val chat = ChatEntity.findById(chatId) ?: throw ChatShouldExist()

        val alarm = NotifyRaceWeekEntity.findById(chat.notifyRaceWeek.id) ?: throw NotifyRaceWeekShouldExist()
        alarm.isActivated = false
        alarm.day = ALARM_RW_DEFAULT_DAY
        alarm.hour = ALARM_RW_DEFAULT_HOUR
        alarm.minute = ALARM_RW_DEFAULT_MINUTE

        update(alarm)
    }

    /***
     * Update alarm with provided values or if null then the already settled values.
     ***/
    private fun updateAlarm(chatId: Long, isActivated: Boolean, day : Int? = null, hour: Int? = null, minute: Int? = null){
        val chat = ChatEntity.findById(chatId) ?: throw ChatShouldExist()

        val alarm = NotifyRaceWeekEntity.findById(chat.notifyRaceWeek.id) ?: throw NotifyRaceWeekShouldExist()
        alarm.isActivated = isActivated
        alarm.day = day ?: alarm.day
        alarm.hour = hour ?: alarm.hour
        alarm.minute = minute ?: alarm.minute

        update(alarm)
    }

    private fun update(notifyRaceWeek: NotifyRaceWeekEntity) {
        NotifyRaceWeekTable.update({ NotifyRaceWeekTable.id eq notifyRaceWeek.id }) {
            it[isActivated] = notifyRaceWeek.isActivated
            it[day] = notifyRaceWeek.day
            it[hour] = notifyRaceWeek.hour
            it[minute] = notifyRaceWeek.minute
        }
    }

    override fun findById(id: Long): NotifyRaceWeek? = NotifyRaceWeekEntity.findById(id)?.toNotifyRaceWeek()

    override fun saveNotifyRaceWeek(notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeekEntity {
        return NotifyRaceWeekEntity.new {
            this.isActivated = notifyRaceWeek.isActivated
            this.day = notifyRaceWeek.day
            this.hour = notifyRaceWeek.hour
            this.minute = notifyRaceWeek.minute
        }
    }
}