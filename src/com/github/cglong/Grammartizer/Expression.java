package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class Expression {
	private List<Symbol> rightSymbols;
	private Nonterminal leftSymbol;
	
	public Expression(Nonterminal leftSymbol, Collection<Symbol> rightSymbols) {
		this.leftSymbol = leftSymbol;
		this.rightSymbols = new ArrayList<Symbol>(rightSymbols);
	}
	
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
}
