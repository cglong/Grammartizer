package com.github.cglong.Grammartizer;

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
}
