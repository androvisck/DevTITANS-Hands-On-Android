package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow

interface PasswordDBStore {
    fun getList(): Flow<List<Password>>
    suspend fun add(password: Password): Long
    suspend fun update(password: Password)
    fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    suspend fun isEmpty(): Flow<Boolean>
}

class LocalPasswordDBStore(
    private val passwordDao : PasswordDao
): PasswordDBStore {
    override fun getList(): Flow<List<Password>> {
        return passwordDao.getList()
    }

    override suspend fun add(password: Password): Long {
        return passwordDao.insert(password)
    }

    override suspend fun update(password: Password) {
        passwordDao.update(password)
    }

    override fun get(id: Int): Password? {
        return passwordDao.get(id)
    }

    override suspend fun save(passwordInfo: PasswordInfo) {
        if (passwordInfo.id == 0) {
            add(passwordInfo.toPassword())
        } else {
            update(passwordInfo.toPassword())
        }
    }

    override suspend fun isEmpty(): Flow<Boolean> {
        return passwordDao.isEmpty()
    }
}
