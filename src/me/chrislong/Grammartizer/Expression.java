package me.chrislong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Expression {
	private Collection<Token> tokens;
	
	public Expression() {
		this.tokens = new ArrayList<Token>();
	}
}
