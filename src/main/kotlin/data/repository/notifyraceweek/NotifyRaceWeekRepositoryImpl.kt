@file:JvmName("NotifyRaceWeekRepositoryKt")

package data.repository.notifyraceweek

import domain.model.NotifyRaceWeek
import domain.model.dao.NotifyRaceWeekEntity
import domain.model.dao.NotifyRaceWeekTable
import domain.model.dao.toNotifyRaceWeek
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NotifyRaceWeekRepositoryImpl : NotifyRaceWeekRepository {

    override fun findById(id: Long): NotifyRaceWeek? =
        transaction { NotifyRaceWeekEntity.findById(id)?.toNotifyRaceWeek() }

    override fun update(notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeek? {
        return transaction {
            NotifyRaceWeekTable.update({ NotifyRaceWeekTable.id eq notifyRaceWeek.id }) {
                it[isActivated] = notifyRaceWeek.isActivated
                it[day] = notifyRaceWeek.day
                it[hour] = notifyRaceWeek.hour
                it[minute] = notifyRaceWeek.minute
            }

            return@transaction findById(notifyRaceWeek.id)
        }
    }

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