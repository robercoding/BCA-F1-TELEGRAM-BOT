package data.repository.race

import common.utils.RaceDetailsNotFound
import common.utils.toLocalDateTime
import domain.model.dao.RaceEntity
import domain.model.dao.RaceTable
import domain.model.dao.toRaceDTO
import domain.model.dto.RaceDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class RaceRepositoryImpl : RaceRepository {

    override fun getRacesBySeason(season: Int): List<RaceDTO> {
        return transaction {
            val races = RaceEntity.find { RaceTable.calendarRaceId eq 2021 }
            return@transaction races.map { it.toRaceDTO() }
        }
    }

    override fun getRaceDetailsById(raceId: Long): RaceDTO? {
        return transaction {
            val raceEntity = RaceEntity.findById(raceId)
            return@transaction raceEntity?.toRaceDTO()
        }
    }

    override fun update(raceDTO: RaceDTO): RaceDTO? {
        return transaction {
            RaceTable.update({ RaceTable.id eq raceDTO.id }) {
                it[dateRace] = raceDTO.dateRace.toLocalDateTime()
                it[dateQualifying] = raceDTO.dateQualifying.toLocalDateTime()
                it[dateSprintQualifying] = raceDTO.dateSprintQuailifying.toLocalDateTime()
                it[isSprintQualifying] = raceDTO.isSprintQualifying
                it[weekRace] = raceDTO.weekRace
            }

            return@transaction findById(raceDTO.id)
        }
    }

    override fun findById(id: Long): RaceDTO = RaceEntity.findById(id)?.toRaceDTO() ?: throw RaceDetailsNotFound()
}