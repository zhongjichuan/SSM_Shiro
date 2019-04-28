package com.QRCode_zxing;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import jp.sourceforge.qrcode.util.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Zxing {

	/**生成二维码
	 * @param imgPath 二维码保存路径
	 * @throws Exception 
	 */
	public static void encode(String contents,String imgPath, int width,int height) throws Exception{
		//定义二维码样式
		Map hints = new HashMap();
		//排错率
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//边距
		hints.put(EncodeHintType.MARGIN, 2);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//创建比特矩阵(位矩阵)的QR码编码的字符串.
		//内容;条形码格式;条形码的宽高;样式集合map
		//BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);
		BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		
		
		/*
		 *zxing的辅助jar包提供的快捷方法，快速生成二维码
		 *writeToFile由谷歌提供，逻辑同下方相同
		 */
		//MatrixToImageWriter.writeToFile(matrix, "png", new File(imgPath));
		
		
		
		//将matrix的信息绘制到内存的一张图片里
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
            	//指定点赋色
            	bufferedImage.setRGB(x, y,  (matrix.get(x, y) ? Color.BLACK : Color.WHITE));  
            }  
        }  
		
		Graphics2D graphics = bufferedImage.createGraphics();
		//设置logo
        Image logo = ImageIO.read(new File("src/main/resources/images/1.jpg"));
		//插入图标，图标太大，可能会使二维码扫不出来//边距+2？？
        graphics.drawImage(logo, width/5*2+2, height/5*2+2, width/5, height/5, null);
        
        BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        graphics.setStroke(stroke);
        	
        //10,10圆角度数
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(width/5*2+2-1, height/5*2+2-1, width/5+2, height/5+2,  15, 15);
        graphics.setColor(java.awt.Color.white);
        graphics.draw(round);
        
        graphics.dispose();
		File file = new File(imgPath);
		bufferedImage.flush();
		//写入到磁盘
		ImageIO.write(bufferedImage, "png", file);
		System.out.println("二维码生成成功");
	}
	
	public static String decode(String imgPath) throws Exception{
		File file = new File(imgPath);
		if(!file.exists()){
			return null;
		}
		MultiFormatReader formatReader = new MultiFormatReader();
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		Result result = formatReader.decode(binaryBitmap, hints);
		return result.toString();
	}
	public static void main(String[] args) throws Exception {
		encode("HelloWorld中文", "src/main/resources/images/zxing.png", 400, 400);
		System.out.println(decode("src/main/resources/images/zxing.png"));
	}
}
