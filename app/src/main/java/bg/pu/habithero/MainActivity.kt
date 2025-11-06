package bg.pu.habithero

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import bg.pu.habithero.databinding.ScreenHabitHubBinding

class MainActivity : ComponentActivity() {

    private lateinit var uiBindingHub: ScreenHabitHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uiBindingHub = ScreenHabitHubBinding.inflate(layoutInflater)
        setContentView(uiBindingHub.root)

        uiBindingHub.btnAddNewHabitCore.setOnClickListener {
            val editorIntent = Intent(this, EditHabitActivity::class.java)
            startActivity(editorIntent)
        }
    }
}
