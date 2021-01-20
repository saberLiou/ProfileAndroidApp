package saberliou.demo.profile.contactme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentContactMeBinding

@AndroidEntryPoint
class ContactMeFragment : Fragment() {
    private lateinit var binding: FragmentContactMeBinding
    private val viewModel: ContactMeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_me, container, false)
        binding.viewModel = viewModel
//        binding.btnContactMeSubmit.setOnClickListener {
//            val email = "saberliou@gmail.com"
//            val intent = Intent(Intent.ACTION_SENDTO)
//            intent.data = Uri.parse("mailto:")
//            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello World")
//            intent.putExtra(Intent.EXTRA_TEXT, "test")
//            startActivity(intent)
//        }
        return binding.root
    }

//    private fun enterToHideKeyBoard(view: View, keyCode: Int): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_ENTER) {
//            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
//                .hideSoftInputFromWindow(view.windowToken, 0)
//            return true
//        }
//        return false
//    }
}
