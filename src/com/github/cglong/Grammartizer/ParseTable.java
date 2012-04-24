package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class ParseTable {

	private Rule[][] parsingtable;
	
	public ParseTable(Grammar g)
	{
		ArrayList<Terminal> terminals = g.getTerminals();
		ArrayList<Nonterminal> nonterminals = g.getNonterminals();
		Collection<RuleSet> ruleSet = g.getRuleSets();
		parsingtable = new Rule[nonterminals.size()][terminals.size()];
		
		for (Terminal t : terminals)
			t.setIndex(terminals.indexOf(t));
		for (Nonterminal n : nonterminals)
			n.setIndex(nonterminals.indexOf(n));
		
		for(RuleSet r : ruleSet)
		{
			Nonterminal A = r.getSymbol();
			Collection<Rule> rules = r.getRules();
			for (Rule rule : rules)
			{
				boolean hasempty = false;
				for(Symbol alpha : rule.getRightSymbols())
				{
					hasempty = false;
					for(Symbol first : alpha.getFirstSet())
					{
						parsingtable[A.getIndex()][first.getIndex()] = rule;
						if (first.getName().equals(""))
							hasempty = true;
					}
					if(!hasempty)
						break;
				}
			
				if(hasempty)
				{
					for(Symbol follow : A.getFollowSet())
						parsingtable[A.getIndex()][follow.getIndex()] = rule;
				}
			}
		}
	}
	
	public Rule get(Nonterminal A, Terminal a)
	{
		return parsingtable[A.getIndex()][a.getIndex()];
	}
}
