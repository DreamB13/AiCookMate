package com.sdc.aicookmate.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recentRecipes = MutableLiveData<List<Recipe>>()
    val recentRecipes: LiveData<List<Recipe>> get() = _recentRecipes

    private val _subscribedRecipes = MutableLiveData<List<Recipe>>()
    val subscribedRecipes: LiveData<List<Recipe>> get() = _subscribedRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadRecentRecipes()
        loadSubscribedRecipes()
    }

    // 최근 레시피 로드
    private fun loadRecentRecipes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _recentRecipes.value = repository.getRecentRecipes()
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error loading recent recipes", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 구독된 레시피 로드
    private fun loadSubscribedRecipes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _subscribedRecipes.value = repository.getSubscribedRecipes()
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error loading subscribed recipes", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 최근 레시피에 추가
    fun addToRecent(recipe: Recipe) {
        viewModelScope.launch {
            repository.addToRecent(RecentRecipe(recipeId = recipe.id))
            loadRecentRecipes()
        }
    }

    // 레시피 구독 토글
    fun toggleSubscription(recipe: Recipe) {
        viewModelScope.launch {
            val isSubscribed = _subscribedRecipes.value?.any { it.id == recipe.id } ?: false
            if (isSubscribed) {
                repository.unsubscribeRecipe(recipe.id)
            } else {
                repository.subscribeRecipe(SubscribedRecipe(recipeId = recipe.id))
            }
            loadSubscribedRecipes()
        }
    }
}