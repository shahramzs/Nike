package com.example.nike.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    var discount: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var code: String,
    var price: Int,
    var previousPrice:Int,
    var description:String,
    var image:String,
    var status: Int,
    var title: String,
    var isFavorite:Boolean = false
) : Parcelable




const val SORT_LATEST = 0
const val SORT_POPULAR = 1
const val SORT_PRICE_DESC = 2
const val SORT_PRICE_ASC = 3
