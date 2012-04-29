package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Grammar {
	private Map<Nonterminal, RuleSet> ruleSets;
	private ArrayList<Nonterminal> nonterminals;
	private ArrayList<Terminal> terminals;
	private Nonterminal startvariable;
	
	public Grammar() {
		this.ruleSets = new HashMap<Nonterminal, RuleSet>();
		this.terminals = new ArrayList<Terminal>();
		this.nonterminals = new ArrayList<Nonterminal>();
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
	
	public Collection<RuleSet> getRuleSets() {
		return this.ruleSets.values();
	}
	
	public void setStartvariable(Nonterminal s) {
		this.startvariable = s;
	}//end setter

	public Nonterminal getStartvariable() {
		return startvariable;
	}//end getter
	
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
