package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class Expression {
	private List<Symbol> rightSymbols;
	private Nonterminal leftSymbol;
	
	public void add(Symbol symbol)
	{
		this.rightSymbols.add(symbol);
	}
	
	public Symbol getLeft(){
		return this.rightSymbols.get(0);
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
