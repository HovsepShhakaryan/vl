package com.vylo.common.validation

import android.content.Context
import com.vylo.common.R
import java.util.regex.Pattern

class Validation(private val context: Context) {

    fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }


    fun isValidEmailWithText(email: String): String {
        if (!isValidEmailId(email))
            return context.resources.getString(R.string.label_invalid_field)
        return ""
    }


    fun isInputEmpty(text: String): String {
        val input = text.trim()
        if (input.isBlank())
            return context.resources.getString(R.string.label_file_field)
        return ""
    }


    fun isValidPassword(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
//                    "(?=.*[0-9])" +         //at least 1 digit
//                    "(?=.*[a-z])" +         //at least 1 lower case letter
//                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()
    }


    fun isValidPasswordWithText(password: String): String {
        if (!isValidPassword(password))
            return context.resources.getString(R.string.label_invalid_field)
        return ""
    }


    fun isFieldsMatched(valueOne: String, valueTwo: String): String {
        if (valueOne != valueTwo)
            return context.resources.getString(R.string.label_pass_not_matched)
        return ""
    }
}