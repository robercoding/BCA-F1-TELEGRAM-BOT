package data.repository.notifyraceweek

import data.Repository
import domain.model.NotifyRaceWeek
import domain.model.dao.NotifyRaceWeekEntity

interface NotifyRaceWeekRepository : Repository<NotifyRaceWeek> {
    fun saveNotifyRaceWeek(notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeekEntity
    override fun update(notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeek?
}