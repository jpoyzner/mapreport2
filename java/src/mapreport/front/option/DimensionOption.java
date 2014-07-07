package mapreport.front.option;

public class DimensionOption extends Option {
	
	public enum DimensionType {
		LOC_DIMENSION,
		TOPIC_DIMENSION,
		TIME_DIMENSION
	}
	
	DimensionType type;
	
	public DimensionOption(String type) {
		super(type);
		switch (type) {
			case "loc": this.type = DimensionType.LOC_DIMENSION;	break;
			case "topic": this.type = DimensionType.TOPIC_DIMENSION;	break;
			case "time": this.type = DimensionType.TIME_DIMENSION;	break;
		}
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
	boolean isMore;
	boolean isLess;
}
