package com.github.cglong.Grammartizer;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Nonterminal extends Symbol {
	private Set<Terminal> followSet;
	
	public Nonterminal(String name) {
		super(name);
		this.followSet = new HashSet<Terminal>();
	}
	
	@Override
	public boolean isTerminal() {
		return false;
	}
	
	@Override
	public Set<Terminal> getFollowSet() throws UnsupportedOperationException {
		return this.followSet;
	}
	
	@Override
	public boolean updateFirstSet(Rule rule) {
		Symbol first = rule.getRightSymbols().get(0);
		return this.getFirstSet().addAll(first.getFirstSet());
	}
	
	@Override
	public boolean updateFollowSet(Rule rule) throws UnsupportedOperationException {
		List<Symbol> symbols = rule.getRightSymbols();
		List<Symbol> followSymbols = symbols.subList(symbols.indexOf(this)+1, symbols.size());
		
		boolean changes = false;
		for (Symbol symbol : followSymbols)
			changes = this.getFollowSet().addAll(symbol.getFirstSet()) || changes;
		
		boolean hadEmpty = this.getFollowSet().remove(new Nonterminal(""));
		if (hadEmpty)
			changes = this.getFollowSet().addAll(rule.getLeftSide().getFollowSet()) || changes;
		
		return changes;
	}
}
