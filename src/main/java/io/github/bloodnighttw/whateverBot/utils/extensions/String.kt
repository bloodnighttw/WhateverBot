package io.github.bloodnighttw.whateverBot.utils.extensions

fun String.isUrl() = this.startsWith("https://") or this.startsWith("http://")