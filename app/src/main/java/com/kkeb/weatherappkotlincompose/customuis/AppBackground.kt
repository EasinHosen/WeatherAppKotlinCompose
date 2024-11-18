package com.kkeb.weatherappkotlincompose.customuis

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBackground(
    @DrawableRes photoId: Int,
    modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = photoId),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier.background(Color.Black)
//            .fillMaxSize()
            .alpha(0.7f)
            .blur(8.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
    )
}