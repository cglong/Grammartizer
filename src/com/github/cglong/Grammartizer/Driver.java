package com.github.cglong.Grammartizer;

public class Driver {
	
	public static void main(String[] args) {
		realMain(args);
	}
	
	public static int realMain(String[] args) {
		if (args.length != 2) {
			System.out.println("Not enough arguments!");
			return -1;
		}
		
		GrammarReader grammarReader = new GrammarReader(args[0]);
		Grammar grammar = grammarReader.readGrammar();
		
		grammar.eliminateLeftRecursion();
		grammar.eliminateCommonPrefixes();
		grammar.updateFirstSets();
		grammar.updateFollowSets();
		
		ParseTable parseTable;
		try {
			parseTable = new ParseTable(grammar);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return -1;
		}
		
		ProgramReader programReader = new ProgramReader(args[1], grammar.getTerminals());
		Parser.parse(programReader.getInputTokens(), grammar.getStartvariable(), new Terminal(""), parseTable);
		
		return 0;
	}
}
