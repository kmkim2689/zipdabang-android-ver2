package com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview

import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipes

data class SearchCategoryList(
    val categoryId: Int,
    val elements: Int,
    val recipeList: List<SearchRecipes>
)