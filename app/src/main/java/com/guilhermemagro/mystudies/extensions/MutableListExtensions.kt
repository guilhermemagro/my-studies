package com.guilhermemagro.mystudies.extensions

fun MutableList<String>.addOrRemoveIfExist(string: String) {
    if (contains(string)) {
        removeAll { element -> element.startsWith(string) }
    } else {
        add(string)
    }
}
