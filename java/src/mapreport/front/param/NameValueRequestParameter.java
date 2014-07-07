package mapreport.front.param;

public class NameValueRequestParameter extends RequestParameter {
	String name;
	String value;
	
	public NameValueRequestParameter(String name, String value) {
		this.value = value;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
