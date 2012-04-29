package com.github.cglong.Grammartizer;

public class Driver {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Not enough arguments!");
			return;
		}
		
		GrammarReader grammarReader = new GrammarReader(args[0]);
		Grammar grammar = grammarReader.readGrammar();
		
		grammar.updateFirstSets();
		grammar.updateFollowSets();
		
		grammar.debugDump();
	}

}
