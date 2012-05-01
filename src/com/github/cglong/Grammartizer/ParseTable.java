package com.github.cglong.Grammartizer;

import java.util.Collection;

public class ParseTable {
	private TwoKeyHashMap<Nonterminal, Terminal, Rule> table;

	public ParseTable(Grammar grammar) throws IllegalArgumentException {
		this.table = new TwoKeyHashMap<Nonterminal, Terminal, Rule>();
		Collection<RuleSet> ruleSets = grammar.getRuleSets();
		
		Terminal emptyString = new Terminal("");
		for (RuleSet ruleSet : ruleSets) {
			Nonterminal A = ruleSet.getSymbol();
			Collection<Rule> rules = ruleSet.getRules();
			
			for (Rule rule : rules) {
				boolean hasEmpty = false;
				for (Symbol alpha : rule.getRightSymbols()) {
					for (Terminal first : alpha.getFirstSet()) {
						if (this.table.put(A, first, rule) != null)
							throw new IllegalArgumentException("Rule already exists for [" + A + ", " + first + "]!");
						if (first.equals(emptyString))
							hasEmpty = true;
					}
					if (!hasEmpty)
						break;
				}
				
				if (hasEmpty)
					for (Terminal follow : A.getFollowSet())
						if (this.table.put(A, follow, rule) != null)
							throw new IllegalArgumentException("Rule already exists for [" + A + ", " + follow + "]!");
			}
		}
	}
	
	public Rule get(Nonterminal A, Terminal a) {
		return this.table.get(A, a);
	}
	
	@Override
	public String toString() {
		return this.table.toString();
	}
}
