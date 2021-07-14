package domain.interfaces

interface Repository<T> {
    fun findById(id: Long): T?
    fun update(objectZ: T): T?
}