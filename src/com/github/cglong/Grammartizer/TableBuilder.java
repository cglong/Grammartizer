package com.github.cglong.Grammartizer;

import java.util.ArrayList;

public class TableBuilder {

	private Rule[][] parsingtable;
	
	public TableBuilder(Grammar g)
	{
		ArrayList<Terminal> terminals = g.getTerminals();
		ArrayList<Nonterminal> nonterminals = g.getNonterminals();
		ArrayList<Rule> rules = g.getRules();
		parsingtable = new Rule[nonterminals.size()][terminals.size()];
		
		for (Terminal t : terminals)
			t.index = terminals.indexOf(t);
		for (Nonterminal n : nonterminals)
			n.index = nonterminals.indexOf(n);
		
		for(Rule r : rules)
		{
			Nonterminal A = r.getSymbol();
			Expression rightside = r.getExpression();
			Boolean hasempty = false;
			for(Symbol alpha : rightside.getSymbols())
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
