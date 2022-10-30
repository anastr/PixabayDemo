package com.anastr.pixabaydemo.domain.repository

import com.anastr.pixabaydemo.domain.entity.User

interface LoginRepository {
    suspend fun login(email: String, password: String): User
}
