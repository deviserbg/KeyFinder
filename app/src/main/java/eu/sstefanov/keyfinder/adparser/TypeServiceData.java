package eu.sstefanov.keyfinder.adparser;

public class TypeServiceData extends AdElement {

	public TypeServiceData( byte data[],int pos, int len) {
		int ptr = pos;
		int v = ((int)data[ptr]) & 0xFF;
		ptr++;
		v |= (((int)data[ptr]) & 0xFF ) << 8;
		ptr++;
		uuid = v;
		int blen = len - 2;
		b = new byte[blen];
		System.arraycopy(data, ptr, b, 0, blen);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "Service data (uuid: "+hex16(uuid)+"): ");
		for( int i = 0 ; i < b.length ; ++i ) {
			if( i > 0)
				sb.append(",");
			int v = ((int)b[i]) & 0xFF;
			sb.append(hex8(v));			
		}
		return new String(sb);
	}
	
	public int getUUID() {
		return uuid;
	}
	
	public byte[] getBytes() {
		return b;
	}
	
	int uuid;
	byte b[];

}
