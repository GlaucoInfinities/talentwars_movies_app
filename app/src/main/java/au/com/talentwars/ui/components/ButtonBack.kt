package au.com.talentwars.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import au.com.talentwars.R

@Composable
fun ButtonBack(backStack: NavHostController) {
    Row(
        modifier = Modifier.padding(top = 31.dp, start = 30.dp)
    ) {
        Button(
            onClick = { backStack.popBackStack() },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White.copy(alpha = 0.3f),
            ),
            modifier = Modifier.height(26.dp),
            shape = RoundedCornerShape(60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 9.dp),
                )
                TextInterRegular(
                    modifier = Modifier.padding(start = 8.dp, end = 9.dp),
                    text = "Back to Search",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}
