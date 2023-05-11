package com.example.utilityinsight

class waterCalculateValidation {

    fun waterCalculateValidationFields(textAccountName: String, textAccountNumber: String, textNumberOfDays: String, textNumberOfUnits: String ):Boolean{

        if (textAccountName.isEmpty()){
            return false
        }

        if (textAccountNumber.isEmpty()){
            return false
        }

        if (textNumberOfDays.isEmpty()){
            return false
        }

        if (textNumberOfUnits.isEmpty()){
            return false
        }
        return true
    }
}