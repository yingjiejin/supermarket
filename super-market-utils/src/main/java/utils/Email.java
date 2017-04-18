package utils;

public class Email
		implements Cloneable {
	private String account;
	private String host;

	public Email() {
	}

	public Email(String str) {
		try {
			int pos = str.indexOf(64);
			this.account = str.substring(0, pos);
			this.host = str.substring(pos + 1, str.length());
		} catch (Exception e) {

			throw new RuntimeException(String.format("Error email format [%s]", new Object[] { str }));
		}
		if ((Strings.isBlank(this.account)) || (Strings.isBlank(this.host)) || (this.host.indexOf(46) < 0))
			throw new RuntimeException(String.format("Error email format [%s]", new Object[] { str }));
	}

	public Email(String account, String host) {
		this.account = account;
		this.host = host;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int hashCode() {
		if (null == this.account)
			return 0;
		return this.account.hashCode();
	}

	@Override
	public Email clone() throws CloneNotSupportedException {
		return new Email(this.account, this.host);
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(Email.class.isAssignableFrom(obj.getClass())))
			return false;
		if (!(this.account.equals(((Email) obj).account))) {
			return false;
		}
		return (this.host.equals(((Email) obj).host));
	}

	@Override
	public String toString() {
		return String.format("%s@%s", new Object[] { this.account, this.host });
	}
}