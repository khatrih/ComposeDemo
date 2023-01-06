package com.example.composedemo.features_note.domain.model

import androidx.room.*


/*@Entity(
foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("uId"),
    childColumns = arrayOf("userId"),
    onDelete = ForeignKey.CASCADE
)]
)*/
@Entity
data class Packages(
    @PrimaryKey val pId: Int? = null,
    val userId: Int? = null,
    val title: String,
    val price: String,
    val description: String?,
    val payOption: String,
    val isDone: Boolean,
    @ColumnInfo(defaultValue = "0")
    val updates: Int
)

data class UserWithProduct(
    @Embedded
    val user: User,
    @Relation(parentColumn = "uId", entityColumn = "userId")
    val packages: List<Packages>
)