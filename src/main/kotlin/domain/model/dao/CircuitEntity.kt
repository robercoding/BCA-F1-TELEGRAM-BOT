package domain.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

//TODO add circuit details like km for the freaks ones
object CircuitTable : IntIdTable("Circuits", "circuit_id") {
    val name = varchar("name", 100)
    val country = varchar("country", 50)
    val city = varchar("city", 50)
    val layoutCircuitUrl = varchar("layoutCircuitUrl", 255).default("")
}

/***Might in the future change the City/Country to another table where I can fetch the weather from api &
 * save in that table and store for the next years so we can show how it's been the weather for the past years
 * idk, crazy things that require an effort and have 0 impact on the end user but might be fun to do it
 *
 * Or just do an api weather call and don't store it!
 ***/
class CircuitEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CircuitEntity>(CircuitTable)

    var name by CircuitTable.name
    var country by CircuitTable.country
    var city by CircuitTable.city
    var layoutCircuitUrl by CircuitTable.layoutCircuitUrl
}

fun CircuitEntity.toCircuit(): Circuit = Circuit(
    id = this.id.value,
    name = this.name,
    country = this.country,
    city = this.city,
    layoutCircuitUrl = this.layoutCircuitUrl
)