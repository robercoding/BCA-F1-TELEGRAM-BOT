package domain.model.dao

import domain.model.CircuitEntity
import domain.model.CircuitTable
import domain.model.dto.GrandPrix
import domain.model.toDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GrandPrixTable : IntIdTable("GrandPrix", "grand_prix_id") {
    var circuit = reference("circuit_id", CircuitTable)

    var name = varchar("name", 100)
}

class GrandPrixEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GrandPrixEntity>(GrandPrixTable)

    val circuit by CircuitEntity referencedOn GrandPrixTable.circuit

    var name by GrandPrixTable.name
}

fun GrandPrixEntity.toDTO(): GrandPrix = GrandPrix(
    id = this.id.value,
    circuit = this.circuit.toDTO(),
    name = this.name
)