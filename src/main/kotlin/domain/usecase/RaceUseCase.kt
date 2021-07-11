package domain.usecase

import common.utils.RaceDetailsNotFound
import data.repository.race.RaceRepository
import domain.model.dto.RaceDTO

class RaceUseCase(
    private val raceRepository: RaceRepository
) {
    fun getRaceDetailsById(raceId: Long): RaceDTO {
        return raceRepository.getRaceDetailsById(raceId) ?: throw RaceDetailsNotFound()
    }
}