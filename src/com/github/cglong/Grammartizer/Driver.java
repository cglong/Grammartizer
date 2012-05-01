package com.github.cglong.Grammartizer;

import java.util.ArrayList;

public class Driver {
	
	public static void main(String[] args) {
		testPrefix();
	}
	
	public static int testMain(String[] args) {
		if (args.length != 1) {
			System.out.println("Not enough arguments!");
			return -1;
		}
		
		GrammarReader grammarReader = new GrammarReader(args[0]);
		Grammar grammar = grammarReader.readGrammar();
		
		grammar.eliminateLeftRecursion();
		grammar.updateFirstSets();
		grammar.updateFollowSets();
		
		return 0;
	}
	
	public static void testPrefix() {
		// Testing common prefix removal
		// Input:  <A> : abb | abc | bd
		// Expected output:
		// <A> : a <A>a | bd
		// <A>a : b <A>ab
		// <A>ab : b | c
		Grammar g = new Grammar();
		
		Nonterminal A = new Nonterminal("<A>");
		Terminal a = new Terminal("a");
		Terminal b = new Terminal("b");
		Terminal c = new Terminal("c");
		Terminal d = new Terminal("d");
		
		
		ArrayList<Symbol> Rule1List = new ArrayList<Symbol>();
		Rule1List.add(a);
		Rule1List.add(b);
		Rule1List.add(b);
		
		ArrayList<Symbol> Rule2List = new ArrayList<Symbol>();
		Rule2List.add(a);
		Rule2List.add(b);
		Rule2List.add(c);
		
		ArrayList<Symbol> Rule3List = new ArrayList<Symbol>();
		Rule3List.add(b);
		Rule3List.add(d);
		
		Rule rule1 = new Rule(A, Rule1List);
		Rule rule2 = new Rule(A, Rule2List);
		Rule rule3 = new Rule(A, Rule3List);
		
		g.add(A, rule1);
		g.add(A, rule2);
		g.add(A, rule3);
		
		g.eliminateCommonPrefixes();
		
		for (RuleSet set : g.getRuleSets()) {
			for (Rule rule : set.getRules())
				System.out.println(rule);
		}	
	}
	
	public static void testRecursion() {
		// Testing recursion removal
		Grammar g = new Grammar();

		Nonterminal expList = new Nonterminal("<exp-list>");
		Nonterminal exp = new Nonterminal("<exp>");
		Terminal comma = new Terminal(",");

		ArrayList<Symbol> recursiveRule = new ArrayList<Symbol>();
		recursiveRule.add(expList);
		recursiveRule.add(comma);
		recursiveRule.add(exp);

		ArrayList<Symbol> terminator = new ArrayList<Symbol>();
		terminator.add(exp);

		// The rule <exp-list> -> <exp-list>,<exp>
		Rule rule1 = new Rule(expList, recursiveRule);
		// The rule <exp-list> -> <exp>
		Rule rule2 = new Rule(expList, terminator);

		g.add(expList, rule1);
		g.add(expList, rule2);

		g.eliminateLeftRecursion();
		
		
		// Expected results are:
		// <exp-list> -> <exp><exp-list>-tail
		// <exp-list>-tail -> ,<exp><exp-list>-tail
		// <exp-list>-tail -> *epsilon*
		for (RuleSet set : g.getRuleSets()) {
			for (Rule rule : set.getRules())
				System.out.println(rule);
		}
	}
}
