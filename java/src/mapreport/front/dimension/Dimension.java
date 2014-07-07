package mapreport.front.dimension;

public class Dimension {
	String name;
	// ???  levelList
	int priority;
    String image;
	boolean isMore;
	boolean isLess;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isMore() {
		return isMore;
	}
	public void setMore(boolean isMore) {
		this.isMore = isMore;
	}
	public boolean isLess() {
		return isLess;
	}
	public void setLess(boolean isLess) {
		this.isLess = isLess;
	}
}
