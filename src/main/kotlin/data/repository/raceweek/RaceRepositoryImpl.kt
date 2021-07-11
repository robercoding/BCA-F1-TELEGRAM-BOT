package data.repository.raceweek

import domain.model.dao.RaceEntity
import domain.model.dao.RaceTable
import domain.model.dao.toRaceDTO
import domain.model.dto.RaceDTO

class RaceRepositoryImpl : RaceRepository {

    override fun getRacesBySeason(season: Int): List<RaceDTO> {
        val races = RaceEntity.find { RaceTable.calendarRaceId eq 2021 }
        return races.map { it.toRaceDTO() }
    }

    override fun findById(id: Long): RaceDTO? = RaceEntity.findById(id)?.toRaceDTO()
}