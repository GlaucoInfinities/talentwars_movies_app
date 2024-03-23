package au.com.talentwars.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import au.com.talentwars.R

@Composable
fun CenteredCircularProgressIndicator() = CircularProgressIndicator(
    color = colorResource(id = R.color.green_top_bar),
    modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
)