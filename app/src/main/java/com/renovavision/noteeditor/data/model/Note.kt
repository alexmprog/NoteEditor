package com.renovavision.noteeditor.data.model;

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.renovavision.noteeditor.utils.formatDate
import java.util.*

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Entity(tableName = "note")
class Note {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo
    var title: String? = null

    @ColumnInfo
    var text: String? = null

    @ColumnInfo(name = "create_date")
    var createdDate: Date? = null

    @ColumnInfo(name = "change_date")
    var changedDate: Date? = null

    constructor(title: String, createDate: Date) {
        this.title = title
        this.createdDate = createDate
        this.changedDate = createDate
    }

    constructor(){
        this.createdDate = Date()
        this.changedDate = Date()
    }

    fun getInfo(): String = "Title:\n$title\n" +
            "Created at:\n${formatDate(createdDate)}\n" +
            "Chnaged at:\n${formatDate(changedDate)}";
}
