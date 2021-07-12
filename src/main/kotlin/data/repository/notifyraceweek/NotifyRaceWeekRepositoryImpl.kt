@file:JvmName("NotifyRaceWeekRepositoryKt")

package data.repository.notifyraceweek

import domain.model.NotifyRaceWeek
import domain.model.dao.NotifyRaceWeekEntity
import domain.model.dao.toNotifyRaceWeek
import org.jetbrains.exposed.sql.transactions.transaction

class NotifyRaceWeekRepositoryImpl : NotifyRaceWeekRepository {

    override fun findById(id: Long): NotifyRaceWeek? = NotifyRaceWeekEntity.findById(id)?.toNotifyRaceWeek()

    override fun saveNotifyRaceWeek(notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeekEntity {
        return transaction {
            return@transaction NotifyRaceWeekEntity.new {
                this.isActivated = notifyRaceWeek.isActivated
                this.day = notifyRaceWeek.day
                this.hour = notifyRaceWeek.hour
                this.minute = notifyRaceWeek.minute
            }
        }
    }
}