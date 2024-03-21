package au.com.talentwars.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.theme.TalentwarsTheme

@Composable
fun SourceCompose(
    movie: Movies,
    defaultCheck: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    var checked by rememberSaveable { mutableStateOf(defaultCheck) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                checked = !checked
                onCheckedChange(checked)
            }) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Checkbox(
            modifier = Modifier.padding(10.dp),
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(checked)
            })
    }
}

@Preview("default")
@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun SourceComposeDarkPreview() {
    TalentwarsTheme {
        //Compose(Movies("id", "ABC News Night Mode"), true) { }
    }
}