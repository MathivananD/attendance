package md.attendance.sl

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.ui.login.view_model.LoginViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.primary)
        setContentView(R.layout.activity_main)
    }
}
