package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class Expression {
	private ArrayList<Symbol> exp;
	private List<Symbol> rightSymbols;
	private Symbol leftSymbol;
	
	public Expression(ArrayList<Symbol> s){
		this.exp = s;
	}
	
	public void add(Symbol s)
	{
		exp.add(s);
	}
	
	public ArrayList<Symbol> getSymbols(){
		return exp;
	}
	
	public Expression(Nonterminal leftSymbol, Collection<Symbol> rightSymbols) {
		this.leftSymbol = leftSymbol;
		this.rightSymbols = new ArrayList<Symbol>(rightSymbols);
	}
	
	public List<Symbol> getRightSymbols() {
		return this.rightSymbols;
	}
	
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
}
