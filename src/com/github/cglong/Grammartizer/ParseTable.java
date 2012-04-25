package com.github.cglong.Grammartizer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;

public class ParseTable {

	private Rule[][] parsingtable;
	private ArrayList<Terminal> terminals;
	private ArrayList<Nonterminal> nonterminals;
	
	public ParseTable(Grammar g)
	{
		terminals = g.getTerminals();
		nonterminals = g.getNonterminals();
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
	
	public void toFile(String filename)
	{
		String s = "";
		for(Nonterminal n : nonterminals)
		{
			for(Terminal t : terminals)
			{
				Rule r = this.get(n, t);
				String s1 = "";
				for(Symbol sym : r.getRightSymbols())
					s1 = s1 + sym.getName();
				
				s = s + "[" + n.getName() + " , " + t.getName() + "] : " +
				r.getLeftSide().getName() + " --> " + s1 + "\n";
			}
		}
//		char[] buffer = new char[s.length()];
//		s.getChars(0, s.length(), buffer, 0);
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			System.out.println("Filewriter failed");
			System.exit(-1);
		}
	}//end to file
}
