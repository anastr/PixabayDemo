package com.anastr.pixabaydemo.domain.repository

import com.anastr.pixabaydemo.domain.entity.User

interface RegisterRepository {
    suspend fun register(email: String, age: Int, password: String): User
}
