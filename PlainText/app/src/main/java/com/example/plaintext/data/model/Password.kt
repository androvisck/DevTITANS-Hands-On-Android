package com.example.plaintext.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

// 1. Entidade do Room com TODOS os campos exigidos nos requisitos
@Entity(tableName = "passwords")
@Immutable
data class Password(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "notes") val notes: String? = null // Opcional, nulo por padrão
)

// 2. Classe de dados para UI/Serialização (mantendo o Parcelable e Serializable)
@Serializable
@Parcelize
data class PasswordInfo(
    val id: Int = 0,
    val name: String,
    val login: String,
    val password: String,
    val notes: String? = null
) : Parcelable

// --- Extensões de Mapeamento (Extremely useful para converter entre as duas) ---

fun Password.toInfo(): PasswordInfo = PasswordInfo(
    id = id,
    name = name,
    login = login,
    password = password,
    notes = notes
)

fun PasswordInfo.asEntity(): Password = Password(
    id = id,
    name = name,
    login = login,
    password = password,
    notes = notes
)