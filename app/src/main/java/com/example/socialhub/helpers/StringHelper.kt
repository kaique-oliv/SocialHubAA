package com.example.socialhub.helpers

fun unmask(s: String): String {
    return s.replace("-", "").replace("/","").replace(".", "").replace("(","").replace(")","")
}