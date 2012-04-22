package com.github.cglong.Grammartizer;

import java.util.Map;
import java.util.HashMap;

public class Grammar {
	private Map<Nonterminal, Rule> rules;
	
	public Grammar() {
		this.rules = new HashMap<Nonterminal, Rule>();
	}
	
	public void add(Nonterminal symbol, Rule rule) {
		this.rules.put(symbol, rule);
	}
}
