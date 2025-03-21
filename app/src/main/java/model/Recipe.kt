package com.sdc.aicookmate.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(tableName = "recipes", indices = [Index(value = ["title"], unique = true)])
//data class Recipe(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val title: String,
//    val description: String,
//    val views: Int
//)

@Entity(tableName = "recipes", indices = [Index(value = ["title"], unique = true)])
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 고유 식별자 추가
    val title: String,        // 레시피 제목
    val thumbnail: String,    // 레시피 썸네일 URL
    val servings: String,     // 인분 정보
    val timeRequired: String, // 소요 시간
    val difficulty: String,   // 난이도
    val description: String,  // 레시피 설명
    val url: String           // 레시피 상세 URL
)

@Entity(tableName = "recent_recipes")
data class RecentRecipe(
    @PrimaryKey val recipeId: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "subscribed_recipes")
data class SubscribedRecipe(
    @PrimaryKey val recipeId: Int
)
