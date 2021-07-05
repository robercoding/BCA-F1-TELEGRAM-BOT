package config

import org.jetbrains.exposed.sql.Database

object DBConfig {
    private const val connectionName = ""
    private const val hostname = ""
    private const val port = ""
    private const val typeDBMS = "" //Example: mysql
    private const val timezone = ""
    const val url = "jdbc:$typeDBMS://$hostname:$port/$connectionName?serverTimezone=$timezone"

    private const val driver = "" //Example com.mysql.jdbc.Driver

    private const val user = ""
    private const val password = ""

    //You can config your database here!
    fun setup(isInDebugMode: Boolean = false) {
        if (!isInDebugMode) {
            connectToRealDatabase()
        } else {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
            loadExamplesToWork()
        }
    }

    private fun connectToRealDatabase() {
        Database.connect(
            url, driver = driver,
            user = user, password = password
        )
    }

    private fun loadExamplesToWork() {
        //TODO add example adding to database
    }
}