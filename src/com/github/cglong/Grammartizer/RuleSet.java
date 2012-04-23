package com.github.cglong.Grammartizer;

import java.util.ArrayList;

public class RuleSet {

	private Symbol symbol;
	private ArrayList<Rule> rules;
	
	public RuleSet(Symbol s, ArrayList<Rule> r)
	{
		this.symbol = s;
		this.rules = r;
	}//end constructor
	
}
