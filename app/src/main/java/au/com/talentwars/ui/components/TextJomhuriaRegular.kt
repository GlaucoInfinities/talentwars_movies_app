package au.com.talentwars.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import au.com.talentwars.R

@Composable
fun TextJomhuriaRegular(
    text: String,
    colorResource: Int? = null,
    color: Color? = null,
    modifier: Modifier? = Modifier,
    maxLines:Int = Int.MAX_VALUE,
    fontSize: TextUnit = 16.sp
) {
    val fontFamily = FontFamily(Font(R.font.jomhuria_regular))

    val resolvedColor = when {
        colorResource != null -> colorResource(id = colorResource)
        color != null -> color
        else -> Color.Black
    }

    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = fontSize,
        color = resolvedColor
    )

    Text(
        text = text,
        style = textStyle,
        modifier = modifier ?: Modifier,
        maxLines = maxLines
    )
}

