package com.example.myapplication.utils

import com.example.myapplication.model.Meal

fun parseIngredients(meal: Meal): List<String> {
    val ingredients = mutableListOf<String>()
    val ingFields = listOf(
        meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5,
        meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
        meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
        meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
    )
    val measureFields = listOf(
        meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5,
        meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10,
        meal.strMeasure11, meal.strMeasure12, meal.strMeasure13, meal.strMeasure14, meal.strMeasure15,
        meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
    )

    for (i in ingFields.indices) {
        val ing = ingFields[i]?.trim()
        val meas = measureFields.getOrNull(i)?.trim()
        if (!ing.isNullOrEmpty()) {
            val combined = if (!meas.isNullOrEmpty()) "$ing - $meas" else ing
            ingredients.add(combined)
        }
    }
    return ingredients
}