
public class MTGCard {
	private String name;
	private int convertedManaCost;
	private String effectText;
	private String flavourText;
	private int power;
	private int toughness;
	private String artistName;
	
	public MTGCard(String name, int convertedManaCost, String effectText, String flavourText,
						int power, int toughness, String artistName) {
		this.name = name;
		this.convertedManaCost = convertedManaCost;
		this.effectText = effectText;
		this.flavourText = flavourText;
		this.power = power;
		this.toughness = toughness;
		this.artistName = artistName;
	}
	
	public String toString() {
		return "Card name:\t" + name + "\nConverted mana cost:\t" + convertedManaCost + 
			   "\nEffect text:\t" + effectText + "\nFlavour text:\t" + flavourText +
			   "\nPower:\t" + power + "\tToughness:\t" + toughness +
			   "\nArtist name:\t" + artistName; 
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getConvertedManaCost() {
		return convertedManaCost;
	}
	
	public void setConvertedManaCost(int convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}
	
	public String getEffectText() {
		return effectText;
	}
	
	public void setEffectText(String effectText) {
		this.effectText = effectText;
	}
	
	public String getFlavourText() {
		return flavourText;
	}
	
	public void setFlavourText(String flavourText) {
		this.flavourText = flavourText;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getToughness() {
		return toughness;
	}
	
	public void setToughness(int toughness) {
		this.toughness = toughness;
	}
	
	public String getArtistName() {
		return artistName;
	}
	
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
}
