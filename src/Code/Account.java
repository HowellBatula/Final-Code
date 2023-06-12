package Code;

public class Account {
	private String username;
	private String password;
	private String name;
	private String number;
	
	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
	
	public Account(String username, String password, String name, String number) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.number = number;
	}
	
}
