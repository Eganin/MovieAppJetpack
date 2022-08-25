package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.TagLineColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

@Composable
fun TopBarMovieList(typeMovie : String){
    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_combined_shape),
            tint = TagLineColor,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(start = 16.dp, top = 18.dp)
        )
        Text(
            text = typeMovie, modifier = Modifier.padding(top = 19.dp, start = 12.dp),
            style = TextStyle(fontSize = 14.sp, color = White, fontWeight = FontWeight.Bold)
        )
    }
}