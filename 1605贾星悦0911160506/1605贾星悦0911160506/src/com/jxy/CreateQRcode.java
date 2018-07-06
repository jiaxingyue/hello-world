package com.jxy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRcode {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int version=6;
		int imageSize=127;
		Qrcode x=new Qrcode();
		x.setQrcodeErrorCorrect('L');
	    x.setQrcodeEncodeMode('B');
	    x.setQrcodeVersion(version);
	    String qrData="http://www.dijiaxueshe.com";
        byte[] contentBytes=qrData.getBytes("utf-8");
        //����ͼƬ����	
        BufferedImage bufferedImage=new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);
        //ͼƬ����
        Graphics2D gs=bufferedImage.createGraphics();
        //���ñ�����ɫ
        gs.setBackground(Color.WHITE);
        //�������
        gs.clearRect(0,0,imageSize,imageSize);
        //��ά�����
        int startR=255,startG=0,startB=0;
        int endR=0,endG=0,endB=255;
        int pixoff=2;
        boolean[][] s=x.calQrcode(contentBytes);
        for(int i=0;i<s.length;i++){
        	for(int j=0;j<s.length;j++){
        		if(s[i][j]){
        			int num1=startR+(endR-startR)*(i+1)/s.length;
        			int num2=startG+(endG-startG)*(i+1)/s.length;
        			int num3=startB+(endB-startB)*(i+1)/s.length;
        			Color color=new Color(num1,num2,num3);
        			gs.setColor(color);
        			gs.fillRect(i*3+pixoff,j*3+pixoff,3,3);
        		}
        	}
        }
        //���ͼƬ
        BufferedImage logo=scale("D:logo.jpg", 30, 30, true);
        int o=(imageSize-logo.getHeight())/2;
        gs.drawImage(logo, o, o, 30, 30, null);
        /*//���ͼƬ
         BufferedImage logo=ImageIO.read(new File("D:/logo.jpg"));
         int logoSize=imageSize/4;
        //����logoͼƬ�ڶ�ά������
        int o=(imageSize-logoSize)/2;
        gs.drawImage(logo, o, o, logoSize, logoSize, null);*/
       gs.dispose();
       bufferedImage.flush();
       try{
    	   ImageIO.write(bufferedImage,"png",new File("D:/qrcode.png"));
       }catch(IOException e){
    	   e.printStackTrace();
    	   System.out.println("��������");
    	   
       }
	System.out.println("��ά�����ɳɹ���");
	}
	private static BufferedImage scale(String logoPath,int width,int height,boolean hasFiller) throws Exception{
		double ratio=0.0;//���ű���
		File file=new File(logoPath);
		BufferedImage srcImage=ImageIO.read(file);
		Image destImage =srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		//�������
		if ((srcImage.getHeight()>height)||(srcImage.getWidth()>width)){
		    if(srcImage. getHeight ()>srcImage.getWidth()){
		       ratio=(new Integer(height)).doubleValue() / srcImage.getHeight();
		    }else{
		       ratio=(new Integer(width)).doubleValue()/ srcImage.getWidth();
		}
		    AffineTransformOp op= new AffineTransformOp(AffineTransform. getScaleInstance(ratio, ratio),null);
		    destImage=op.filter(srcImage,null);
		}
		if(hasFiller){//����
		   BufferedImage image =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		   Graphics2D graphic =image. createGraphics();
		   graphic.setColor(Color. white);
		   graphic.fillRect(0,0,width,height);
		   if(width==destImage.getWidth(null)) {
		      graphic.drawImage(destImage, 0,(height-destImage.getHeight(null))/2,destImage.getWidth(null),
		              destImage.getHeight(null),Color.white,null);
		}else{
		      graphic.drawImage(destImage,(width-destImage. getWidth(null))/2,0,destImage.getWidth(null),
		              destImage.getHeight(null),Color.white,null);
		}
		graphic.dispose();
		destImage=image;
		}
		return (BufferedImage) destImage;

	}

}
