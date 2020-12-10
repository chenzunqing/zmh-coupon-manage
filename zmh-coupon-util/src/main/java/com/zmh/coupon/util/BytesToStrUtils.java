package com.zmh.coupon.util;

public class BytesToStrUtils {

    // private static final char[] HEX = new char[] { '0', '1', '2', '3', '4',
    // '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public static final String enHex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        // for (int i = 0; i < arr.length; i++) {
        // sb.append(Integer.toHexString(arr[i] & 0xFF | 0x100).substring(1,
        // 3));
        // }

        for (byte bt : arr) {
            sb.append(HEX[bt >> 4 & 0x0F]);
            sb.append(HEX[bt & 0x0F]);
        }

        return sb.toString();
    }

}
