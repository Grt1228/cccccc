package com.unfbx.demo.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Description
 * @Author Grt
 * @Date 2021-08-17
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = RAFTestFactory.getRAFWithModelR();
        System.out.println("raf.length()->获取文本内容长度:" + raf.length());
        System.out.println("raf.getFilePointer()->获取文本头指针:" + raf.getFilePointer());
        long endPosition = 0;
        raf.seek(endPosition);
        byte tmp = (byte) raf.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            raf.seek(endPosition);
            tmp = (byte) raf.read();
        }
        System.out.println(tmp);
        System.out.println("raf.getFilePointer()->第二次获取文本头指针:" + raf.getFilePointer());
    }


}
