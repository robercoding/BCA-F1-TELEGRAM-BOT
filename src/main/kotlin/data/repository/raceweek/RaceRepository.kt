package data.repository.raceweek

import data.Repository
import domain.model.dto.RaceDTO

interface RaceRepository : Repository<RaceDTO> {
    fun getRacesBySeason(season: Int): List<RaceDTO>
}