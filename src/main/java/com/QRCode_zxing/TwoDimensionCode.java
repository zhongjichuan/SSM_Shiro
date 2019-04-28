package com.QRCode_zxing;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 *二维码核心操作类
 */
public class TwoDimensionCode implements QRCodeImage{

	BufferedImage bufferedImage;
	public TwoDimensionCode(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return bufferedImage.getHeight();
	}

	//获得指定像素的图标的颜色值
	@Override
	public int getPixel(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return bufferedImage.getRGB(arg0, arg1);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return bufferedImage.getWidth();
	}
	
}
