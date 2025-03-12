/*
 * Copyright (c) 10/14/19 3:58 PM.  Zhejiang xiaoqu information technology Co., Ltd.  All rights reserved.
 * Project lianzhong
 * File com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@4545d15f
 * Author yqj
 * Modify  10/9/19 4:48 PM
 */

package com.interesting.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileMd5Util {

    private final static Logger logger = LoggerFactory.getLogger(FileMd5Util.class);

    public static String md5Lower(String s) {
        String md5Hex = DigestUtils.md5Hex(s);
        return md5Hex;
    }
//
//    public static String getFileMd5(File file) throws FileNotFoundException {
//        String value = null;
//        FileInputStream in = new FileInputStream(file);
//        MappedByteBuffer byteBuffer = null;
//        try {
//            byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            md5.update(byteBuffer);
//            BigInteger bi = new BigInteger(1, md5.digest());
//            value = bi.toString(16);
//            if (value.length() < 32) {
//                value = "0" + value;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != in) {
//                try {
//                    in.getChannel().close();
//                    in.close();
//                } catch (IOException e) {
//                    logger.error("get file md5 error!!!", e);
//                }
//            }
//            if (null != byteBuffer) {
//                freedMappedByteBuffer(byteBuffer);
//            }
//        }
//        return value;
//    }
//
//    /**
//     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
//     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检 查是否还有线程在读或写
//     *
//     * @param mappedByteBuffer
//     */
//    public static void freedMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) {
//        try {
//            if (mappedByteBuffer == null) {
//                return;
//            }
//
//            mappedByteBuffer.force();
//            AccessController.doPrivileged(new PrivilegedAction<Object>() {
//                @Override
//                public Object run() {
//                    try {
//                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
//                        getCleanerMethod.setAccessible(true);
//                        sun.misc200.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
//                                new Object[0]);
//                        cleaner.clean();
//                    } catch (Exception e) {
//                        logger.error("clean MappedByteBuffer error!!!", e);
//                    }
//                    logger.info("clean MappedByteBuffer completed!!!");
//                    return null;
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
