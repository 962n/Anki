package com.example.a962n.anki.data.datastore.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a962n.anki.domain.entity.WordEntity

@Entity(tableName = "random_words")
data class RRandomWordEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int = 0,
    @ColumnInfo(name = "word_id")
    val wordId: Int,
    @ColumnInfo(name = "swiped")
    val swiped: Boolean
) {
    companion object {
        fun instance(wordEntity: WordEntity) : RRandomWordEntity{
            return RRandomWordEntity(
                wordId = wordEntity.id,
                swiped = false
            )
        }
    }
}

