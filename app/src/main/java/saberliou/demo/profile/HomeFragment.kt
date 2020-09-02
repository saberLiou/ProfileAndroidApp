package saberliou.demo.profile

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import saberliou.demo.profile.databinding.FragmentHomeBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val developer = Developer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        binding.developer = developer
        binding.ibtnDeveloperEditName.setOnClickListener {
            launchMaterialDialog(R.string.homeFragment_mdEditDeveloperNameTitle_text)
        }
        binding.ibtnDeveloperEditMotto.setOnClickListener {
            launchMaterialDialog(R.string.homeFragment_mdEditDeveloperMottoTitle_text)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun launchMaterialDialog(titleTextResourceId: Int) {
        activity?.let {
            MaterialDialog(it).show {
                input(waitForPositiveButton = true, allowEmpty = false) { _, value ->
                    val valueStr = value.toString()
                    val message = when (titleTextResourceId) {
                        R.string.homeFragment_mdEditDeveloperNameTitle_text -> {
                            developer.name = valueStr
                            makeToastString(UpdateTypes.NAME.getType(), valueStr)
                        }
                        R.string.homeFragment_mdEditDeveloperMottoTitle_text -> {
                            developer.motto = valueStr
                            makeToastString(UpdateTypes.MOTTO.getType(), valueStr)
                        }
                        else -> ""
                    }
                    binding.invalidateAll() // Refresh the UI with the value in the updated binding object.
                    when (val activity = activity) {
                        is MainActivity -> activity.showToast(message)
                    }
                }
                title(titleTextResourceId)
                positiveButton(R.string.homeFragment_mdEditDeveloperButton_text)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }

        fun makeToastString(field: String, value: String) = "Your $field is $value now."

        enum class UpdateTypes {
            NAME, MOTTO;

            fun getType() = this.toString().toLowerCase(Locale.ROOT)
        }
    }
}