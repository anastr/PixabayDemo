package com.anastr.pixabaydemo.data.repository

import com.anastr.pixabaydemo.domain.entity.User
import com.anastr.pixabaydemo.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class FakeLoginRepository @Inject constructor(): LoginRepository {

    override suspend fun login(email: String, password: String): User {
        delay(Random.nextLong(0, 1_000))
        return User(
            id = UUID.randomUUID().toString(),
            email = email,
        )
    }
}