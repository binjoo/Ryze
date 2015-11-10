package com.base.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

public class Generator {
    private String hash;
    private boolean[][] booleanValueArray;

    public BufferedImage create(String hash, int size) throws Exception {
        if (StringUtils.isBlank(hash) || hash.length() < 16 || size <= 0) {
            throw new Exception("illegal argument hash:not null and size >= 16");
        }
        this.hash = hash;

        boolean[][] array = this.getBooleanValueArray();

        BigDecimal b = new BigDecimal(size / 5.0);
        int ratio = b.setScale(1, RoundingMode.HALF_UP).intValue();

        BufferedImage identicon = new BufferedImage(ratio * 5, ratio * 5, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = identicon.getGraphics();

        graphics.setColor(this.getBackgroundColor()); // 背景色
        graphics.fillRect(0, 0, identicon.getWidth(), identicon.getHeight());

        graphics.setColor(this.getForegroundColor()); // 图案前景色
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (array[i][j]) {
                    graphics.fillRect(j * ratio, i * ratio, ratio, ratio);
                }
            }
        }

        return identicon;
    }

    private boolean[][] getBooleanValueArray() throws Exception {
        boolean[][] array = new boolean[6][5];

        // 初始化字符串
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                array[i][j] = false;
            }
        }

        for (int i = 0; i < hash.length(); i += 2) {
            int s = i / 2; // 只取hash字符串偶数编号（从0开始）的字符
            BigDecimal b = new BigDecimal(Integer.parseInt(hash.charAt(i) + "", 16) / 10.0);
            int toInt = b.setScale(1, RoundingMode.HALF_UP).intValue();
            boolean v = toInt > 0 ? true : false;
            if (s % 3 == 0) {
                array[s / 3][0] = v;
                array[s / 3][4] = v;
            } else if (s % 3 == 1) {
                array[s / 3][1] = v;
                array[s / 3][3] = v;
            } else {
                array[s / 3][2] = v;
            }
        }

        this.booleanValueArray = array;

        return this.booleanValueArray;
    }

    private Color getBackgroundColor() {
        int r = Integer.parseInt(String.valueOf(this.hash.charAt(0)), 16) * 16;
        int g = Integer.parseInt(String.valueOf(this.hash.charAt(1)), 16) * 16;
        int b = Integer.parseInt(String.valueOf(this.hash.charAt(2)), 16) * 16;

        return new Color(r, g, b);
    }

    private Color getForegroundColor() {
        int r = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 1)), 16) * 16;
        int g = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 2)), 16) * 16;
        int b = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 3)), 16) * 16;

        return new Color(r, g, b);
    }
}