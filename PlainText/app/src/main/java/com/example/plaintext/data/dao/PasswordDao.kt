package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {

    // Retorna todas as senhas como Flow para observação em tempo real
    @Query("SELECT * FROM passwords ORDER BY name ASC")
    abstract fun getAll(): Flow<List<Password>>

    // Busca uma senha específica pelo ID
    @Query("SELECT * FROM passwords WHERE id = :id")
    abstract suspend fun getById(id: Int): Password?

}
