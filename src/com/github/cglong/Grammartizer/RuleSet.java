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
		for (Rule expression : this.rules)
			for (Symbol symbol : expression.getRightSymbols())
				if (!symbol.isTerminal())
					changes = symbol.updateFollowSet(expression) || changes;
		return changes;
	}
}
