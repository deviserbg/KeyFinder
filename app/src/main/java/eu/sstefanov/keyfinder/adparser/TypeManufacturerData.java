package eu.sstefanov.keyfinder.adparser;

public class TypeManufacturerData extends AdElement {

	public TypeManufacturerData( byte data[],int pos, int len) {
		int ptr = pos;
		int v = ((int)data[ptr]) & 0xFF;
		ptr++;
		v |= (((int)data[ptr]) & 0xFF ) << 8;
		ptr++;
		manufacturer = v;
		int blen = len - 2;
		b = new byte[blen];
		System.arraycopy(data, ptr, b, 0, blen);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "Manufacturer data (manufacturer: "+hex16(manufacturer)+"): ");
		for( int i = 0 ; i < b.length ; ++i ) {
			if( i > 0)
				sb.append(",");
			int v = ((int)b[i]) & 0xFF;
			sb.append(hex8(v));			
		}
		return new String(sb);
	}
	
	public int getManufacturer() {
		return manufacturer;
	}
	
	public byte[] getBytes() {
		return b;
	}
	
	int manufacturer;
	byte b[];

}
