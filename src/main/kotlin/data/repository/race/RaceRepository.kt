package data.repository.race

import domain.interfaces.Repository
import domain.model.dto.RaceDTO

interface RaceRepository : Repository<RaceDTO> {
    fun getRacesBySeason(season: Int): List<RaceDTO>
    fun getRaceDetailsById(raceId: Long): RaceDTO?
    override fun update(raceDTO: RaceDTO): RaceDTO?
}