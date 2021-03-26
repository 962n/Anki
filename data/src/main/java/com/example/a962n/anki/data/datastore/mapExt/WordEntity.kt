package com.example.a962n.anki.data.datastore.mapExt

import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import com.example.a962n.anki.domain.entity.WordEntity

fun WordEntity.Companion.instance(rWordEntity:RWordEntity) : WordEntity {
    return WordEntity(
        rWordEntity.id,
        rWordEntity.name,
        rWordEntity.meaning,
        rWordEntity.extra
    )
}