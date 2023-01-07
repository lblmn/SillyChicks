package com.bing.monkey.test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 * User:  Bing
 * Date:  2022/12/14
 */
@Service
public class TestService {

    private static final int FRONT_COLOR = Color.BLACK.getRGB();
    private static final int BACKGROUND_COLOR = Color.WHITE.getRGB();

    //  http://i.icoke.cn/M/41WE9F3OFP9P6EGH
    //  http://i.icoke.cn/M/465RK47HBIAL7KOL
    //  http://i.icoke.cn/M/495LAWJ3QVSA38GZ
    //  http://i.icoke.cn/M/49E4UJBX7EFELL4G
    //  http://i.icoke.cn/M/4R7R62MPC0DFLLK3
    //  http://i.icoke.cn/M/4YDK0LS56526L8FM
    public void geneQr(String url) {
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 520, 520, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? FRONT_COLOR : BACKGROUND_COLOR);
            }
        }
        String[] split = url.split("/");
        String name = split[split.length - 1];
        try {
            ImageIO.write(
                    bufferedImage,
                    "png",
                    new File(
                            "C:" + File.separator + "Users" + File.separator + "admin" + File.separator + "Desktop" + File.separator + "test" + File.separator + name + ".png"
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        for (int i = 0; i < 100; i++) {
            geneQr(randomUrl());
//            System.out.println(randomUrl());
        }
    }

    public String randomUrl() {
        StringBuilder url = new StringBuilder("http://i.icoke.cn/M/4");
        gene:
        {
            while (true) {
                for (int i = 1; i < (int) (Math.random() * 5); i += 1) {
                    char c = randomChar();
                    url.append(c);
                    if (url.length() == 36) {
                        break gene;
                    }
                }
                for (int i = 1; i < (int) (Math.random() * 5); i++) {
                    int n = randomNum();
                    url.append(n);
                    if (url.length() == 36) {
                        break gene;
                    }
                }
            }
        }
        return url.toString();
    }

    public char randomChar() {
        return (char) (int) (Math.random() * 26 + 65);
    }

    public int randomNum() {
        return (int) (Math.random() * 9);
    }

}
