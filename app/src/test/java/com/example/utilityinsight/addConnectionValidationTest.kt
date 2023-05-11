package com.example.utilityinsight

import org.junit.Assert.*

import org.junit.Test

class addConnectionValidationTest {

    @Test
    fun `empty name return false`() {

        val addConnectionValidation = addConnectionValidation()

        val result = addConnectionValidation.addConnectionValidateFields(
            "",
            "200034901507",
            "G102"
        )
        assertEquals(false, result)
    }

    @Test
    fun `empty account number return false`() {

        val addConnectionValidation = addConnectionValidation()

        val result = addConnectionValidation.addConnectionValidateFields(
            "Hemsith Deesara",
            "",
            "G400"
        )
        assertEquals(false, result)
    }


    @Test
    fun `empty premises ID return false`() {

        val addConnectionValidation = addConnectionValidation()

        val result = addConnectionValidation.addConnectionValidateFields(
            "Maleesha Dilnuwan",
            "4407472502",
            ""
        )
        assertEquals(false, result)
    }


}