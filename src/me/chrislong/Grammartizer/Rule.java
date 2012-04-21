package me.chrislong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;

public class Rule {
	private Collection<Expression> expressions;
	
	public Rule() {
		this.expressions = new ArrayList<Expression>();
	}
}
