package com.example.utilityinsight

import org.junit.Assert.*

import org.junit.Test

class SignupValidationTest {

    @Test
    fun `empty Name return False`(){
        val validateName = SignupValidation()

        val result = validateName.validateData(
            "",
        "tharindu888",
            "tharindusahan11@gmail.com",
            "maina"
        )
        assertEquals(false, result)
    }


    @Test
    fun `empty userName return False`(){
        val validateuserName = SignupValidation()

        val userNameResult = validateuserName.validateData(
            "Tharindu Sahan",
            "",
            "tharindusahan11@gmail.com",
            "maina"
        )
        assertEquals(false, userNameResult)
    }


    @Test
    fun `empty Email return False`(){
        val validateEmail = SignupValidation()

        val EmailResult = validateEmail.validateData(
            "Tharindu Sahan",
            "tharindu888",
            "",
            "maina"
        )
        assertEquals(false, EmailResult)
    }

    @Test
    fun `empty Password return False`(){
        val validatePassword = SignupValidation()

        val PasswordResult = validatePassword.validateData(
            "Tharindu Sahan",
            "tharindu888",
            "tharindusahan11@gmail.com",
            ""
        )
        assertEquals(false, PasswordResult)
    }

}