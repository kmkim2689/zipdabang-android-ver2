package com.zipdabang.zipdabang_android.module.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.TextFieldBasic
import com.zipdabang.zipdabang_android.ui.component.TextFieldForContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(ZipdabangandroidTheme.Shapes.smallRoundedTop),
                ) {
                    Column(){
                        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, name2: String, modifier: Modifier = Modifier) {
    Column(){
        Text(
            text = "$name",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.logo,
            color = ZipdabangandroidTheme.Colors.Choco,)
        Text(
            text = "$name2",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.underLogo,
            color = ZipdabangandroidTheme.Colors.Choco,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZipdabangandroidTheme {
        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
    }
}