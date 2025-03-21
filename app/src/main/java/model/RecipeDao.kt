package com.sdc.aicookmate.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecipeDao {
    // 단일 레시피 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    // 다수의 레시피 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    // ID로 레시피 조회
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    // 모든 레시피 삭제
    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    // 최근 레시피 조회
    @Query("SELECT r.* FROM recipes r INNER JOIN recent_recipes rr ON r.id = rr.recipeId ORDER BY rr.timestamp DESC LIMIT 10")
    suspend fun getRecentRecipes(): List<Recipe>

    // 최근 레시피 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToRecent(recentRecipe: RecentRecipe)

    // 최근 레시피 수 제한 유지
    @Query("DELETE FROM recent_recipes WHERE recipeId NOT IN (SELECT recipeId FROM recent_recipes ORDER BY timestamp DESC LIMIT 10)")
    suspend fun maintainRecentLimit()

    // 레시피 구독
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun subscribeRecipe(subscribedRecipe: SubscribedRecipe)

    // 레시피 구독 취소
    @Query("DELETE FROM subscribed_recipes WHERE recipeId = :recipeId")
    suspend fun unsubscribeRecipe(recipeId: Int)

    // 구독된 레시피 조회
    @Query("SELECT r.* FROM recipes r INNER JOIN subscribed_recipes sr ON r.id = sr.recipeId")
    suspend fun getSubscribedRecipes(): List<Recipe>

    // 특정 레시피가 구독되었는지 확인
    @Query("SELECT COUNT(*) > 0 FROM subscribed_recipes WHERE recipeId = :recipeId")
    suspend fun isRecipeSubscribed(recipeId: Int): Boolean

    // 레시피 업데이트 및 최신 레시피 조회
    @Transaction
    suspend fun updateAndFetchRecipes(newRecipes: List<Recipe>): List<Recipe> {
        deleteAllRecipes()
        insertRecipes(newRecipes)
        return getRecentRecipes()
    }
}
