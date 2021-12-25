package io.github.bloodnighttw.WhateverBot.CodeAutoResend;

public class LanguageDetector {

	private final static String JAVA = "public static void main(String[]";
	private final static String CPP = "#incluide <iostream>";
	private final static String C = "#include <stdio.h>";

	public static Language detectCode(String code) {
		if (code.contains(JAVA))
			return Language.Java;
		if (code.contains(CPP))
			return Language.CPP;
		if (code.contains(C))
			return Language.C;
		return Language.Other;
	}

}
