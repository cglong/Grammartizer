package com.github.cglong.Grammartizer;

import java.util.Collection;

public class ParseTable {
	private TwoKeyHashMap<Nonterminal, Terminal, Rule> table;

	public ParseTable(Grammar grammar) {
		Collection<RuleSet> ruleSets = grammar.getRuleSets();
		
		Terminal emptyString = new Terminal("");
		for (RuleSet ruleSet : ruleSets) {
			Nonterminal A = ruleSet.getSymbol();
			Collection<Rule> rules = ruleSet.getRules();
			
			for (Rule rule : rules) {
				boolean hasEmpty = false;
				for (Symbol alpha : rule.getRightSymbols()) {
					for (Terminal first : alpha.getFirstSet()) {
						this.table.put(A, first, rule);
						if (first.equals(emptyString))
							hasEmpty = true;
					}
					if (!hasEmpty)
						break;
				}
				
				if (hasEmpty)
					for (Terminal follow : A.getFollowSet())
						this.table.put(A, follow, rule);
			}
		}
	}
	
	public Rule get(Nonterminal A, Terminal a) {
		return this.table.get(A, a);
	}
}
