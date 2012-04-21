package com.github.cglong.Grammartizer;

public class Nonterminal extends Token {

	public Nonterminal(String name) {
		super(name);
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
}
