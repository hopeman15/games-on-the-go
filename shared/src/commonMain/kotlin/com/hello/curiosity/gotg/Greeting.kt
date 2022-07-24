package com.hello.curiosity.gotg

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}