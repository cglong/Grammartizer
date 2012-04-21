package com.github.cglong.Grammartizer;

public abstract class Token {
	private String name;
	
	public Token(String name) {
		this.name = name;
	}
	
	abstract public boolean isTerminal();
}
