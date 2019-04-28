package com.demo.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/***
 * 手动输入url,测试响应头
 *
 */
@Controller
public class HttpTestController {

//	@RequestMapping("/httpTestController/test1")
//	public void test1(HttpServletResponse response){
//		response.setStatus(302);//返回302错误
//        response.setHeader("location", "redirect:/message.jsp");//请求重定向
//	}
	
	//数据压缩
	@RequestMapping("/httpTestController/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String data = "abcdabcdabcdabcdabcdabcd";
        System.out.println("原始数据的大小为：" + data.getBytes().length);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();//字节数组流
 
        GZIPOutputStream gout = new GZIPOutputStream(bout); //2.将压缩流中数据写入到字节数组中
        gout.write(data.getBytes());//data.getBytes()将数据转换为字节，1.将其写入到压缩流
        gout.close();//压缩流自带Buffer，故只有关闭资源后才会立即显示出来
 
        //得到压缩后的数据
        byte g[] = bout.toByteArray();
        System.out.println(request.getLocalAddr()+"***"+request.getLocalName());
//        response.setHeader("Content-Encoding", "gzip");
//        response.setHeader("Content-Length",g.length +"");
        
        //下载文件
        response.getWriter().write(("<a href='http://localhost:80/SSM/httpTestController/test5'>"+123+"</a>"));
        //response.getOutputStream().
    }

	 //指定回送数据类型
	@RequestMapping("/httpTestController/test3")
    public void test3(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //.jpg文件对应的类型为"image/jpg"，可在tomcat_8.5\conf\web.xml中找【Ctrl+F=》输入>jpg(表示后缀为jpg)】
       response.setHeader("content-type", "image/jpg");
 
       	InputStream in = new FileInputStream(new File("F:/worksapce/SSM/src/main/resources/images/1.jpg"));
        byte buffer[] = new byte[1024];
        int len = 0;
        OutputStream out = response.getOutputStream();
        while((len=in.read(buffer))>0){//将图片输出
            out.write(buffer, 0, len);
        }
 
    }
	
	 //指定浏览器定时刷新
	@RequestMapping("/httpTestController/test4")
    public void test4(HttpServletRequest request, HttpServletResponse response) throws IOException{
 
    	response.setHeader("refresh", "3");//每隔3秒刷新一次
        response.setHeader("refresh", "3;url='http://www.baidu.com'");//三秒后跳到指定页面
    }
 
    //指定浏览器下载
	@RequestMapping("/httpTestController/test5")
    public void test5(HttpServletRequest request, HttpServletResponse response) throws IOException{
 
        response.setHeader("content-disposition", "attachment;filename=xxx.jpg");//下载.jpg文件
 
        InputStream in = new FileInputStream(new File("F:/worksapce/SSM/src/main/resources/images/1.jpg"));
        byte buffer[] = new byte[1024];
        int len = 0;
        OutputStream out = response.getOutputStream();
        while((len=in.read(buffer))>0){
            out.write(buffer, 0, len);
        }
	}

}
