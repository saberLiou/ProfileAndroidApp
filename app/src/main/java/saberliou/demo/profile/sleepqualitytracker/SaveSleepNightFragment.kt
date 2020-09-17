package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import saberliou.demo.profile.AppDatabase
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentSaveSleepNightBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SaveSleepNightFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaveSleepNightFragment : Fragment() {
    private lateinit var binding: FragmentSaveSleepNightBinding
    private lateinit var viewModel: SaveSleepNightViewModel

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_save_sleep_night, container, false)

        val nightId = SaveSleepNightFragmentArgs.fromBundle(requireArguments()).nightId
        viewModel = ViewModelProvider(
            this,
            SaveSleepNightViewModelFactory(nightId, AppDatabase.getInstance(requireActivity().application).sleepNightDao)
        ).get(SaveSleepNightViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.navigateToSleepNights.observe(viewLifecycleOwner, { toNavigate ->
            when (toNavigate) {
                true -> {
                    findNavController().navigate(SaveSleepNightFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())

                    viewModel.onSleepNightsNavigationDone()
                }
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SaveSleepNightFragment.
         */
        @JvmStatic
        fun newInstance() =
            SaveSleepNightFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
