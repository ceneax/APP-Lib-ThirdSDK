package ceneax.lib.thirdsdk.util;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

public class BitmapUtil {

    /**
     * 将Bitmap对象转为字节数组
     */
    public static byte[] bitmap2ByteArray(Bitmap bmp) {
        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);

        return buf.array();
    }

}
