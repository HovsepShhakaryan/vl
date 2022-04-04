package com.vylo.auth.signinfragment.presentation.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vylo.auth.R
import com.vylo.auth.databinding.FragmentSigninBinding
import com.vylo.auth.signinfragment.presentation.viewmodel.SignInFragmentViewModel
import com.vylo.common.BaseFragment
import com.vylo.common.util.ThrowStartScreen
import com.vylo.common.util.enums.ScreenType
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSigninBinding>() {

    private val viewModel by viewModel<SignInFragmentViewModel>()

    override fun getViewBinding() = FragmentSigninBinding.inflate(layoutInflater)

    companion object {

        @JvmStatic
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinder = getViewBinding()
        return viewBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beginning()
    }

    private fun beginning() {
        createContentOfView()
    }

    private fun createContentOfView() {

        viewBinder.inputEmailAddress.setContainerColor(ContextCompat.getColor(requireContext(), R.color.primary))
        viewBinder.inputEmailAddress.setInputTitle(resources.getString(R.string.label_email_address))
        viewBinder.inputEmailAddress.setInputTitleColor(ContextCompat.getColor(requireContext(), R.color.primary))
        viewBinder.inputEmailAddress.setInputTypeHint(resources.getString(R.string.label_email_hint))
        viewBinder.inputEmailAddress.setInputTypeHintColor(ContextCompat.getColor(requireContext(), R.color.black_hint))
        viewBinder.inputEmailAddress.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

        viewBinder.inputPassword.setContainerColor(ContextCompat.getColor(requireContext(), R.color.primary))
        viewBinder.inputPassword.setInputTitle(resources.getString(R.string.label_password))
        viewBinder.inputPassword.setInputTitleColor(ContextCompat.getColor(requireContext(), R.color.primary))
        viewBinder.inputPassword.setInputTypeHint(resources.getString(R.string.label_password))
        viewBinder.inputPassword.setInputTypeHintColor(ContextCompat.getColor(requireContext(), R.color.black_hint))
        viewBinder.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

        viewBinder.buttonLogin.setTitle(resources.getString(R.string.label_log_in_button))
        viewBinder.buttonLogin.setButtonColor(ContextCompat.getColor(requireContext(), R.color.primary))
        viewBinder.buttonLogin.setTitleColor(ContextCompat.getColor(requireContext(), R.color.black))
        val buttonLogin = View.OnClickListener { checkFieldsValidation() }
        viewBinder.buttonLogin.clickOnButton(buttonLogin)

        viewBinder.linkButtonForgotPassword.setOnClickListener{
            //TODO
        }

        val signUpLabel = SpannableString(resources.getString(R.string.label_new_to_column))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //TODO
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        val color = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary))
        signUpLabel.setSpan(clickableSpan, 15, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        signUpLabel.setSpan(color, 15, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        viewBinder.linkButtonSignUp.movementMethod = LinkMovementMethod.getInstance()
        viewBinder.linkButtonSignUp.highlightColor = Color.TRANSPARENT
        viewBinder.linkButtonSignUp.text = signUpLabel

        viewModel.responseError.observe(viewLifecycleOwner) { showMessage(it) }
        viewModel.responseSuccess.observe(viewLifecycleOwner) {
            setScreenType(ScreenType.MAIN)
            showMessage(it)
            throwStartScreen()
        }
    }

    private fun checkFieldsValidation() {
        var isValidationPassed = true
        val emailAddressValidationResponse = viewModel.checkEmailAddressIsValid(viewBinder.inputEmailAddress.getInputText())
        val emailAddressEmptyResponse = viewModel.checkEmailAddressIsEmpty(viewBinder.inputEmailAddress.getInputText())
        val passwordValidationResponse = viewModel.checkPasswordIsValid(viewBinder.inputPassword.getInputText())
        val passwordEmptyResponse = viewModel.checkPasswordIsEmpty(viewBinder.inputPassword.getInputText())

        if (emailAddressEmptyResponse.isNotBlank()) {
            viewBinder.inputEmailAddress.setErrorTitle(emailAddressEmptyResponse)
            viewBinder.inputEmailAddress.setErrorTitleColor(ContextCompat.getColor(requireContext(), R.color.red))
            isValidationPassed = false
        } else if (emailAddressValidationResponse.isNotBlank()) {
            viewBinder.inputEmailAddress.setErrorTitle(emailAddressValidationResponse)
            viewBinder.inputEmailAddress.setErrorTitleColor(ContextCompat.getColor(requireContext(), R.color.red))
            isValidationPassed = false
        }
        if (passwordEmptyResponse.isNotBlank()) {
            viewBinder.inputPassword.setErrorTitle(passwordEmptyResponse)
            viewBinder.inputPassword.setErrorTitleColor(ContextCompat.getColor(requireContext(), R.color.red))
            isValidationPassed = false
        } else if (passwordValidationResponse.isNotBlank()) {
            viewBinder.inputPassword.setErrorTitle(passwordValidationResponse)
            viewBinder.inputPassword.setErrorTitleColor(ContextCompat.getColor(requireContext(), R.color.red))
            isValidationPassed = false
        }

        if (isValidationPassed)
            viewModel.signInCall()
    }

}