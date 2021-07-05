package utils

sealed class Answer<T> {
    data class Yes<T>(val data: T) : Answer<T>()
    data class Not<T>(val data: T? = null) : Answer<T>()
}
