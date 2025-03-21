package com.sdc.aicookmate.model

class RecipeRepository(private val recipeDao: RecipeDao) {

    // 최근 레시피 가져오기
    suspend fun getRecentRecipes(): List<Recipe>? {
        return try {
            recipeDao.getRecentRecipes()
        } catch (e: Exception) {
            // 에러 처리
            null
        }
    }

    // 구독된 레시피 가져오기
    suspend fun getSubscribedRecipes(): List<Recipe>? {
        return try {
            recipeDao.getSubscribedRecipes()
        } catch (e: Exception) {
            // 에러 처리
            null
        }
    }

    // 최근 레시피에 추가
    suspend fun addToRecent(recentRecipe: RecentRecipe) {
        try {
            recipeDao.addToRecent(recentRecipe)
            recipeDao.maintainRecentLimit()
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    // 레시피 목록 삽입
    suspend fun insertRecipes(recipes: List<Recipe>) {
        try {
            recipeDao.insertRecipes(recipes)
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    // 모든 레시피 삭제
    suspend fun deleteAllRecipes() {
        try {
            recipeDao.deleteAllRecipes()
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    // 레시피 구독
    suspend fun subscribeRecipe(subscribedRecipe: SubscribedRecipe) {
        try {
            recipeDao.subscribeRecipe(subscribedRecipe)
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    // 레시피 구독 취소
    suspend fun unsubscribeRecipe(recipeId: Int) {
        try {
            recipeDao.unsubscribeRecipe(recipeId)
        } catch (e: Exception) {
            // 에러 처리
        }
    }
}