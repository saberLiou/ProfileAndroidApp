package saberliou.demo.profile.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import saberliou.demo.profile.R

fun TextInputLayout.setOnTextInputChangedListener(afterTextChanged: (changedText: String) -> Unit) {
    editText!!.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged(editable.toString())
        }
    })
}

fun TextInputLayout.setErrorIfEmpty() {
    error = if (editText!!.isTextFilled()) null else context.getString(
        R.string.error_editText_notFilled,
        hint
    )
}

fun EditText.isTextFilled() = text.isNotBlank()
