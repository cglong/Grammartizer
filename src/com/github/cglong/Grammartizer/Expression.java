package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Expression {
	private Collection<Symbol> tokens;
	
	public Expression() {
		this.tokens = new ArrayList<Symbol>();
	}
}
