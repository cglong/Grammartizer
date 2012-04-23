package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class TableBuilder {

	private RuleSet[][] parsingtable;
	
	public TableBuilder(Grammar g)
	{
		ArrayList<Terminal> terminals = g.getTerminals();
		ArrayList<Nonterminal> nonterminals = g.getNonterminals();
		Collection<RuleSet> ruleSet = g.getRuleSets();
		parsingtable = new RuleSet[nonterminals.size()][terminals.size()];
		
		for (Terminal t : terminals)
			t.index = terminals.indexOf(t);
		for (Nonterminal n : nonterminals)
			n.index = nonterminals.indexOf(n);
		
		for(RuleSet r : ruleSet)
		{
			Nonterminal A = r.getSymbol();
			Collection<Rule> rules = r.getRules();
			boolean hasempty = false;
			for (Rule rule : rules)
				for(Symbol alpha : rule.getRightSymbols())
				{
					hasempty = false;
					for(Symbol first : alpha.getFirstSet())
					{
						parsingtable[A.index][first.index] = r;
						if (first.getName().equals(""))
							hasempty = true;
					}
					if(!hasempty)
						break;
				}
			
			if(hasempty)
			{
				for(Symbol follow : A.getFollowSet())
					parsingtable[A.index][follow.index] = r;
			}
		}
	}//end constructor
}//end class
