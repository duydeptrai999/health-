package com.studyai.health.data.network

import com.studyai.health.data.model.AiResponse
import com.studyai.health.data.model.Recommendation
import retrofit2.http.*

/**
 * Retrofit service interface for API calls
 */
interface ApiService {
    
    /**
     * Get AI response for a health question
     * 
     * @param message The user's question
     * @param includeHealthData Whether to include user's health data in the request
     * @return AI response
     */
    @POST("health/ai/chat")
    suspend fun getAiResponse(
        @Query("message") message: String,
        @Query("includeHealthData") includeHealthData: Boolean = true,
        @Body healthData: Map<String, Any>? = null
    ): AiResponse
    
    /**
     * Get personalized health recommendations based on health data
     * 
     * @param healthData Map of health metrics
     * @return List of recommendations
     */
    @POST("health/recommendations")
    suspend fun getRecommendations(
        @Body healthData: Map<String, Any>
    ): List<Recommendation>
    
    /**
     * Get diet plan recommendations
     * 
     * @param healthData Map of health metrics
     * @return Diet plan recommendations
     */
    @POST("health/diet-plan")
    suspend fun getDietPlan(
        @Body healthData: Map<String, Any>
    ): DietPlanResponse
    
    /**
     * Get exercise suggestions
     * 
     * @param healthData Map of health metrics
     * @return Exercise recommendations
     */
    @POST("health/exercise-plan")
    suspend fun getExercisePlan(
        @Body healthData: Map<String, Any>
    ): ExercisePlanResponse
}

/**
 * Response model for diet plan
 */
data class DietPlanResponse(
    val breakfast: List<MealItem>,
    val lunch: List<MealItem>,
    val dinner: List<MealItem>,
    val snacks: List<MealItem>,
    val dailyCalories: Int,
    val macroNutrients: MacroNutrients,
    val notes: String? = null
)

/**
 * Meal item
 */
data class MealItem(
    val name: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val portion: String,
    val imageUrl: String? = null
)

/**
 * Macro nutrients
 */
data class MacroNutrients(
    val protein: Float, // in grams
    val carbs: Float,   // in grams
    val fat: Float      // in grams
)

/**
 * Response model for exercise plan
 */
data class ExercisePlanResponse(
    val exercises: List<Exercise>,
    val totalCalories: Int,
    val durationMinutes: Int,
    val intensity: String,
    val notes: String? = null
)

/**
 * Exercise item
 */
data class Exercise(
    val name: String,
    val description: String,
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val intensity: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null
) 