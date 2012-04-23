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
	
	public boolean updateFirstSets() {
		boolean changes = false;
		for (Expression expression : this.expressions)
			changes = this.symbol.updateFirstSet(expression) || changes;
		return changes;
	}
	
	public boolean updateFollowSets() {
		boolean changes = false;
		for (Expression expression : this.expressions)
			for (Symbol symbol : expression.getRightSymbols())
				if (!symbol.isTerminal())
					changes = symbol.updateFollowSet(expression) || changes;
		return changes;
	}
}
