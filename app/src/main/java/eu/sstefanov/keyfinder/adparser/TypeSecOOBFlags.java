package eu.sstefanov.keyfinder.adparser;

public class TypeSecOOBFlags extends AdElement {
	public static int FLAGS_OOB_DATA_PRESENT = 0x01;
	public static int FLAGS_LE_SUPPORTED_HOST = 0x02;
	public static int FLAGS_SIMULTANEOUS_LE_BR_EDR = 0x04;
	public static int FLAGS_RANDOM_ADDRESS = 0x08;
	
	public TypeSecOOBFlags(byte data[],int pos) {
		flags = ((int)data[pos]) & 0xFF;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("OOB Flags: ");
		if( ( flags & FLAGS_OOB_DATA_PRESENT ) != 0 )
			sb.append("OOB data present");
		if( ( flags & FLAGS_LE_SUPPORTED_HOST ) != 0 ) {
			if( sb.length()>10)
				sb.append(",");
			sb.append("LE supported (Host)");
		}
		if( ( flags & FLAGS_SIMULTANEOUS_LE_BR_EDR ) != 0 ) {
			if( sb.length()>10)
				sb.append(",");
			sb.append("Simultaneous LE and BR/EDR to Same Device Capable (Host)");
		}
		if( sb.length()>10)
			sb.append(",");
		if( ( flags & FLAGS_RANDOM_ADDRESS ) != 0 ) 
			sb.append("Random Address");
		else
			sb.append("Public Address");
		return new String(sb);		
	}
	
	public int getFlags() {
		return flags;
	}
	
	int flags;
}
