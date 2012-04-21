package com.github.cglong.Grammartizer;

public class Nonterminal extends Symbol {

	public Nonterminal(String name) {
		super(name);
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
}
