package com.example.utilityinsight

class GasValidation {

    fun validateGasInfo(accNo:String, gasDate:String, gasUnits:String, gasDays:String):Boolean{

        if(accNo.isEmpty()){
            return false
        }
        if(gasDate.isEmpty()){
            return false
        }
        if(gasUnits.isEmpty()){
            return false
        }
        if(gasDays.isEmpty()){
            return false
        }
        return true
    }

}