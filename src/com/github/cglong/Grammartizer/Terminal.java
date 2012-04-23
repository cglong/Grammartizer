package com.github.cglong.Grammartizer;

import java.util.Set;

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
	public Set<Symbol> getFollowSet() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean updateFirstSet(Rule rule) {
		return false;
	}
	
	@Override
	public boolean updateFollowSet(Rule rule) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
