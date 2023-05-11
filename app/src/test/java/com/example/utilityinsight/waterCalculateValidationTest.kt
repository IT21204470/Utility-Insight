package com.example.utilityinsight

import org.junit.Assert.*

import org.junit.Test

class waterCalculateValidationTest {

    @Test
    fun `empty Account Name return false`() {

        val waterCalculateValidation = waterCalculateValidation()

        val result = waterCalculateValidation.waterCalculateValidationFields(
            "",
            "123/123/12/15/123",
            "25",
            "80"
        )
        assertEquals(false,result)
    }

    @Test
    fun `empty Account Number return false`() {

        val waterCalculateValidation = waterCalculateValidation()

        val result = waterCalculateValidation.waterCalculateValidationFields(
            "Amadee Nisalasara",
            "",
            "25",
            "80"
        )
        assertEquals(false,result)
    }

    @Test
    fun `empty Number Of Units return false`() {

        val waterCalculateValidation = waterCalculateValidation()

        val result = waterCalculateValidation.waterCalculateValidationFields(
            "Amadee Nisalasara",
            "152/147/12/25/258",
            "25",
            ""
        )
        assertEquals(false,result)
    }
}