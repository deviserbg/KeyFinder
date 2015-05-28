package eu.sstefanov.keyfinder.adparser;

public class TypeSlaveConnectionIntervalRange extends AdElement {
	public TypeSlaveConnectionIntervalRange(byte data[],int pos,int len) {
		connIntervalMin = 0xFFFF;
		connIntervalMax = 0xFFFF;
		int ptr = pos;
		if( len >= 4) {
			int v = ((int)data[ptr]) & 0xFF;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 8;
			ptr++;
			connIntervalMin = v;
			v = ((int)data[ptr]) & 0xFF;
			ptr++;
			v |= (((int)data[ptr]) & 0xFF ) << 8;
			ptr++;
			connIntervalMax = v;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "Slave Connection Interval Range: ");
		sb.append( "conn_interval_min: ");
		if( connIntervalMin == 0xFFFF)
			sb.append( "none");
		else
			sb.append( Float.toString((float)connIntervalMin*1.25f)+" msec");
		sb.append( ",conn_interval_max: ");
		if( connIntervalMax == 0xFFFF)
			sb.append( "none");
		else
			sb.append( Float.toString((float)connIntervalMax*1.25f)+" msec");
		return new String(sb);
	}

	public int getConnIntervalMin() {
		return connIntervalMin;
	}

	public int getConnIntervalMax() {
		return connIntervalMax;
	}
	
	int connIntervalMin;
	int connIntervalMax;
}
