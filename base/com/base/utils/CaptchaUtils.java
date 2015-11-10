package com.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class CaptchaUtils {
    private final static String SESSION_NAME = "_CAPTCHA_TOKEN_";
    private static int WIDTH = 80;
    private static int HEIGHT = 32;
    private static int STRING_SIZE = 40;//干扰线数量
    private static int LENGTH = 4;
    private final static Random random = new Random();
    private static String RAND_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 生成验证码
     * 
     * @throws IOException
     */
    public static void get(HttpServletRequest request,
            HttpServletResponse response, HttpSession session,
            Map<String, Cookie> cookies) throws IOException {
        // if(ctx.isRobot()){
        // ctx.forbidden();
        // return;
        // }
        render(generateRegKey(request, response, session, cookies), response.getOutputStream(), WIDTH, HEIGHT);
    }

    /**
     * 检查验证码是否正确
     * 
     * @param req
     * @return
     */
    public static boolean validate(HttpServletRequest req, HttpSession session) {
        String code1 = (String) session.getAttribute(SESSION_NAME);//getValue();
        String code2 = req.getParameter("captcha");
        return StringUtils.equalsIgnoreCase(code1, code2);
    }

    private static String generateRegKey(HttpServletRequest request,
            HttpServletResponse response, HttpSession session,
            Map<String, Cookie> cookies) {
        String code = RandomStringUtils.randomAlphanumeric(LENGTH).toUpperCase();
        code.replace('0', 'W');
        code.replace('o', 'R');
        code.replace('I', 'E');
        code.replace('1', 'T');
        if (session != null) {
            session.setAttribute(SESSION_NAME, code);
        }
        return code;
    }

    private static void render(String text, OutputStream out, int width,
            int height) throws IOException {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // g.setColor(Color.RED);
        // g.drawRect(1,1,width-2,height-2);
        for (int i = 0; i < STRING_SIZE; i++) {
            g.setColor(getRandColor(150, 250));
            g.drawString(getRandomString(), random.nextInt(90), random.nextInt(35));
        }
        Font mFont = new Font("Consolas", Font.CENTER_BASELINE, 28);
        g.setFont(mFont);
        g.setColor(getRandColor(10, 240));
        g.drawString(text, 10, 25);
        ImageIO.write(bi, "png", out);
    }

    private void drowLine(Graphics2D g) {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    /*
     * 获取随机的字符
     */
    public static String getRandomString(){
        return String.valueOf(RAND_STRING.charAt(random.nextInt(RAND_STRING.length())));
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            System.out.println(5 + random.nextInt(10));
        }
//        String code = RandomStringUtils.randomAlphanumeric(LENGTH).toUpperCase();
//        code = code.replace('0', 'W');
//        code = code.replace('o', 'R');
//        code = code.replace('I', 'E');
//        code = code.replace('1', 'T');
//
//        FileOutputStream out = new FileOutputStream("d:\\aa.jpg");
//        render(code, out, 120, 40);
    }
}
