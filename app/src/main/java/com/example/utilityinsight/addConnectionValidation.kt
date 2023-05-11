package com.example.utilityinsight

class addConnectionValidation {

    fun addConnectionValidateFields(testName: String, testAccountNumber:String, testPremisesID:String ):Boolean{

        if(testName.isEmpty()) {
            return false
        }

        if(testAccountNumber.isEmpty()) {
            return false
        }

        if(testPremisesID.isEmpty()) {
            return false
        }
        return true

    }
}