package saberliou.demo.profile

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import saberliou.demo.profile.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val developer = Developer()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var toast: Toast? = null

    companion object {
        enum class UpdateTypes {
            NAME, MOTTO;

            fun getType() = this.toString().toLowerCase(Locale.ROOT)
        }

        fun makeToastString(field: String, value: String) = "Your $field is $value now."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.developer = developer

        binding.ibtnDeveloperEditName.setOnClickListener {
            launchMaterialDialog(R.string.mainActivity_mdEditDeveloperNameTitle_text)
        }

        binding.ibtnDeveloperEditMotto.setOnClickListener {
            launchMaterialDialog(R.string.mainActivity_mdEditDeveloperMottoTitle_text)
        }
    }

    private fun launchMaterialDialog(titleTextResourceId: Int) {
        MaterialDialog(this).show {
            input(waitForPositiveButton = true, allowEmpty = false) { _, value ->
                val valueStr = value.toString()
                val message = when (titleTextResourceId) {
                    R.string.mainActivity_mdEditDeveloperNameTitle_text -> {
                        developer.name = valueStr
                        makeToastString(UpdateTypes.NAME.getType(), valueStr)
                    }
                    R.string.mainActivity_mdEditDeveloperMottoTitle_text -> {
                        developer.motto = valueStr
                        makeToastString(UpdateTypes.MOTTO.getType(), valueStr)
                    }
                    else -> ""
                }
                binding.invalidateAll() // Refresh the UI with the value in the updated binding object.
                showToast(message)
            }
            title(titleTextResourceId)
            positiveButton(R.string.mainActivity_mdEditDeveloperButton_text)
        }
    }

    private fun showToast(message: String) {
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)?.also { it.show() }
    }
}
