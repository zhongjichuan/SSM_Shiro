package com.QRCode_zxing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import com.swetake.util.Qrcode;

public class QRCode {

	/**生成二维码
	 * @param content 二维码内容
	 * @param imgPath 二维码存放路径
	 * @param imgType 二维码格式
	 * @param size 二维码尺寸（默认正方形）
	 * @throws Exception 
	 */
	public 	static void getQRCode(String content,String imgPath,String imgType,int size) throws Exception{
		//实例化QRCode
		Qrcode qrCode = new Qrcode();
		//设置二维码的排错率L(7%) M(15%) Q(25%) H(35%)，排错率越小，可存储的信息越少，但清晰度要求越少
		qrCode.setQrcodeErrorCorrect('M');
		//可存放的信息类型：N:数字，A:数字A-Z，B:所有
		qrCode.setQrcodeEncodeMode('B');
		//设置版本（版本至49）
		qrCode.setQrcodeVersion(size);
		
		//整个图片的尺寸
		int imgSize = 67+12*(size-1);
		
		//设置整个二维码图片尺寸（内存中的整个图片）
        BufferedImage bufImg=new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_BGR);
        
        //绘制整个二维码画板
        //生成图片对应的画板
        Graphics2D gs=bufImg.createGraphics();
        //设置二维码背景颜色白色
        gs.setBackground(Color.WHITE);
        //创建一个矩形区域
        gs.clearRect(0, 0, imgSize, imgSize);
        //设置二维码的图片颜色值 黑色
        gs.setColor(Color.BLACK);
        //将内容转换成字节数组
        byte[] contentBytes = content.getBytes("utf-8");
        boolean[][] calQrcode = qrCode.calQrcode(contentBytes);
        int pixoff=2;//二维码边长
        //遍历内容，将信息输出到画板上
        for(int i=0;i<calQrcode.length;i++){
        	for (int j = 0; j < calQrcode.length; j++) {
				if(calQrcode[j][i]){
					gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
				}
			}
        }
        
        //设置logo
        Image logo = ImageIO.read(new File("src/main/resources/images/1.jpg"));
        int width = bufImg.getWidth();
		int height=bufImg.getHeight();
		//图标太大，可能会使二维码扫不出来
		gs.drawImage(logo, width/7*3, height/7*3, width/7, height/7, null);
        
        gs.dispose();//释放画板空间
        bufImg.flush();
        //生成二维码图片
        File imgFile=new File(imgPath);
        //将内存中图片写入磁盘
        ImageIO.write(bufImg, imgType, imgFile);
        System.out.println("二维码生成成功！");
	}
	
	
	/**解析二维码
	 * @param imgPath 二维码路径
	 * @return
	 * @throws IOException 
	 */
	public static String decodeQRCode(String imgPath){
		
		BufferedImage bufferedImage = null;
		String content = null;
		try {
			File file = new File(imgPath);
			if(!file.exists()){
				return null;
			}
			bufferedImage = ImageIO.read(file);
			QRCodeDecoder decoder = new QRCodeDecoder();
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode(bufferedImage);
			//解析二维码
			byte[] contentBytes = decoder.decode(twoDimensionCode);
			content = new String(contentBytes, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	public static void main(String[] args) throws Exception {
//		getQRCode("helloworld", "src/main/resources/images/二维码.png", "png", 22);
		getQRCode("http://m.55bbs.com/shangchang_2573473/", "src/main/resources/images/二维码.png", "png",20);
		System.out.println(decodeQRCode("src/main/resources/images/二维码.png"));
	}
}
