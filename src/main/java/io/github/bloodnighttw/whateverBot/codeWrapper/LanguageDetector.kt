package io.github.bloodnighttw.whateverBot.codeWrapper

import java.util.*

private const val JAVA = "public static void main(String[]"
private val CPP = arrayOf("#incluide <iostream>", "#include <bits/stdc++.h>")
private const val C = "#include <stdio.h>"

fun detectCode(code: String): Language {
    if (code.contains(JAVA)) return Language.Java
    if (Arrays.stream(CPP).anyMatch { s: String -> code.contains(s) }) return Language.CPP
    return if (code.contains(C)) Language.C
    else Language.Other
}
