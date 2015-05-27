package eu.sstefanov.keyfinder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sstefanov on 27-May-15.
 */
public  class AdRecord {
    public AdRecord(int length, int type, byte[] data) {
        String decodedRecord = "";
        try {
            decodedRecord = new String(data,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        Log.d("DEBUG", "Length: " + length + " Type : " + type + " Data : " + ByteArrayToString(data));
    }

    // ...

    public static List<AdRecord> parseScanRecord(byte[] scanRecord) {
        List<AdRecord> records = new ArrayList<AdRecord>();

        int index = 0;
        while (index < scanRecord.length) {
            int length = scanRecord[index++];
            //Done once we run out of records
            if (length == 0) break;

            int type = scanRecord[index];
            //Done if our record isn't a valid type
            if (type == 0) break;

            byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);

            records.add(new AdRecord(length, type, data));
            //Advance
            index += length;
        }

        return records;
    }

}
