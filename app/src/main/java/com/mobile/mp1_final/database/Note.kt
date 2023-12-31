package com.mobile.mp1_final.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String?= null,

    @ColumnInfo(name = "species")
    var species: String?= null,

    @ColumnInfo(name = "img")
    var img: String?= null

): Parcelable