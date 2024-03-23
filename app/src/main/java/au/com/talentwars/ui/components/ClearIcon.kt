package au.com.talentwars.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import au.com.talentwars.R

@Composable
fun ClearIcon(onClearClicked: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_close_black_24),
        contentDescription = "Clear text",
        tint = Color.Gray,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClearClicked() },
    )
}