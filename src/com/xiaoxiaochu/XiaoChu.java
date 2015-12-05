package com.xiaoxiaochu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class XiaoChu {

	private Paint  myPaint ;//画笔类  来实现给图片上色  主要是控制图片的灰暗程度
	private Bitmap myBitMap;//即将要画的图片  会在界面上呈现出来
	private Point  myPoint;//图片的位置
	private int drawableId ;
	private boolean isWillToTown=false;
	//用来判断是否需要消除 主要在消除后有生成的新的
	private  boolean isNeedToClear=false;
	public boolean isNeedToClear() {
		return isNeedToClear;
	}
	public void setNeedToClear(boolean isNeedToClear) {
		this.isNeedToClear = isNeedToClear;
	}
	public boolean isWillToTown() {
		return isWillToTown;
	}
	public void setWillToTown(boolean isWillToTown) {
		this.isWillToTown = isWillToTown;
	}
    //在消除的时候使用
	private  int row;//记录消除的   行
	private  int col;//记录消除的列
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	public  XiaoChu(){
		
	}
	public XiaoChu(Paint myPaint, Bitmap myBitMap,Point  myPoint)  {
		super();
		this.myPaint = myPaint;
		this.myBitMap = myBitMap;
		this.myPoint=myPoint;
	}
	
	public Paint getMyPaint() {
		return myPaint;
	}
	public void setMyPaint(Paint myPaint) {
		this.myPaint = myPaint;
	}
	public Bitmap getMyBitMap() {
		return myBitMap;
	}
	public void setMyBitMap(Bitmap myBitMap) {
		this.myBitMap = myBitMap;
	}
	
	public Point getMyPoint() {
		return myPoint;
	}

	public void setMyPoint(Point myPoint) {
		this.myPoint = myPoint;
	}

	public void draw(Canvas canvas){
		
		//把图片显示在界面上面
		if(myBitMap!=null&&myPoint!=null)
		canvas.drawBitmap(myBitMap, myPoint.x, myPoint.y, myPaint);
		
	}
	
}
