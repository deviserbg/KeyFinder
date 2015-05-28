package eu.sstefanov.keyfinder.adparser;

public class TypeUnknown extends AdElement {

	public TypeUnknown(int type,byte data[], int pos,int len ) {
		unknownType = type;
		unknownData = new byte[len];
		System.arraycopy(data, pos, unknownData,0, len);
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "unknown type: 0x"+Integer.toHexString(unknownType)+" ");
		for( int i = 0 ; i < unknownData.length ; ++i ) {
			if( i > 0 )
				sb.append(",");
			int v = ((int)unknownData[i]) & 0xFF; 
			sb.append("0x"+Integer.toHexString(v));
		}
		return new String(sb);
	}

	public int getType() {
		return unknownType;
	}
	
	public byte[] getBytes() {
		return unknownData;
	}
	
	int unknownType;
	byte unknownData[];
}
