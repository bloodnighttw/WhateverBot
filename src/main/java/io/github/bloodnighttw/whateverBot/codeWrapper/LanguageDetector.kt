package io.github.bloodnighttw.whateverBot.codeWrapper

private const val JAVA = "public static void main(String[]"
private const val CPP1 = "#incluide <iostream>"
private const val CPP2 = "#include <bits/stdc++.h>"
private const val C = "#include <stdio.h>"

fun detectCode(code: String): Language {
        if (JAVA in code) return Language.Java
        if (CPP1 in code || CPP2 in code) return Language.CPP
        return if (C in code) Language.C
        else Language.Other
}


