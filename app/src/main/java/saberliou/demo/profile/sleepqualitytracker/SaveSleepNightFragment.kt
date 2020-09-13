package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import saberliou.demo.profile.R

/**
 * A simple [Fragment] subclass.
 * Use the [SaveSleepNightFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaveSleepNightFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_save_sleep_night, container, false)
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
