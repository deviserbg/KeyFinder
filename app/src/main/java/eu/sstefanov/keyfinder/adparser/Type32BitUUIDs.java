package eu.sstefanov.keyfinder.adparser;

public class Type32BitUUIDs extends AdElement {
	public Type32BitUUIDs( int type, byte data[], int pos, int len) {
		this.type = type;
		int items = len / 4;
		uuids = new int[items];
		int ptr = pos;
		for( int i = 0 ; i < items ; ++i ) {
			int v = ((int)data[ptr]) & 0xFF;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 8;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 16;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 24;
			ptr++;
			uuids[i] = v;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("flags: ");
		switch( type ) {
		case 0x04:
			sb.append( "More 32-bit UUIDs: ");
			break;
		case 0x05:
			sb.append("Complete list of 32-bit UUIDs: ");
			break;
		case 0x15:
			sb.append("Service UUIDs: ");
			break;			
		default:
			sb.append( "Unknown 32Bit UUIDs type: 0x"+Integer.toHexString(type)+": ");
			break;
		}
		for( int i = 0 ; i < uuids.length ; ++i ) {
			if( i > 0 )
				sb.append(",");
			sb.append( "0x"+hex32(uuids[i]));
		}
		return new String(sb);
	}

	public int getType() {
		return type;
	}
	
	public int[] getUUIDs() {
		return uuids;
	}
	
	int type;
	int uuids[];

}
