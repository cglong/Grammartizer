package com.github.cglong.Grammartizer;

import java.util.List;

public class Expression {
	private List<Symbol> rightSymbols;
	private Nonterminal leftSymbol;
	
	public Expression(Nonterminal leftSymbol, List<Symbol> rightSymbols) {
		this.leftSymbol = leftSymbol;
		this.rightSymbols = rightSymbols;
	}
	
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
}
