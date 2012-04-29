package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class RuleSet {
	private Collection<Rule> rules;
	private Nonterminal symbol;
	
	public RuleSet(Nonterminal symbol) {
		this.rules = new ArrayList<Rule>();
		this.symbol = symbol;
	}
	
	public void add(Rule rule) {
		this.rules.add(rule);
	}
	
	public Nonterminal getSymbol()
	{
		return this.symbol;
	}
	
	public Collection<Rule> getRules() {
		return this.rules;
	}
	
	public boolean updateFirstSets() {
		boolean changes = false;
		for (Rule rule : this.rules)
			changes = this.symbol.updateFirstSet(rule) || changes;
		return changes;
	}
	
	public boolean updateFollowSets() {
		boolean changes = false;
		for (Rule rule : this.rules)
			for (Symbol symbol : rule.getRightSymbols())
				if (!symbol.isTerminal())
					changes = symbol.updateFollowSet(rule) || changes;
		return changes;
	}
	
	@Override
	public String toString() {
		String str = "First(" + this.symbol + "): " + this.symbol.getFirstSet();
		str += "\nFollow(" + this.symbol + "): " + this.symbol.getFollowSet();
		return str;
	}
}
