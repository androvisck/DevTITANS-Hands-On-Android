package com.example.plaintext.data.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.plaintext.data.PlainTextDatabase
import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.repository.LocalPasswordDBStore
import com.example.plaintext.data.repository.PasswordDBStore
import com.example.plaintext.ui.screens.hello.dbSimulator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

    // 1. Prover a instância única do Banco de Dados Room
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlainTextDatabase {
        return Room.databaseBuilder(
            context,
            PlainTextDatabase::class.java,
            "plaintext_database"
        ).build()
    }

    // 2. Prover o DAO extraído a partir do banco de dados instanciado acima
    @Provides
    fun providePasswordDao(database: PlainTextDatabase): PasswordDao {
        return database.passwordDao() // Supondo que você declarou essa função abstrata no PlainTextDatabase
    }

    // 3. Prover a interface PasswordDBStore mapeada para a implementação local
    @Provides
    @Singleton
    fun providePasswordDBStore(
        passwordDao: PasswordDao
    ): PasswordDBStore = LocalPasswordDBStore(passwordDao)

    @Provides
    @Singleton
    fun provideDBSimulator(): dbSimulator = dbSimulator()
}

