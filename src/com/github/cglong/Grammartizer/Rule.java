package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Rule {
	private Expression exp;
	private Nonterminal symbol;
	
	public Rule(Nonterminal symbol, Expression exp) {
		this.exp = exp;
		this.symbol = symbol;
	}
	
	public void add(Symbol s) {
		this.exp.add(s);
	}
	
	public Expression getExpression()
	{
		return exp;
	}//end getter
	
	public Nonterminal getSymbol()
	{
		return symbol;
	}//end getter
	
//	public boolean updateFirstSets() {
//		boolean changes = false;
//		for (Expression expression : this.expressions)
//			changes = this.symbol.updateFirstSet(expression) || changes;
//		return changes;
//	}
//	
//	public boolean updateFollowSets() {
//		boolean changes = false;
//		for (Expression expression : this.expressions)
//			for (Symbol symbol : expression.getRightSymbols())
//				if (!symbol.isTerminal())
//					changes = symbol.updateFollowSet(expression) || changes;
//		return changes;
//	}
}
