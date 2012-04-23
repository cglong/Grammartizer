package com.github.cglong.Grammartizer;

import java.util.ArrayList;

public class TableBuilder {

	private Rule[][] parsingtable;
	
	public TableBuilder(Grammar g)
	{
		ArrayList<Symbol> allsymbols = g.getAllSymbols();
		ArrayList<Rule> rules = g.getRules();
		parsingtable = new Rule[allsymbols.size()][allsymbols.size()];
		
		for (Symbol t : allsymbols)
			t.index = allsymbols.indexOf(t);
		
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
