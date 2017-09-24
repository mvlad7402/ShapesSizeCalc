package com.alogo.pics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShapeCalc {

	private PicInfo pg;
	private int cnt;
	private int totCnt;
	
	public static void main(String[] args) {
	
		ShapeCalc sp = new ShapeCalc();
		sp.proc();

	}
	

	public void proc(){
		BufferedImage img = null;
		cnt = 0;
		totCnt=0;
		
		try{
			img = ImageIO.read(new File("shape.jpg"));
			int h = img.getHeight();
			int w = img.getWidth();
			

			int[] pixels = img.getRGB(0,0, w, h, null, 0, w);
			pg = new PicInfo(pixels, w, h);
			
			// calculate initial total
			int allCntIni=0; 
			for(int x = 0; x < w; x++){
				for(int y = 0; y < h; y++){
					int cc = pg.get(x, y);
					if(cc != -1){
						allCntIni ++;
					}
				
				}
			}
			
			System.out.println("h="+h+"; w="+w+"; allCntIni="+allCntIni);
			
			
			
			System.out.println("Start ...");
			pixCnt();
			
			System.out.println("Done allCntIni="+allCntIni+"; totCnt="+totCnt);
			
		} catch(IOException e){
			e.printStackTrace();
		}

		
	}
	
	// recursive procedure to calculate number of pixels in one shape
	public void pixCnt(){
		int shapeCnt = 0;
		for(int y = 0; y < pg.getH(); y++){
			for(int x = 0; x < pg.getW(); x++){
				int px = pg.get(x, y);
				if(px != -1 ){
					shapeCnt++;
					cnt = 0;
					System.out.println("Start shape("+shapeCnt+") x="+x+"; y="+y);
					pixCnt(x,y);
					System.out.println("Shape ("+shapeCnt+") cnt="+cnt);
					totCnt+=cnt;
				}
			}
		}
	}
	
	/*
	 * Recursive random shape pixel counter
	 * 
	 * @param x x - coordinate of the first found pixel in shape - non-white pixel
	 * @param y y - coordinate of the first found pixel in shape - non-white pixel  
	 * @return  void, counter is an instance variable of enclosed class 
	 */
	public int pixCnt_2(int x, int y, int cnt){
		// return if we are reaching edges of the picture
		if( (x<0 || x>=pg.getW())  || (y<0 || y>=pg.getH())){
			return cnt;
		}
		int px = pg.get(x, y);
		// return if pixel is white (-1)
		if (px == -1){
			return cnt;
		} else {
			// set pixel to white after it was counted to prevent recounting
			cnt ++;
			pg.set(x,y,-1);
		}
		
		// test all 8 possible neighbor pixels
		pixCnt_2(x+1, y, cnt);
		pixCnt_2(x,   y+1, cnt);
		pixCnt_2(x+1, y+1, cnt);
		pixCnt_2(x-1, y, cnt);
		pixCnt_2(x,   y-1, cnt);
		pixCnt_2(x-1, y-1, cnt);
		
		pixCnt_2(x+1, y-1, cnt);
		pixCnt_2(x-1, y+1, cnt);
		return cnt;
		
	}
	

	
	/*
	 * Recursive random shape pixel counter
	 * 
	 * @param x x - coordinate of the first found pixel in shape - non-white pixel
	 * @param y y - coordinate of the first found pixel in shape - non-white pixel  
	 * @return  void, counter is an instance variable of enclosed class 
	 */
	public void pixCnt(int x, int y){
		// return if we are reaching edges of the picture
		if( (x<0 || x>=pg.getW())  || (y<0 || y>=pg.getH())){
			return;
		}
		int px = pg.get(x, y);
		// return if pixel is white (-1)
		if (px == -1){
			return;
		} else {
			// set pixel to white after it was counted to prevent recounting
			cnt ++;
			pg.set(x,y,-1);
		}
		
		// test all 8 possible neighbor pixels
		pixCnt(x+1, y);
		pixCnt(x,   y+1);
		pixCnt(x+1, y+1);
		pixCnt(x-1, y);
		pixCnt(x,   y-1);
		pixCnt(x-1, y-1);
		
		pixCnt(x+1, y-1);
		pixCnt(x-1, y+1);
		
	}

}


class PicInfo {
	int w;
	int h;
	int [] arr;
	
	public  PicInfo(int[] pxa, int ww, int hh){
		arr = pxa;
		w = ww;
		h = hh;
	}
	
	public int get(int x, int y){
		return arr[x+y*w];
	}

	public int set(int x, int y, int cc){
		return arr[x+y*w] = cc;
	}	
	
	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}


}
