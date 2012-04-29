package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class ParseTable extends HashMap<Nonterminal, Map<Terminal, Rule>> {

	private static final long serialVersionUID = -9213469451171219214L;

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
						this.put(A, first, rule);
						if (first.equals(emptyString))
							hasEmpty = true;
					}
					if (!hasEmpty)
						break;
				}
				
				if (hasEmpty)
					for (Terminal follow : A.getFollowSet())
						this.put(A, follow, rule);
			}
		}
	}
	
	public Rule get(Nonterminal A, Terminal a) {
		return this.get(A).get(a);
	}
	
	public Rule put(Nonterminal A, Terminal a, Rule rule) {
		if (!this.containsKey(A))
			this.put(A, new HashMap<Terminal, Rule>());
		return this.get(A).put(a, rule);
	}
}
