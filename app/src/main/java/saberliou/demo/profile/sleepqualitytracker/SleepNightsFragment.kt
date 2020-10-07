package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import saberliou.demo.profile.AnyViewModelFactory
import saberliou.demo.profile.AppDatabase
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentSleepNightsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SleepNightsFragment.newInstance] factory method to create an instance of this fragment.
 */
class SleepNightsFragment : Fragment() {
    companion object {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of fragment [SleepNightsFragment].
         */
        @JvmStatic
        fun newInstance() = SleepNightsFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    private lateinit var binding: FragmentSleepNightsBinding
    private lateinit var viewModel: SleepNightsViewModel

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_nights, container, false)

        val application = requireActivity().application
        viewModel = ViewModelProvider(
            this,
            AnyViewModelFactory {
                SleepNightsViewModel(
                    application,
                    AppDatabase.getInstance(application).sleepNightDao
                )
            }
        ).get(SleepNightsViewModel::class.java)
        binding.viewModel = viewModel

        val sleepNightAdapter = SleepNightAdapter(object : SleepNightAdapter.OnSleepNightClickListener {
            override fun onSleepNightClicked(night: SleepNight) {
                viewModel.onSleepNightClicked(night)
            }
        })
        binding.rvSleepNights.adapter = sleepNightAdapter

//        viewModel.nights.observe(viewLifecycleOwner, { nights ->
//            nights?.let {
////                sleepNightAdapter.nights = nights
//                sleepNightAdapter.submitListWithHeader(nights)
//            }
//        })

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToSaveSleepNight.observe(viewLifecycleOwner, { night ->
            night?.let {
                findNavController().navigate(SleepNightsFragmentDirections.actionSleepNightsFragmentToSaveSleepNightFragment((night.nightId)))

                viewModel.onSaveSleepNightNavigationDone()
            }
        })

        viewModel.navigateToSleepNightDetail.observe(viewLifecycleOwner, { night ->
            night?.let {
                findNavController().navigate(SleepNightsFragmentDirections.actionSleepNightsFragmentToSleepNightDetailFragment(night.nightId))

                viewModel.onSleepNightDetailNavigationDone()
            }
        })

        viewModel.showSnackbar.observe(viewLifecycleOwner, { toShow ->
            when (toShow) {
                true -> {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                    ).show()

                    viewModel.onSnackbarShown()
                }
            }
        })

        binding.rvSleepNights.layoutManager = GridLayoutManager(requireActivity(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when (position) {
                    0 -> 3
                    else -> 1
                }
            }
        }

        return binding.root
    }
}
