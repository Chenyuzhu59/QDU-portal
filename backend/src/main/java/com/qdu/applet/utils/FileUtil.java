package com.qdu.applet.utils;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtil {
    public static boolean saveCaptcha(String base64, String answer) {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64 == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "captcha\\" + CommonUtil.getNow() + "_answer.jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}