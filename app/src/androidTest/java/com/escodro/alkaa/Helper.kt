package com.escodro.alkaa

class Helper {
    companion object {
        fun randomString(length: Int) : String {
            val charset = "abcdefghijklmnopqrstuvwxyz"
            return (1..length)
                .map { charset.random() }
                .joinToString("")
        }
    }

}