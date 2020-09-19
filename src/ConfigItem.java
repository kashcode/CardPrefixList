package CardPrefixList;

public class ConfigItem implements Comparable<ConfigItem> {

	private int from;
	private int to;
	private String name;	

	public ConfigItem(int from, int to, String name) {
		this.from = from;
		this.to = to;
		this.name = name;
	}

	public int getFrom() {
		return this.from;
	}

	public int getTo() {
		return this.to;
	}

	public String getCardName() {
		return this.name;
	}

	@Override
	public int compareTo(ConfigItem other) {
		// sakârto pçc robeþu lieluma
		if((to - from) > (other.to - other.from)){
			return 1;
		}
		else if ((to - from) < (other.to - other.from)){
			return -1;
		}
		else {
			return 0;
		}
		
		// ðî kârtoðana îsti nederçs 
		/*
		if (from == other.from) {
			return new Integer(to).compareTo(other.to);
		}

		return new Integer(from).compareTo(other.from);
		*/
	}
}
