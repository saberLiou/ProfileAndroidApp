package saberliou.demo.profile.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //    private val viewModel: HomeViewModel by viewModels {
//        AnyViewModelFactory {
//            HomeViewModel((requireContext().applicationContext as BaseApplication).githubRepository)
//        }
//    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getSharingIntent(): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            setType("text/plain")
                .putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.optionsMenu_sharingSuccess_text)
                )
        }
    }

//    private fun launchMaterialDialog(titleTextResourceId: Int) {
//        activity?.let {
//            MaterialDialog(it).show {
//                input(waitForPositiveButton = true, allowEmpty = false) { _, value ->
//                    val valueStr = value.toString()
//                    val message = when (titleTextResourceId) {
//                        R.string.mdEditDeveloperNameTitle_text -> {
//                            viewModel.updateDeveloperName(valueStr)
//                            makeToastString(UpdateTypes.NAME.getType(), valueStr)
//                        }
//                        R.string.mdEditDeveloperMottoTitle_text -> {
//                            viewModel.updateDeveloperMotto(valueStr)
//                            makeToastString(UpdateTypes.MOTTO.getType(), valueStr)
//                        }
//                        else -> ""
//                    }
//                    when (val activity = activity) {
//                        is MainActivity -> activity.showToast(message)
//                    }
//                }
//                title(titleTextResourceId)
//                positiveButton(R.string.mdEditDeveloperButton_text)
//            }
//        }
//    }
}
