package com.github.cglong.Grammartizer;

public abstract class Symbol {
	private String name;
	
	public Symbol(String name) {
		this.name = name;
	}
	
	abstract public boolean isTerminal();
}
