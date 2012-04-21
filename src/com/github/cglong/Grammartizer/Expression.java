package com.github.cglong.Grammartizer;

import java.util.List;
import java.util.ArrayList;

public class Expression {
	private List<Symbol> rightSymbols;
	private Nonterminal leftSymbol;
	
	public Expression(Nonterminal leftSymbol) {
		this.rightSymbols = new ArrayList<Symbol>();
		this.leftSymbol = leftSymbol;
	}
	
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
}
