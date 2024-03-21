package au.com.talentwars.ui.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import au.com.talentwars.R
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.popular.PopularMoviesViewModel
import au.com.talentwars.ui.theme.TalentwarsTheme

@Composable
fun ArticleDetailScreen(
    navController: NavHostController,
    movie: Movies,
    alreadySaved: Boolean = false
) {
    val viewModel: PopularMoviesViewModel = hiltViewModel()
    //val isSaved = if (alreadySaved) true else
       // viewModel.isSaved.collectAsState(initial = false).value
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_black_24),
                    contentDescription = stringResource(R.string.close)
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(.8f))
            Button(onClick = {
                navController.popBackStack()
                //if (isSaved) viewModel.deleteArticle(article)
                //else viewModel.saveArticle(article)
            }) {
                //Icon(
                    //painter = painterResource(
                        //if (isSaved) R.drawable.ic_delete_black_24 else R.drawable.ic_save_black_24
                   // ),
                    //contentDescription = if (isSaved) stringResource(id = R.string.delete)
                   // else stringResource(R.string.save)
                //)
            }
        }

    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

@Preview
@Composable
fun ScreenPreview() {
    TalentwarsTheme {
        //DetailScreen(
          //  rememberNavController(),
            ///movie = Movies(
               // "https://mob.studio",
                //"Gabriel",
                //"Test Article",
                //"Very big description here for test ui on the preview mode",
                //"", "", ""
            //)
        //)
    }
}