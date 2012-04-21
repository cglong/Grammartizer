package com.github.cglong.Grammartizer;

public class Terminal extends Symbol {

	public Terminal(String name) {
		super(name);
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
}
