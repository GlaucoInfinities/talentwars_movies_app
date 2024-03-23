package au.com.talentwars.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.talentwars.R

@Composable
fun TextInterBold(
    text: String,
    colorResource: Int? = null,
    color: Color? = null,
    modifier: Modifier? = Modifier,
    maxLines:Int = Int.MAX_VALUE,
    fontSize: TextUnit = 16.sp
) {
    val fontFamily = FontFamily(Font(R.font.inter_bold))

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

@Composable
fun TextFieldInterBold(
    searchText: TextFieldValue,
    setSearchText: (TextFieldValue) -> Unit,
    colorResource: Int? = null,
    color: Color? = null,
    modifier: Modifier? = Modifier
) {
    val fontFamily = FontFamily(Font(R.font.inter_bold))

    val resolvedColor = when {
        colorResource != null -> colorResource(id = colorResource)
        color != null -> color
        else -> Color.Black
    }

    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        color = resolvedColor
    )
    TextField(
        value = searchText,
        onValueChange = { setSearchText(it) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle,
        placeholder = {
            TextInterBold(
                "Search"
            )
        },
        trailingIcon = {
            if (searchText.text.isNotEmpty()) {
                ClearIcon(onClearClicked = { setSearchText(TextFieldValue("")) })
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 57.dp)
            .padding(horizontal = 37.dp)
            .clip(shape = RoundedCornerShape(37.dp))
            .height(50.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
    )
}