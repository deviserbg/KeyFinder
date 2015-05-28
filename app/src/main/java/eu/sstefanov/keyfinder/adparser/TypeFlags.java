package eu.sstefanov.keyfinder.adparser;

public class TypeFlags extends AdElement {
	public static int FLAGS_LE_LIMITED_DISCOVERABLE_MODE = 0x01;
	public static int FLAGS_LE_GENERAL_DISCOVERABLE_MODE = 0x02;
	public static int FLAGS_BR_EDR_NOT_SUPPORTED = 0x04;
	public static int FLAGS_SIMULTANEOUS_LE_CONTROLLER = 0x08;
	public static int FLAGS_SIMULTANEOUS_LE_HOST = 0x10;
	
	public TypeFlags(byte data[],int pos) {
		flags = ((int)data[pos]) & 0xFF;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("flags:");
		if( ( flags & FLAGS_LE_LIMITED_DISCOVERABLE_MODE ) != 0 )
			sb.append("LE Limited Discoverable Mode");
		if( ( flags & FLAGS_LE_GENERAL_DISCOVERABLE_MODE ) != 0 ) {
			if( sb.length()>6)
				sb.append(",");
			sb.append("LE General Discoverable Mode");
		}
		if( ( flags & FLAGS_BR_EDR_NOT_SUPPORTED ) != 0 ) {
			if( sb.length()>6)
				sb.append(",");
			sb.append("BR/EDR Not Supported");
		}
		if( ( flags & FLAGS_SIMULTANEOUS_LE_CONTROLLER ) != 0 ) {
			if( sb.length()>6)
				sb.append(",");
			sb.append("Simultaneous LE and BR/EDR to Same Device Capable (Controller)");
		}
		if( ( flags & FLAGS_SIMULTANEOUS_LE_HOST ) != 0 ) {
			if( sb.length()>6)
				sb.append(",");
			sb.append("Simultaneous LE and BR/EDR to Same Device Capable (Host)");
		}
		return new String(sb);		
	}
	
	public int getFlags() {
		return flags;
	}
	
	int flags;
}
