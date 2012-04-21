package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Rule {
	private Collection<Expression> expressions;
	private Symbol symbol;
	
	public Rule(Symbol symbol) {
		this.expressions = new ArrayList<Expression>();
		this.symbol = symbol;
	}
}
