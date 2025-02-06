package com.app.githubu.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_last_view_user")
class LastViewUser() : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "idUser")
    var idUser: Int? = 0

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "avatar")
    var avatar: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "company")
    var company: String? = null

    @ColumnInfo(name = "blog")
    var blog: String? = null

    @ColumnInfo(name = "location")
    var location: String? = null

    @ColumnInfo(name = "totalFollower")
    var totalFollower: Int? = 0

    @ColumnInfo(name = "totalFollowing")
    var totalFollowing: Int? = 0

    @ColumnInfo(name = "totalRepo")
    var totalRepo: Int? = 0
}
