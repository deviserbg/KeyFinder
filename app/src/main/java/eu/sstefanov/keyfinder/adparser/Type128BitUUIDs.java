package eu.sstefanov.keyfinder.adparser;

import java.util.UUID;

public class Type128BitUUIDs extends AdElement {
	public Type128BitUUIDs( int type, byte data[], int pos, int len) {
		this.type = type;
		int items = len / 16;
		uuids = new UUID[items];
		int ptr = pos;
		for( int i = 0 ; i < items ; ++i ) {
			long vl = ((long)data[ptr]) & 0xFF;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 8;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 16;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 24;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 32;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 40;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 48;
			ptr++;
			vl |= (((long)data[ptr]) & 0xFF ) << 56;
			ptr++;
			long vh = ((long)data[ptr]) & 0xFF;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 8;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 16;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 24;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 32;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 40;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 48;
			ptr++;
			vh |= (((long)data[ptr]) & 0xFF ) << 56;
			ptr++;
			uuids[i] = new UUID(vh,vl);
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
		default:
			sb.append( "Unknown 32Bit UUIDs type: 0x"+Integer.toHexString(type)+": ");
			break;
		}
		for( int i = 0 ; i < uuids.length ; ++i ) {
			if( i > 0 )
				sb.append(",");
			sb.append( uuids[i].toString());
		}
		return new String(sb);
	}

	public int getType() {
		return type;
	}
	
	public UUID[] getUUIDs() {
		return uuids;
	}
	
	int type;
	UUID uuids[];
}
