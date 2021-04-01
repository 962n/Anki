package com.example.a962n.anki.data.datastore.repositoryImpl

import android.content.Context
import android.os.Environment
import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.ExtWordsFileRepository
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

class ExtWordsFileRepositoryImpl
constructor(
    private val context: Context
) : ExtWordsFileRepository {
    override fun fetch(filePath: String): Either<Failure, List<WordEntity>> {
        TODO("Not yet implemented")
    }

    override fun create(filePath: String, words: List<WordEntity>): Either<Failure, String> {
        val dirFile =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                ?: return Either.Left(Failure.HogeFailure)

        val writeFile = File(dirFile, filePath)
        try {
            val isCreated = writeFile.createNewFile()
            if (!isCreated) {
                return Either.Left(Failure.HogeFailure)
            }
            val printWriter = PrintWriter(FileWriter(writeFile))
            printWriter.use {
                words.forEach { entity ->
                    it.println("${entity.name},${entity.meaning},${entity.extra}")
                }
            }
        } catch (e: IOException) {
            return Either.Left(Failure.HogeFailure)
        }
        return Either.Right(writeFile.absolutePath)
    }
}