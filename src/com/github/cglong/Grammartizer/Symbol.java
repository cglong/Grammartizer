package com.github.cglong.Grammartizer;

import java.util.Set;
import java.util.HashSet;

public abstract class Symbol {
	private String name;
	private Set<Symbol> firstSet;
	private Set<Symbol> followSet;
	
	public Symbol(String name) {
		this.name = name;
		this.firstSet = new HashSet<Symbol>();
		this.followSet = new HashSet<Symbol>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Set<Symbol> getFirstSet() {
		return this.firstSet;
	}
	
	public Set<Symbol> getFollowSet() {
		return this.followSet;
	}
	
	@Override
	public boolean equals(Object o) {
		Symbol other = (Symbol) o;
		return this.name.equals(other.getName());
	}
	
	abstract public boolean isTerminal();
	abstract public boolean updateFirstSet(Expression expression);
	abstract public boolean updateFollowSet(Expression expression);
}
