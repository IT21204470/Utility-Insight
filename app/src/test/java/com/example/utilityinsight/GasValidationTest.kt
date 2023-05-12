package com.example.utilityinsight

import org.junit.Assert.*

import org.junit.Test

class GasValidationTest {

    @Test
    fun `empty gasAccNo return false`(){

        val ValidateAccNo = GasValidation()

        val accNoResult = ValidateAccNo.validateGasInfo(

            "",
        "12-03-2023",
        "23",
        "12"

        )

        assertEquals(false, accNoResult)
    }

    @Test
    fun `empty gasDate return fasle`(){

        val ValidateDate = GasValidation()


        val gasDate = ValidateDate.validateGasInfo(

            "234565432",
            "",
            "34",
            "23"
        )
        assertEquals(false, gasDate)
    }


    @Test
    fun `empty gasUnits return fasle`(){

        val ValidateUnits = GasValidation()


        val gasUnits = ValidateUnits.validateGasInfo(

            "234565432",
            "12-03-2023",
            "",
            "23"
        )
        assertEquals(false, gasUnits)
    }


    @Test
    fun `empty gasNoDays return fasle`(){

        val ValidateDays = GasValidation()


        val gasDays = ValidateDays.validateGasInfo(

            "234565432",
            "12-03-2023",
            "23",
            "11"
        )
        assertEquals(false, gasDays)
    }

}