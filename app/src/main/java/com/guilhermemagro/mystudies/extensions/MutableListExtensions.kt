package com.guilhermemagro.mystudies.extensions

fun <T> MutableList<T>.addOrRemoveIfExist(item: T) {
    if (contains(item)) {
        remove(item)
    } else {
        add(item)
    }
}
