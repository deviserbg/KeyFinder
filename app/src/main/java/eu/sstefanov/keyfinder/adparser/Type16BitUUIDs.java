package eu.sstefanov.keyfinder.adparser;

public class Type16BitUUIDs extends AdElement {
	public Type16BitUUIDs( int type, byte data[], int pos, int len) {
		this.type = type;
		int items = len / 2;
		uuids = new int[items];
		int ptr = pos;
		for( int i = 0 ; i < items ; ++i ) {
			int v = ((int)data[ptr]) & 0xFF;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 8;
			ptr++;
			uuids[i] = v;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		switch( type ) {
		case 0x02:
			sb.append( "More 16-bit UUIDs: ");
			break;
		case 0x03:
			sb.append("Complete list of 16-bit UUIDs: ");
			break;
		case 0x14:
			sb.append("Service UUIDs: ");
			break;
		default:
			sb.append( "Unknown 16Bit UUIDs type: 0x"+Integer.toHexString(type)+": ");
			break;
		}
		for( int i = 0 ; i < uuids.length ; ++i ) {
			if( i > 0 )
				sb.append(",");
			sb.append( "0x"+hex16(uuids[i]));
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
