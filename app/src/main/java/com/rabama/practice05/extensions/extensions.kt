package com.rabama.practice05.extensions

fun Double.format(digits: Int) = "%.${digits}f".format(this)