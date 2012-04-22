package com.github.cglong.Grammartizer;

import java.util.Set;
import java.util.HashSet;

public abstract class Symbol {
	private String name;
	private Set<Symbol> firstSet;
	
	public Symbol(String name) {
		this.name = name;
		this.firstSet = new HashSet<Symbol>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Set<Symbol> getFirstSet() {
		return this.firstSet;
	}
	
	@Override
	public boolean equals(Object o) {
		Symbol other = (Symbol) o;
		return this.name.equals(other.getName());
	}
	
	abstract public boolean isTerminal();
	abstract public boolean updateFirstSet(Expression expression);
}
