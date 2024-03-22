package au.com.talentwars.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import au.com.talentwars.ui.components.Navigation
import au.com.talentwars.ui.components.Screen

class DetailsMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { Navigation(Screen.DetailScreen.route) }
        }
    }
}