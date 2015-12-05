package com.xiaoxiaochu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;

/**
 * ����������ǵ����鼯�� �����洢���м���Ҫ��ʾ�����ǵļ���
 * 
 * @author xiaojie
 *
 */
public class ArrayXing {
	// �����洢���ǵļ���
	private XiaoChu[][] arrayXing;
	// �������ǵ�����
	private int xingX;
	// �������ǵ�����
	private int xingY;
	// ��������е�ͼƬ�ز�
	private List<Integer> drawableList;
	// ��������� �������������ͼƬ�ز�
	private Random random;
	// �����ı��� ����������ȡ���ҵ��زļ�
	private Context context;
	// ͼƬ�Ĵ�С ������ʾͼƬ
	private static int pic_size = 124;
	private int preChangeX;
    private  Handler handler ;
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getPreChangeX() {
		return preChangeX;
	}

	public void setPreChangeX(int preChangeX) {
		this.preChangeX = preChangeX;
	}

	public int getPreChangeY() {
		return preChangeY;
	}

	public void setPreChangeY(int preChangeY) {
		this.preChangeY = preChangeY;
	}

	private int preChangeY;
	private boolean isChange = false;

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	private Set<XiaoChu> removeXing;
	private static int offsetX = 35;
	private static int offsetY = 600;
	private boolean isInit = false;
	private int preSetDrawableX = -1;
	private int preSetDrawableY = -1;

	public static int getPic_size() {
		return pic_size;
	}

	public static void setPic_size(int pic_size) {
		ArrayXing.pic_size = pic_size;
	}

	public static int getOffsetX() {
		return offsetX;
	}

	public static void setOffsetX(int offsetX) {
		ArrayXing.offsetX = offsetX;
	}

	public static int getOffsetY() {
		return offsetY;
	}

	public static void setOffsetY(int offsetY) {
		ArrayXing.offsetY = offsetY;
	}

	// ��ʼ������
	public ArrayXing(int xingX, int xingY, Context context) {

		this.xingX = xingX;
		this.xingY = xingY;
		this.context = context;
		this.random = new Random();
		this.drawableList = new ArrayList<Integer>();
		removeXing = new HashSet<XiaoChu>();
		arrayXing = new XiaoChu[xingX][xingY];
		this.initXing();

	}

	private void initXing() {
		isInit = true;
		// ��ʼ������
		initDrawable();
		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {

				// ��ȡ�زĵ�����
				int size = drawableList.size();
				if (size > 0) {
					// ��Ȼ���ز� ���Լ�����ʼ��
					// �����ȡ�ز�
					XiaoChu xing = new XiaoChu();
					arrayXing[x][y] = xing;
					int id = random.nextInt(size);
					Bitmap map = BitmapFactory.decodeResource(
							context.getResources(), drawableList.get(id));
					xing.setDrawableId(drawableList.get(id));
					xing.setMyBitMap(map);
					xing.setMyPoint(new Point(offsetX + y * pic_size, offsetY
							+ x * pic_size));
					xing.setMyPaint(null);// ��ʼ���r����Ҫ�M�лҶ�׃��
					xing.setRow(x);
					xing.setCol(y);
					if (judgeMapIsLine(x, y)) {
						y -= 1;
						drawableList.remove(id);
					} else {

						initDrawable();
					}

				} else {
					// û���زĿ��Գ�ʼ����
					// ˵���زı�ʹ����֮ǰ ��Ȼ���ܹ����г�ʼ��
					// ˵����ʼ��ʱ����ͼ������ ����Ҫ���³�ʼ��
					arrayXing = new XiaoChu[xingX][xingY];
					this.initXing();
					return;// ������ȵݹ����

				}

			}

	}

	/**
	 * �L�u����
	 * 
	 * @param canvas
	 */
	public void drawChild(Canvas canvas) {

		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {
				if(arrayXing[x][y]!=null)
				arrayXing[x][y].draw(canvas);
			}
	}

	/**
	 * �Á��Д��Ƿ�����B��һ�l�� ֻ���Д������Ϳ����� һ�������N��r
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean judgeMapIsLine(int x, int y) {
		boolean isLeft_two = y - 2 > -1;// 0 1 0
		boolean isRight_two = y + 1 < xingY;// 0 1 0
		boolean isLeft_three = y - 2 > -1;
		boolean isRight_three = y + 2 < xingY;// 1 0 0 :
		boolean isUp_one = x + 2 < xingX; // 1 0 0 x
		boolean isUp_two = x - 1 > -1;// 0 1 0 x
		boolean isDown_two = x + 1 < xingX;// 0 1 0 x
		boolean isUp_three = x - 2 > -1; // 0 0 1
		if (isRight_three) {// 1 0 0
			if (judgeMap_ThreeXingInLine(arrayXing[x][y], arrayXing[x][y + 1],
					arrayXing[x][y + 2]))
				return true;
		}
		// 0 1 0
		if (isLeft_two && isRight_two) {
			if (judgeMap_ThreeXingInLine(arrayXing[x][y - 1], arrayXing[x][y],
					arrayXing[x][y + 1])) {
				return true;
			}
		}
		// 0 0 1
		if (isLeft_three) {
			if (judgeMap_ThreeXingInLine(arrayXing[x][y - 2],
					arrayXing[x][y - 1], arrayXing[x][y])) {
				return true;
			}

		}
		// 1 0 0
		if (isUp_one) {

			if (judgeMap_ThreeXingInLine(arrayXing[x][y], arrayXing[x + 1][y],
					arrayXing[x + 2][y])) {

				return true;
			}
		}
		// 0 1 0
		if (isUp_two && isDown_two) {
			if (judgeMap_ThreeXingInLine(arrayXing[x - 1][y], arrayXing[x][y],
					arrayXing[x + 1][y])) {

				return true;
			}
		}
		// 0 0 1
		if (isUp_three) {
			if (judgeMap_ThreeXingInLine(arrayXing[x - 2][y],
					arrayXing[x - 1][y], arrayXing[x][y])) {

				return true;
			}

		}

		return false;
	}

	private boolean judgeMap_ThreeXingInLine(XiaoChu a, XiaoChu b, XiaoChu c) {
		if (a != null && b != null && c != null) {

			return a.getDrawableId() == b.getDrawableId()
					&& a.getDrawableId() == c.getDrawableId();

		}

		return false;
	}

	private void initDrawable() {

		drawableList.add(R.drawable.a1);
		drawableList.add(R.drawable.a2);
		drawableList.add(R.drawable.a3);
		drawableList.add(R.drawable.a4);
		drawableList.add(R.drawable.a5);
		drawableList.add(R.drawable.a6);
	}

	/**
	 * 
	 * @param x
	 *            �����x����
	 * @param y
	 *            �����y����
	 */
	public void setDrawableAlpha(int x, int y) {
		if (x < 0 || y < 0)
			return;
		if (x >= xingX || y >= xingY)
			return;
		if (x == preSetDrawableX && y == preSetDrawableY)
			return;
		Paint paint = new Paint();
		paint.setAlpha(150);
		arrayXing[x][y].setMyPaint(paint);
		if (preSetDrawableX == -1 || preSetDrawableY == -1)
			;
		else {
			arrayXing[preSetDrawableX][preSetDrawableY].setMyPaint(null);
		}
		// ������ǰ�����ͼƬ��֮ǰ�����ͼƬ
		// ������������������
		if (preSetDrawableX == -1 || preSetDrawableY == -1) {
			// ��֮ǰ����û�е��ͼƬ��ʱ�� ɶ�¶�����

		} else if (isNear(new Point(x, y), new Point(preSetDrawableX,
				preSetDrawableY))) {
			// �ı��˷���
			isChange = true;
			// �洢��ǰ�ĵ�����
			preChangeX = preSetDrawableX;
			preChangeY = preSetDrawableY;
			changeXing(arrayXing[x][y],
					arrayXing[preSetDrawableX][preSetDrawableY]);
		} else {
			// ���û�иı���� �Ͱ�isChange��Ϊfalse
			isChange = false;
		}
		preSetDrawableX = x;
		preSetDrawableY = y;

	}

	/**
	 * �ı�����XiaoChu�� ֻ��ı�����ͼƬ��id������
	 * 
	 * @param a
	 * @param b
	 */
	private void changeXing(XiaoChu a, XiaoChu b) {
		Bitmap amap = a.getMyBitMap();
		Bitmap bmap = b.getMyBitMap();
		int aid = a.getDrawableId();
		int bid = b.getDrawableId();
		a.setMyBitMap(bmap);
		a.setDrawableId(bid);
		b.setMyBitMap(amap);
		b.setDrawableId(aid);
	}

	public boolean isNear(Point a, Point b) {
		if (a.x == -1 || a.y == -1 || b.x == -1 || b.y == -1)
			return false;
		if (a.x == b.x) {
			// x��(����)���ʱֻ���ж�y����������

			if (Math.abs(a.y - b.y) == 1)
				return true;
		}
		if (a.y == b.y) {
			if (Math.abs(a.x - b.x) == 1)
				return true;

		}

		return false;
	}

	/**
	 * 
	 * �ж�x y�����Ƿ��������һ��ֱ��
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInLineOverThree(int x, int y) {
		if (x < 0 || y < 0)
			return false;
		if (x >= xingX || y >= xingY)
			return false;
		int rightCount = isRightInColor(arrayXing[x][y]);
		int leftCount = isLeftInColor(arrayXing[x][y]);
		int upCount = isUpInColor(arrayXing[x][y]);
		int downCount = isDownInColor(arrayXing[x][y]);
		boolean isOk = false;
		if ((rightCount + leftCount + 1) >= 3) {
			isOk = true;
			removeXing.add(arrayXing[x][y]);
			for (int i = 1; i <= rightCount; i++) {
				removeXing.add(arrayXing[x][y + i]);
			}
			for (int i = 1; i <= leftCount; i++) {
				removeXing.add(arrayXing[x][y - i]);
			}
		}
		if ((upCount + downCount + 1) >= 3) {
			isOk = true;
			removeXing.add(arrayXing[x][y]);
			for (int i = 1; i <= upCount; i++) {
				removeXing.add(arrayXing[x - i][y]);
			}
			for (int i = 1; i <= downCount; i++) {
				removeXing.add(arrayXing[x + i][y]);
			}
		}

		return isOk;
	}

	/**
	 * ��origin�ұߵ�ͼƬ�����ж� ���Ƿ�����ͬ��ͼƬ
	 * 
	 * @param origin
	 * @return ������origin�ұ���ͬͼƬ������
	 */
	private int isRightInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// �����ж��Ƿ�Խ�� �����ڱ߽紦Ҳ��Ϊ�ǲ����ڽ���ƥ���
		if (y >= xingY - 1 || y < 0)
			return 0;
		if (isCommon(origin, arrayXing[x][y + 1]))
			result += 1;
		else
			return 0;
		// �ݹ����
		return result + isRightInColor(arrayXing[x][y + 1]);

	}

	/**
	 * ��origin��ߵ�ͼƬ�����ж� ���Ƿ�����ͬ��ͼƬ
	 * 
	 * @param origin
	 * @return ������origin�����ͬͼƬ������
	 */
	private int isLeftInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// �����ж��Ƿ�Խ�� �����ڱ߽紦Ҳ��Ϊ�ǲ����ڽ���ƥ��ġ�
		// ֻ��������ߵ�ʱ���ǲ������
		// �����ұߵ�ʱ�������������ƶ�
		if (y > xingY - 1 || y <= 0)
			return 0;
		if (isCommon(origin, arrayXing[x][y - 1]))
			result += 1;
		else
			return 0;
		// �ݹ����
		return result + isLeftInColor(arrayXing[x][y - 1]);

	}

	/**
	 * ��origin�ϱߵ�ͼƬ�����ж� ���Ƿ�����ͬ��ͼƬ
	 * 
	 * @param origin
	 * @return ������origin�ϱ���ͬͼƬ������
	 */
	private int isUpInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// �����ж��Ƿ�Խ�� �����ڱ߽紦Ҳ��Ϊ�ǲ����ڽ���ƥ���
		// ֻ�����������ʱ�������ƶ��ǲ������ ��Ҫ���
		if (x > xingX - 1 || x <= 0)
			return 0;
		if (isCommon(origin, arrayXing[x - 1][y]))
			result += 1;
		else
			return 0;
		// �ݹ����
		return result + isUpInColor(arrayXing[x - 1][y]);

	}

	/**
	 * ��origin�±ߵ�ͼƬ�����ж� ���Ƿ�����ͬ��ͼƬ
	 * 
	 * @param origin
	 * @return ������origin�±���ͬͼƬ������
	 */
	private int isDownInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// �����ж��Ƿ�Խ�� �����ڱ߽紦Ҳ��Ϊ�ǲ����ڽ���ƥ���
		// ������ƥ���ʱ����Ҫע��
		// ��ʱ����������ϱ�Եʱ Ҳ��Ҫ�����ж�
		if (x >= xingX - 1 || x < 0)
			return 0;
		if (isCommon(origin, arrayXing[x + 1][y]))
			result += 1;
		else
			return 0;
		// �ݹ����
		return result + isDownInColor(arrayXing[x + 1][y]);

	}

	private boolean isCommon(XiaoChu a, XiaoChu b) {

		return a.getDrawableId() == b.getDrawableId();

	}

	public void reverseChange() {
		if (!isChange)
			return;
		isChange = false;
		// preCahngeX ��֮ǰ�ĵ�
		// preSetDrawable�ǵ�ǰ�ĵ�
		changeXing(arrayXing[preChangeX][preChangeY],
				arrayXing[preSetDrawableX][preSetDrawableY]);
	}

	public void setBeforeClear() {
		if (removeXing == null || removeXing.size() < 1)
			return;
		Iterator<XiaoChu> lists = removeXing.iterator();
		while (lists.hasNext()) {
			XiaoChu x = lists.next();
			x.setMyBitMap(null);
		}

	}
    /**
     * ���ƶ��������߶�����ǵ�ʱ��
     * ��ͼ�������Ѿ�Ѱ�ҵ��˶��������������  
     * ����������Ҫȥ����Щ������ȥ  ����ľ���ʹ�ü����������Ǹ����ǵ�������������
     * ע�⣺���������Ǹ�Щ���ǿ����ƻ�ͼ���Ƚ�����  ����������Ҫ�ٴν����ж���
     */
	public void setMove() {

		int indexY = xingY - 1;
		int indexX = xingX - 1;
		for (int y = indexY; y > -1; y--) {
			int count = 0;
			for (int x = indexX; x > -1; x--) {

				if (removeXing.contains(arrayXing[x][y])) {

					count++;
					removeXing.remove(arrayXing[x][y]);
					arrayXing[x][y].setWillToTown(true);
				} else {

					if (x + count != x) {
						XiaoChu a = arrayXing[x][y];
						arrayXing[x + count][y].setMyBitMap(a.getMyBitMap());
						arrayXing[x + count][y]
								.setDrawableId(a.getDrawableId());
						
						arrayXing[x + count][y].setWillToTown(false);
						//��Ϊ���ƶ�������  ���Ժܿ�����Ҫ������  ������Ҫ�����ж�
						arrayXing[x + count][y].setNeedToClear(true);
						a.setMyBitMap(null);
						a.setWillToTown(true);
					}

				}
			}

		}
		//�ڲ������ǵ�ʱ��ǵÿ������������ϵ����ǿ����Ǹո�����ȥ���Ǹ�  �����Ǹ�����Ҫ�жϵ�
		setDownNewXing();   
		
	}
/**
 * 
 * 
 * ������ȫ���пհ׵�����
 * ��Ҫ����==null���жϵ�
 */
	public void setDownNewXing() {
		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {
				XiaoChu xingOne = arrayXing[x][y];
				if (xingOne.isWillToTown())
					arrayXing[x][y] = null;

			}
		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {
                if(arrayXing[x][y]!=null)
                	continue;
				// ��ȡ�زĵ�����
				int size = drawableList.size();
					// ��Ȼ���ز� ���Լ�����ʼ��
					// �����ȡ�ز�
					XiaoChu xing = new XiaoChu();
					arrayXing[x][y] = xing;
					int id = random.nextInt(size);
					Bitmap map = BitmapFactory.decodeResource(
							context.getResources(), drawableList.get(id));
					xing.setDrawableId(drawableList.get(id));
					xing.setMyBitMap(map);
					xing.setMyPoint(new Point(offsetX + y * pic_size, offsetY
							+ x * pic_size));
					xing.setMyPaint(null);// ��ʼ���r����Ҫ�M�лҶ�׃��
					xing.setRow(x);
					xing.setCol(y);
                    xing.setNeedToClear(true);
                    
	}
		handler.sendEmptyMessageDelayed(0x99, 500);
	}
	public  void  JudgeIfHaveEqual(){
		//��ÿһ�ε������������µ�������ȥ  ����������Ҫ�����ж��Ƿ����ص��������ϵ�����
		//����� �ظ������ǵĻ�  ������Ҫȥ���������� Ȼ�����ʵ���ʱ��ȥ�����
		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {
				XiaoChu xingOne = arrayXing[x][y];
				if (!xingOne.isNeedToClear())
					continue;
                xingOne.setNeedToClear(false);
                isInLineOverThree(x,y);
			}
		if(removeXing.size()>0){
			
			 setMove();
		     setChange(false);
		     
		}
		
	}
	
	
}
