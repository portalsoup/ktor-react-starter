package ktortest

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.document
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ktortest.example.dao.UserDao
import ktortest.example.entity.User

fun main(args: Array<String>) {

    val userDao = UserDao()

    val user = User("John")

    userDao.saveUser(user)

    userDao.users.map {
        it.name
    }.forEach {
        println("Found $it")
    }

    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                println(call.request.document())
            }
        }
    }.start(wait = true)
}