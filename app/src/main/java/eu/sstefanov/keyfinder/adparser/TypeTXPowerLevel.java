package eu.sstefanov.keyfinder.adparser;

public class TypeTXPowerLevel extends AdElement {
	public TypeTXPowerLevel( byte data[],int pos) {
		txpower = data[pos];
	}
	
	@Override
	public String toString() {
		return "TX Power Level: "+
					Byte.toString(txpower)+
					" dBm";
	}

	public byte getTXPowerLevel() {
		return txpower;
	}
	
	byte txpower;
}
