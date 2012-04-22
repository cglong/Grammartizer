package com.github.cglong.Grammartizer;

import java.util.List;

public class Nonterminal extends Symbol {

	public Nonterminal(String name) {
		super(name);
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
	
	@Override
	public boolean updateFirstSet(Expression expression) {
		Symbol first = expression.getRightSymbols().get(0);
		return this.getFirstSet().addAll(first.getFirstSet());
	}
	
	@Override
	public boolean updateFollowSet(Expression expression) {
		List<Symbol> symbols = expression.getRightSymbols();
		List<Symbol> followSymbols = symbols.subList(symbols.indexOf(this)+1, symbols.size());
		
		boolean changes = false;
		for (Symbol symbol : followSymbols)
			changes = this.getFollowSet().addAll(symbol.getFirstSet()) || changes;
		return changes;
	}
}
