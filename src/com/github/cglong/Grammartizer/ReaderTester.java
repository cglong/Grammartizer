package com.github.cglong.Grammartizer;

public class ReaderTester {


	public static void main(String[] args) {

		GrammarReader reader = new GrammarReader("tiny.txt");
		Grammar g = reader.readGrammar();
		g.debugDump();

	}

}
