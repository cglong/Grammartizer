package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Grammar {
	private Map<Nonterminal, Rule> rules;
	private ArrayList<Nonterminal> nonterminals;
	private ArrayList<Terminal> terminals;
	private Nonterminal startvariable;
	
	public Grammar() {
		this.rules = new HashMap<Nonterminal, Rule>();
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
		for (Rule r : rules.values()) {
			System.out.print(r.getSymbol().getName() + " : ");
			for (Expression e : r.getExpressions())
				for (Symbol s : e.getRightSymbols())
					System.out.print(s.getName() + " ");
			System.out.println();
		}
	}
	
	public void add(Nonterminal symbol, Expression expression) {
		if (!this.rules.containsKey(symbol))
			this.rules.put(symbol, new Rule(symbol));
		Rule rule = this.rules.get(symbol);
		rule.add(expression);
	}
	
	public Collection<Rule> getRules() {
		return this.rules.values();
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
