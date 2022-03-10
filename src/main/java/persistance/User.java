package persistance;

import mediatek2022.Utilisateur;

public abstract class User implements Utilisateur {

	private static int cptUser = 0;

	private int numUser;
	private String name;
	private String login;
	private String password;

	public User(String name, String log, String pass) {
		this.name = name;
		login = log;
		password = pass;
		numUser = ++cptUser;

	}

	@Override
	public Object[] data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public abstract boolean isBibliothecaire();

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return "User [numUser=" + numUser + ", name=" + name + ", login=" + login + ", password=" + password + "]";
	}

}
