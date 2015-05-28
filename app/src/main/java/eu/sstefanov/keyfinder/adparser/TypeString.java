package eu.sstefanov.keyfinder.adparser;

public class TypeString extends AdElement {
	public TypeString( int type, byte data[], int pos, int len) {
		this.type = type;
		int ptr = pos;
		byte sb[] = new byte[len];
		System.arraycopy(data, pos, sb, 0, len);
		s = new String(sb);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		switch(type) {
		case 0x08:
			sb.append("Short local name: ");
			break;
			
		case 0x09:
			sb.append("Local name: ");
			break;
			
		default:
			sb.append("Unknown string type: 0x"+Integer.toString(type)+": ");
			break;
		}
		sb.append("'");
		sb.append(s);
		sb.append("'");
		return new String(sb);
	}

	public int getType() {
		return type;
	}
	
	public String getString() {
		return s;
	}
	
	int type;
	String s;
}
