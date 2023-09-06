package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.search.ui.SearchViewModel
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.SearchBorder
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun SearchBar(
    searchViewModel: SearchViewModel = SearchViewModel(),
    hintText : String
){
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange =  { text= it},
        textStyle = ZipdabangandroidTheme.Typography.sixteen_300_noSpacing,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = hintText,
                style = ZipdabangandroidTheme.Typography.sixteen_300_noSpacing,
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),

            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_icon_small),
                contentDescription = null,
                modifier = Modifier
                    .noRippleClickable {
                        searchViewModel.log(text)
                    }
            )

        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch ={
            searchViewModel.log(text)
            }
        ),
        shape = ZipdabangandroidTheme.Shapes.small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ZipdabangandroidTheme.Colors.SubBackground,
            unfocusedContainerColor = ZipdabangandroidTheme.Colors.SubBackground,
            focusedBorderColor = SearchBorder,
            unfocusedBorderColor = SearchBorder,
            focusedTrailingIconColor = NavBlack,
            unfocusedTrailingIconColor =  ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
            cursorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
        )

        )
}

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview(){
    SearchBar(hintText="찾는 상품을 검색해보세요")
}