package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {

    // Adicione esta query para buscar todas as senhas da tabela "passwords"
    @Query("SELECT * FROM passwords WHERE id = :id")
    abstract suspend fun getById(id: Int): Password?

}