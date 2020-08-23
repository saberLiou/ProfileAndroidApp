package saberliou.demo.profile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ibtnDeveloperEditName.setOnClickListener {
            launchMaterialDialog(
                R.string.mainActivity_mdEditDeveloperNameTitle_text,
                tvDeveloperName
            )
        }

        ibtnDeveloperEditMotto.setOnClickListener {
            launchMaterialDialog(
                R.string.mainActivity_mdEditDeveloperMottoTitle_text,
                tvDeveloperMotto
            )
        }
    }

    private fun launchMaterialDialog(titleTextResourceId: Int, updatedTextView: TextView) {
        MaterialDialog(this).show {
            input(waitForPositiveButton = true, allowEmpty = false) { _, value ->
                updatedTextView.text = value
            }
            title(titleTextResourceId)
            positiveButton(R.string.mainActivity_mdEditDeveloperButton_text)
        }
    }
}
