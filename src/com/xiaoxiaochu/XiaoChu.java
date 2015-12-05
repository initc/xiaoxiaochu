package com.xiaoxiaochu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class XiaoChu {

	private Paint  myPaint ;//������  ��ʵ�ָ�ͼƬ��ɫ  ��Ҫ�ǿ���ͼƬ�ĻҰ��̶�
	private Bitmap myBitMap;//����Ҫ����ͼƬ  ���ڽ����ϳ��ֳ���
	private Point  myPoint;//ͼƬ��λ��
	private int drawableId ;
	private boolean isWillToTown=false;
	//�����ж��Ƿ���Ҫ���� ��Ҫ�������������ɵ��µ�
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
    //��������ʱ��ʹ��
	private  int row;//��¼������   ��
	private  int col;//��¼��������
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
		
		//��ͼƬ��ʾ�ڽ�������
		if(myBitMap!=null&&myPoint!=null)
		canvas.drawBitmap(myBitMap, myPoint.x, myPoint.y, myPaint);
		
	}
	
}
