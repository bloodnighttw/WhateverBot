package io.github.bloodnighttw.whateverBot.codeWrapper;

import java.util.Arrays;

public class LanguageDetector {

	private final static String JAVA = "public static void main(String[]";
	private final static String[] CPP = {"#incluide <iostream>", "#include <bits/stdc++.h>"};
	private final static String C = "#include <stdio.h>";

	public static Language detectCode(String code) {
		if (code.contains(JAVA))
			return Language.Java;
		if (Arrays.stream(CPP).anyMatch(code::contains))
			return Language.CPP;
		if (code.contains(C))
			return Language.C;
		return Language.Other;
	}

}
