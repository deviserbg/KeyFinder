package eu.sstefanov.keyfinder.adparser;

public class TypeByteDump extends AdElement {

	public TypeByteDump( int type,byte data[],int pos,int len) {
		this.type = type;
		b = new byte[len];
		System.arraycopy(data, pos, b, 0, len);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		switch(type) {
		case 0x0D:
			sb.append("Class of device: ");
			break;
			
		case 0x0E:
			sb.append("Simple Pairing Hash C: ");
			break;

		case 0x0F:
			sb.append("Simple Pairing Randomizer R: ");
			break;
			
		case 0x10:
			sb.append("TK Value: ");
			break;
		}
		for( int i = 0 ; i < b.length ; ++i ) {
			if( i > 0)
				sb.append(",");
			int v = ((int)b[i]) & 0xFF;
			sb.append(hex8(v));
		}
		return new String(sb);
	}

	public int getType() {
		return type;
	}
	
	public byte[] getBytes() {
		return b;
	}
	
	byte b[];
	int type;
}
