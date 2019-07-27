package UnitTests.File;

import java.nio.ByteBuffer;

public class TestFile2 {

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocateDirect(100);
        String s = "Hello!";
        //byte[] byteval = s.getBytes();
        for (char c : s.toCharArray()) bb.putChar(c);
        bb.putChar('\0');

        bb.rewind();
        StringBuffer sb = new StringBuffer();
        char c = bb.getChar();
        while (c != '\0') {
            sb.append(c);
            c = bb.getChar();
        }
        //return new String(sb);
    }


}
