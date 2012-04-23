package com.github.cglong.Grammartizer;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Grammar {
	//private Map<Nonterminal, Expression> rules;
	private ArrayList<Rule> rules;
	private ArrayList<Nonterminal> nonterminals;
	private ArrayList<Terminal> terminals;
	private ArrayList<Symbol> allsymbols;
	private Nonterminal startvariable;
	
	public Grammar() {
		this.rules = new ArrayList<Rule>();
		this.terminals = new ArrayList<Terminal>();
		this.nonterminals = new ArrayList<Nonterminal>();
		this.allsymbols = new ArrayList<Symbol>();
		this.startvariable = new Nonterminal("placeholder");
	}//end constructor
	
	public void debugDump()
	{
		System.out.println("Terminals: ");
		for(Terminal t : terminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Nonterminals : ");
		for(Nonterminal t: nonterminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Rules: ");
		for(Rule r : rules)
		{
		System.out.print(r.getSymbol().getName() + " : ");
		for(Symbol s : r.getExpression().getSymbols())
			System.out.print(s.getName() + " ");
		System.out.println();
		}
	}//end debug
	
	
	public void addRule(Nonterminal symbol, Expression e) {
		this.rules.add(new Rule(symbol, e));
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
	
	public void setAllSymbols(ArrayList<Symbol> s)
	{
		this.allsymbols = s;
	}//end setter
	
	public ArrayList<Symbol> getAllSymbols()
	{
		return allsymbols;
	}// end getter
	
}//end class
