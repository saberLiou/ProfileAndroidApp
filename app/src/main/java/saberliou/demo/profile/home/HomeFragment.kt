package saberliou.demo.profile.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import saberliou.demo.profile.MainActivity
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentHomeBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

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

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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

        // Showing the Share Menu Item Dynamically:
        // check if the activity resolves
        if (null == getSharingIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.sharing)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sharing -> startActivity(getSharingIntent())
            else -> return NavigationUI.onNavDestinationSelected(
                item,
                requireView().findNavController()
            )
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getSharingIntent(): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            setType("text/plain")
                .putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.homeFragment_optionsMenu_sharingSuccess_text)
                )
        }
    }

    private fun launchMaterialDialog(titleTextResourceId: Int) {
        activity?.let {
            MaterialDialog(it).show {
                input(waitForPositiveButton = true, allowEmpty = false) { _, value ->
                    val valueStr = value.toString()
                    val message = when (titleTextResourceId) {
                        R.string.homeFragment_mdEditDeveloperNameTitle_text -> {
                            viewModel.updateDeveloperName(valueStr)
                            makeToastString(UpdateTypes.NAME.getType(), valueStr)
                        }
                        R.string.homeFragment_mdEditDeveloperMottoTitle_text -> {
                            viewModel.updateDeveloperMotto(valueStr)
                            makeToastString(UpdateTypes.MOTTO.getType(), valueStr)
                        }
                        else -> ""
                    }
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