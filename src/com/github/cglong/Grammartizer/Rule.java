package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class Rule {
	private List<Symbol> rightSymbols;
	private Nonterminal leftSymbol;
	
	public void add(Symbol symbol)
	{
		this.rightSymbols.add(symbol);
	}
	
	public Symbol getLeft(){
		return this.rightSymbols.get(0);
	}
	
	public Nonterminal getLeftSide()
	{
		return leftSymbol;
	}
	
	public Rule(Nonterminal leftSymbol, Collection<Symbol> rightSymbols) {
		this.leftSymbol = leftSymbol;
		this.rightSymbols = new ArrayList<Symbol>(rightSymbols);
	}
	
	public List<Symbol> getRightSymbols() {
		return this.rightSymbols;
	}
	
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
	
	@Override
	public String toString() {
		String str = this.leftSymbol + " -> ";
		for (Symbol symbol : this.rightSymbols)
			str += symbol;
		return str;
	}
}
