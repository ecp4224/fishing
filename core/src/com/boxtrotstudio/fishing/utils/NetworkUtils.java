package com.boxtrotstudio.fishing.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class NetworkUtils {
    public static byte[] float2ByteArray (float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static int byteArray2Int(byte[] array) {
        return ByteBuffer.wrap(array).getInt();
    }

    public static byte[] int2ByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static float byteArray2Float(byte[] array) {
        return ByteBuffer.wrap(array).getFloat();
    }

    public static byte[]  double2ByteArray(double dob) {
        return ByteBuffer.allocate(8).putDouble(dob).array();
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        long bytesCopied = 0;
        byte[] buffer = new byte[1024];
        int bytes = input.read(buffer);
        while (bytes >= 0) {
            output.write(buffer, 0, bytes);
            bytesCopied += bytes;
            bytes = input.read(buffer);
        }
        return bytesCopied;
    }
}
