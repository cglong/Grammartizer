package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Rule {
	private Collection<Expression> expressions;
	private Nonterminal symbol;
	
	public Rule(Nonterminal symbol) {
		this.expressions = new ArrayList<Expression>();
		this.symbol = symbol;
	}
	
	public void add(Collection<Symbol> expression) {
		this.expressions.add(new Expression(this.symbol, expression));
	}
}
