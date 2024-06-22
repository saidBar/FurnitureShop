package com.example.furnitureshop.com.example.furnitureshop.dialog

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.furnitureshop.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

@SuppressLint("InflateParams")
fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
) {

    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val etEmail = view.findViewById<EditText>(R.id.etResetPassword)
    val buttonSend = view.findViewById<Button>(R.id.buttonSendResetPassword)
    val buttonCancel = view.findViewById<Button>(R.id.buttonCancelResetPassword)

    buttonSend.setOnClickListener {
        val email = etEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}
