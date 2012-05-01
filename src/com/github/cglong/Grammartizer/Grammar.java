package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Grammar {
	private Map<Nonterminal, RuleSet> ruleSets;
	private ArrayList<Nonterminal> nonterminals;
	private ArrayList<Terminal> terminals;
	private Nonterminal startvariable;
	private Terminal epsilon;
	
	public Grammar() {
		this.ruleSets = new ConcurrentHashMap<Nonterminal, RuleSet>();
		this.terminals = new ArrayList<Terminal>();
		this.nonterminals = new ArrayList<Nonterminal>();
		this.epsilon = new Terminal("");
		this.terminals.add(epsilon);
	}
	
	public void debugDump() {
		System.out.println("Terminals: ");
		for (Terminal t : terminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Nonterminals : ");
		for (Nonterminal t: nonterminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Rules: ");
		for (RuleSet r : ruleSets.values()) {
			System.out.print(r.getSymbol().getName() + " : ");
			for (Rule e : r.getRules())
				for (Symbol s : e.getRightSymbols())
					System.out.print(s.getName() + " ");
			System.out.println();
		}
	}
	
	public void add(Nonterminal symbol, Rule rule) {
		if (!this.ruleSets.containsKey(symbol))
			this.ruleSets.put(symbol, new RuleSet(symbol));
		RuleSet ruleSet = this.ruleSets.get(symbol);
		ruleSet.add(rule);
	}
	
	public void updateFirstSets() {
		boolean changes;
		do {
			changes = false;
			for (RuleSet ruleSet : this.ruleSets.values())
				changes = ruleSet.updateFirstSets() || changes;
		} while (changes);
	}
	
	public void updateFollowSets() {
		boolean changes;
		do {
			changes = false;
			for (RuleSet ruleSet : this.ruleSets.values())
				changes = ruleSet.updateFollowSets() || changes;
		} while (changes);
	}
	
	/* Eliminates all left recursion on this grammar.  Should be called once, after all rules have
	 * been added to this grammar.
	 * POSTCONDITION: The grammar will recognize the same language and create the same syntax trees, but
	 * will not be immediately left-recursive.
	 */
	public void eliminateLeftRecursion() {
		for (RuleSet ruleSet : this.ruleSets.values())
			if (ruleSet.hasLeftRecursion())
				eliminateLeftRecursionHelper(ruleSet);
	}

	/* Helper function.  Eliminates all immediate left recursion in a single recursive RuleSet.
	 * recursiveSet - A RuleSet that contains at least one rule with immediate left recursion.
	 * PRECONDITION: recursiveSet.hasLeftRecursion() returns true
	 * POSTCONDITION: recursiveSet no longer has immediate left recursion, but recognizes the same language as before.
	 */
	private void eliminateLeftRecursionHelper(RuleSet recursiveSet) {
		// Split the RuleSet into two lists - Rules with immediate left recursion and rules without.
		ArrayList<Rule> recursiveRules = new ArrayList<Rule>();
		ArrayList<Rule> nonRecursiveRules = new ArrayList<Rule>();

		for (Rule rule : recursiveSet.getRules())
			if (rule.hasLeftRecursion())
				recursiveRules.add(rule);
			else
				nonRecursiveRules.add(rule);

		// The nonterminal we are removing left recursion from
		Nonterminal oldSymbol = recursiveSet.getSymbol();
		// The new 'tail' nonterminal being added to the grammar
		Nonterminal tail = new Nonterminal(oldSymbol.getName() + "-tail"); // Since all grammar rules are named with <>s, <symbol>-tail is guaranteed to be unique.
		// The new RuleSet based off of oldSymbol that will reference tail and no longer contain immediate left recursion.  This will replace oldBase.
		RuleSet newBase = new RuleSet(oldSymbol);
		// The new 'tail' RuleSet
		RuleSet tailSet = new RuleSet(tail);

		// For each non recursive rule B, add a rule B<a-tail> to newBase
		for (Rule rule : nonRecursiveRules) {
			ArrayList<Symbol> newSymbolList = new ArrayList<Symbol>(rule.getRightSymbols());
			newSymbolList.add(tail);
			newBase.add(new Rule(oldSymbol, newSymbolList));
		}

		// For each recursive rule A, add rule A<a-tail> to tailSet
		for (Rule rule : recursiveRules) {
			ArrayList<Symbol> newSymbolList = new ArrayList<Symbol>(rule.getRightSymbols());
			newSymbolList.remove(0); // Remove the immediate left recursive symbol
			newSymbolList.add(tail); // Add the new tail symbol to the end of the set.
			tailSet.add(new Rule(tail, newSymbolList));
		}

		// Insert a rule into tailSet for epsilon
		Terminal epsilon = new Terminal("");
		ArrayList<Symbol> EpsilonList = new ArrayList<Symbol>();
		EpsilonList.add(epsilon);
		tailSet.add(new Rule(tail, EpsilonList));

		// Remove the old Nonterminal RuleSet from this Grammar and add in newBase and tailSet
		this.ruleSets.remove(oldSymbol);
		this.ruleSets.put(oldSymbol, newBase);
		this.ruleSets.put(tail, tailSet);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (RuleSet ruleSet : this.ruleSets.values())
			str += ruleSet.toString() + "\n";
		return str;
	}
	
	public Collection<RuleSet> getRuleSets() {
		return this.ruleSets.values();
	}
	
	public void setStartvariable(Nonterminal s) {
		this.startvariable = s;
		this.startvariable.getFollowSet().add(new Terminal("$"));
	}//end setter

	public Nonterminal getStartvariable() {
		return startvariable;
	}//end getter
	
	public Terminal getEpsilon(){
		return epsilon;
	}
	
	public ArrayList<Terminal> getTerminals(){
		return terminals;
	}//end getter
	
	public ArrayList<Nonterminal> getNonterminals(){
		return nonterminals;
	}//end getter
	
	public void setTerminals(ArrayList<Terminal> t)
	{
		this.terminals = t;
	}//end setter
	
	public void setNonterminals(ArrayList<Nonterminal> n)
	{
		this.nonterminals = n;
	}// end setter
	
}
