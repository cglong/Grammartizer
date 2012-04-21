package com.github.cglong.Grammartizer;

public abstract class Symbol {
	private String name;
	
	public Symbol(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		Symbol other = (Symbol) o;
		return this.name.equals(other.getName());
	}
	
	abstract public boolean isTerminal();
}
