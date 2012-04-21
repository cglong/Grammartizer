package me.chrislong.Grammartizer;

public class Terminal extends Token {

	public Terminal(String name) {
		super(name);
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
}
