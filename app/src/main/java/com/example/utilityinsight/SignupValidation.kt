package com.example.utilityinsight

class SignupValidation {

    fun validateData(Name: String, userName:String, Email:String, Password:String):Boolean{

        if(Name.isEmpty()){
            return false
        }
        if(userName.isEmpty()){
            return false
        }

        if(Email.isEmpty()){
            return false
        }
        if(Password.isEmpty()){
            return false
        }

        return true
    }

}