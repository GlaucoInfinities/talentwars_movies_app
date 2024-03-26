package au.com.talentwars.ui.rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import au.com.talentwars.ui.components.Navigation
import au.com.talentwars.ui.components.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Navigation(Screen.FavouritesScreen.route)
            }
        }
    }
}
