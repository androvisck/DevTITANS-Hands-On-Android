package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PasswordDBStore {
    fun getList(): Flow<List<Password>>
    suspend fun add(password: Password): Long
    suspend fun update(password: Password)
    suspend fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    fun isEmpty(): Flow<Boolean>
}

class LocalPasswordDBStore(
    private val passwordDao: PasswordDao
) : PasswordDBStore {

    override fun getList(): Flow<List<Password>> {
        return passwordDao.getAll()
    }

    override suspend fun add(password: Password): Long {
        return passwordDao.insert(password)
    }

    override suspend fun update(password: Password) {
        passwordDao.update(password)
    }

    override suspend fun get(id: Int): Password? {
        return passwordDao.getById(id)
    }

    override suspend fun save(passwordInfo: PasswordInfo) {
        val password = passwordInfo.asEntity()
        if (password.id == 0) {
            // Novo registro, inserir
            add(password)
        } else {
            // Registro existente, atualizar
            update(password)
        }
    }

    override fun isEmpty(): Flow<Boolean> {
        return passwordDao.getAll().map { it.isEmpty() }
    }
}