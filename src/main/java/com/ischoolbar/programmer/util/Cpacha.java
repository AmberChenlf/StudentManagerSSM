package com.ischoolbar.programmer.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author bingqiong.cbb
 * @date 2019-10-19 17:37
 *
 * @project:StudentManagerSSM
 * @descritption:绘制验证码图片
 *
 **/
public class Cpacha {
    /**
     * 验证码来源
     */
    final private char[] code={
            '2','3','4','5','6','7','8','9',
            'q','w','e','r','t','y','u','i',
            'p','a','s','d','f','g','h','j',
            'k','z','x','c','v','b','n','m',
            'Q','W','E','R','T','Y','U','P',
            'A','S','D','F','G','H','J','K',
            'Z','X','C','V','B','N','M'

    };
    /**
     * 字体
     */
    final private String[] fontName = new String[]{
            "黑体","Arial","Times"
    };

    /**
    * 字体样式
    */
    final private int[] fontStyles = new int[]{
        Font.BOLD,Font.ITALIC|Font.BOLD
    };

    /**
     *验证码长度
     * 默认四个字符
     */
    private int vcodeLen=4;

    /**
     * 验证码图片字体大小
     * 默认14
     */
    private int fontSize=17;

    /**
     * 验证码图片宽度
     */
    private int width = (fontSize+1)*vcodeLen+10;

    /**
     * 验证码图片的高度
     */
    private int height = fontSize+12;

    /**
     * 干扰线条数
     * 默认3
     */
    private int disturbline=3;

    public Cpacha() {
    }

    public Cpacha(int vcodeLen) {
        this.vcodeLen = vcodeLen;
        this.width = (fontSize+1)*vcodeLen+10;
    }

    /**
     * 指定验证码长度，图片宽度，高度
     * @param vcodeLen
     * @param width
     * @param height
     */
    public Cpacha(int vcodeLen, int width, int height) {
        this.vcodeLen = vcodeLen;
        this.width = width;
        this.height = height;
    }


    /**
     * 生成验证码图片
     * @param vcode
     * @param drawline
     * @return
     */
    public BufferedImage generatorVcodeImage(String vcode,boolean drawline){
        //创建一个图片
        BufferedImage vcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = vcodeImage.getGraphics();

//        填充背景色
        graphics.setColor(new Color(246,240, 250));
        graphics.fillRect(0,0,width,height);
        if(drawline){
            drawDisturbLine(graphics);
        }

//        用于生成伪随机数
        Random ran = new Random();

//        在图片上画验证码
        for(int i=0;i<vcode.length();i++){
//            设置字体
            graphics.setFont(new Font(fontName[ran.nextInt(fontName.length)], fontStyles[ran.nextInt(fontStyles.length)],fontSize));

//            随机生成颜色
            graphics.setColor(getRandomColor());

//            画验证码
            graphics.drawString(vcode.charAt(i)+"",i*fontSize+10, fontSize+5);
        }
//        释放此图形的上下文以及它使用的所有系统资源
        graphics.dispose();
        return vcodeImage;
    }




    public BufferedImage generatorRotateVcodeImage(String vcode,boolean drawline) {
        //创建一个图片
        BufferedImage vcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = vcodeImage.createGraphics();

        //填充背景色
        graphics2D.setColor(new Color(246, 240, 250));
        graphics2D.fillRect(0, 0, width, height);
        if (drawline) {
            drawDisturbLine(graphics2D);
        }

//        在图片上画验证码
        for (int i = 0; i < vcode.length(); i++) {
            BufferedImage rotateImage = getRotateImage(vcode.charAt(i));

//            画验证码
            graphics2D.drawImage(rotateImage, null,(int)(this.height*0.7)*i, 0);
        }
//        释放此图形的上下文以及它使用的所有系统资源
        graphics2D.dispose();
        return vcodeImage;
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public String generatorVcode(){
        int len = code.length;
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<vcodeLen;i++){
            int index = ran.nextInt(len);
            sb.append(code[index]);

        }
        return sb.toString();
    }

    /**
     * 为验证码画一些干扰线
     * @param graphics
     */
    private void drawDisturbLine(Graphics graphics){
        Random ran = new Random();
        for(int i = 0;i<disturbline;i++){
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            graphics.setColor(getRandomColor());
//            画干扰线
            graphics.drawLine(x1,y1,x2,y2);

        }


    }

    private BufferedImage getRotateImage(char c) {
        BufferedImage rotateImage = new BufferedImage(height, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = rotateImage.createGraphics();

//        设置透明度为0
        g2d.setColor(new Color(255,255,255));
        g2d.fillRect(0,0,height,height);
        Random ran = new Random();
        g2d.setFont(new Font(fontName[ran.nextInt(fontName.length)], fontStyles[ran.nextInt(fontStyles.length)], fontSize));
        g2d.setColor(getRandomColor());
        double theta = getThata();
//        旋转图片
        g2d.rotate(theta,height/2,height/2);
        g2d.drawString(Character.toString(c),(height-fontSize)/2, fontSize+5);
        g2d.dispose();

        return  rotateImage;
    }

    public int getVcodeLen() {
        return vcodeLen;
    }

    public void setVcodeLen(int vcodeLen) {
        this.vcodeLen = vcodeLen;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDisturbline() {
        return disturbline;
    }

    public void setDisturbline(int disturbline) {
        this.disturbline = disturbline;
    }

    /**
     *
     * @return 角度
     */
    private double getThata() {
        return ((int)(Math.random()*1000)%2==0?-1:1)*Math.random();
    }


    //    获取随机颜色
    private  static Color getRandomColor(){
        Random random = new Random();
        return new Color(random.nextInt(220), random.nextInt(220), random.nextInt(220));
    }
}
