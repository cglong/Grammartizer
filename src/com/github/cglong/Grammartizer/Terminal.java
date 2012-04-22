package com.github.cglong.Grammartizer;

public class Terminal extends Symbol {

	public Terminal(String name) {
		super(name);
		this.getFirstSet().add(this);
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
	
	@Override
	public boolean updateFirstSet(Expression expression) {
		return false;
	}
}
