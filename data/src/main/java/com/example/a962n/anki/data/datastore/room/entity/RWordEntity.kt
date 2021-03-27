package com.example.a962n.anki.data.datastore.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a962n.anki.domain.entity.AddWordEntity
import com.example.a962n.anki.domain.entity.WordEntity

@Entity(tableName = "words")
data class RWordEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "meaning")
    val meaning: String,
    @ColumnInfo(name = "extra")
    val extra: String
) {
    companion object {
        fun instance(wordEntity:WordEntity) : RWordEntity{
            return RWordEntity(
                id = wordEntity.id,
                name = wordEntity.name,
                meaning = wordEntity.meaning,
                extra = wordEntity.extra
            )
        }
        fun instance(addWordEntity: AddWordEntity) : RWordEntity{
            return RWordEntity(
                name = addWordEntity.name,
                meaning = addWordEntity.meaning,
                extra = addWordEntity.extra
            )
        }

    }


}