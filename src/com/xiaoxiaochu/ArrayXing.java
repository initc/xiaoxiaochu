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
 * 这个类是星星的数组集合 用来存储所有即将要显示的星星的集合
 * 
 * @author xiaojie
 *
 */
public class ArrayXing {
	// 用来存储星星的集合
	private XiaoChu[][] arrayXing;
	// 消除星星的行数
	private int xingX;
	// 消除星星的列数
	private int xingY;
	// 存放我所有的图片素材
	private List<Integer> drawableList;
	// 随机数生成 用来生成随机的图片素材
	private Random random;
	// 上下文变量 这里用来获取的我的素材集
	private Context context;
	// 图片的大小 用来显示图片
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

	// 初始化星星
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
		// 初始化动作
		initDrawable();
		for (int x = 0; x < xingX; x++)
			for (int y = 0; y < xingY; y++) {

				// 获取素材的数量
				int size = drawableList.size();
				if (size > 0) {
					// 仍然有素材 可以继续初始化
					// 随机获取素材
					XiaoChu xing = new XiaoChu();
					arrayXing[x][y] = xing;
					int id = random.nextInt(size);
					Bitmap map = BitmapFactory.decodeResource(
							context.getResources(), drawableList.get(id));
					xing.setDrawableId(drawableList.get(id));
					xing.setMyBitMap(map);
					xing.setMyPoint(new Point(offsetX + y * pic_size, offsetY
							+ x * pic_size));
					xing.setMyPaint(null);// 初始化時不需要進行灰度變化
					xing.setRow(x);
					xing.setCol(y);
					if (judgeMapIsLine(x, y)) {
						y -= 1;
						drawableList.remove(id);
					} else {

						initDrawable();
					}

				} else {
					// 没有素材可以初始化了
					// 说明素材被使用完之前 仍然不能够进行初始化
					// 说明初始化时有死图产生了 便需要重新初始化
					arrayXing = new XiaoChu[xingX][xingY];
					this.initXing();
					return;// 避免深度递归调用

				}

			}

	}

	/**
	 * 繪製細節
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
	 * 用來判斷是否可以連成一條線 只需判斷三個就可以了 一共有六種情況
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
	 *            数组的x坐标
	 * @param y
	 *            数组的y坐标
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
		// 交换当前点击的图片和之前点击的图片
		// 条件是他两是相连的
		if (preSetDrawableX == -1 || preSetDrawableY == -1) {
			// 当之前根本没有点击图片的时候 啥事都不做

		} else if (isNear(new Point(x, y), new Point(preSetDrawableX,
				preSetDrawableY))) {
			// 改变了分组
			isChange = true;
			// 存储当前的点坐标
			preChangeX = preSetDrawableX;
			preChangeY = preSetDrawableY;
			changeXing(arrayXing[x][y],
					arrayXing[preSetDrawableX][preSetDrawableY]);
		} else {
			// 如果没有改变分组 就把isChange设为false
			isChange = false;
		}
		preSetDrawableX = x;
		preSetDrawableY = y;

	}

	/**
	 * 改变两个XiaoChu类 只需改变两个图片和id就行了
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
			// x轴(列轴)相等时只需判断y轴相连即可

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
	 * 判断x y四周是否可以连成一条直线
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
	 * 对origin右边的图片进行判断 看是否是相同的图片
	 * 
	 * @param origin
	 * @return 返回与origin右边相同图片的数量
	 */
	private int isRightInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// 首先判断是否越界 并且在边界处也认为是不可在进行匹配的
		if (y >= xingY - 1 || y < 0)
			return 0;
		if (isCommon(origin, arrayXing[x][y + 1]))
			result += 1;
		else
			return 0;
		// 递归调用
		return result + isRightInColor(arrayXing[x][y + 1]);

	}

	/**
	 * 对origin左边的图片进行判断 看是否是相同的图片
	 * 
	 * @param origin
	 * @return 返回与origin左边相同图片的数量
	 */
	private int isLeftInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// 首先判断是否越界 并且在边界处也认为是不可在进行匹配的、
		// 只有在最左边的时候是不允许的
		// 在最右边的时候是允许向左移动
		if (y > xingY - 1 || y <= 0)
			return 0;
		if (isCommon(origin, arrayXing[x][y - 1]))
			result += 1;
		else
			return 0;
		// 递归调用
		return result + isLeftInColor(arrayXing[x][y - 1]);

	}

	/**
	 * 对origin上边的图片进行判断 看是否是相同的图片
	 * 
	 * @param origin
	 * @return 返回与origin上边相同图片的数量
	 */
	private int isUpInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// 首先判断是否越界 并且在边界处也认为是不可在进行匹配的
		// 只有在最上面的时候向上移动是不用许的 需要检测
		if (x > xingX - 1 || x <= 0)
			return 0;
		if (isCommon(origin, arrayXing[x - 1][y]))
			result += 1;
		else
			return 0;
		// 递归调用
		return result + isUpInColor(arrayXing[x - 1][y]);

	}

	/**
	 * 对origin下边的图片进行判断 看是否是相同的图片
	 * 
	 * @param origin
	 * @return 返回与origin下边相同图片的数量
	 */
	private int isDownInColor(XiaoChu origin) {
		int result = 0;
		int x = origin.getRow();
		int y = origin.getCol();
		// 首先判断是否越界 并且在边界处也认为是不可在进行匹配的
		// 在往下匹配的时候需要注意
		// 此时就算点在最上边缘时 也需要向下判断
		if (x >= xingX - 1 || x < 0)
			return 0;
		if (isCommon(origin, arrayXing[x + 1][y]))
			result += 1;
		else
			return 0;
		// 递归调用
		return result + isDownInColor(arrayXing[x + 1][y]);

	}

	private boolean isCommon(XiaoChu a, XiaoChu b) {

		return a.getDrawableId() == b.getDrawableId();

	}

	public void reverseChange() {
		if (!isChange)
			return;
		isChange = false;
		// preCahngeX 是之前的点
		// preSetDrawable是当前的点
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
     * 在移动两个或者多个星星的时候
     * 在图上我们已经寻找到了多个相连的星星了  
     * 所以我们需要去把那些给消除去  具体的就是使用即将消除的那个星星的上面来代替他
     * 注意：掉下来的那个些星星可能破坏图的稳健性了  所以我们需要再次进行判断了
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
						//因为被移动下来了  所以很可能需要被消除  所以需要进行判断
						arrayXing[x + count][y].setNeedToClear(true);
						a.setMyBitMap(null);
						a.setWillToTown(true);
					}

				}
			}

		}
		//在补白星星的时候记得可能有三个以上的星星可能是刚刚移下去的那个  所以那个可需要判断的
		setDownNewXing();   
		
	}
/**
 * 
 * 
 * 用来补全所有空白的星星
 * 主要是由==null来判断的
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
				// 获取素材的数量
				int size = drawableList.size();
					// 仍然有素材 可以继续初始化
					// 随机获取素材
					XiaoChu xing = new XiaoChu();
					arrayXing[x][y] = xing;
					int id = random.nextInt(size);
					Bitmap map = BitmapFactory.decodeResource(
							context.getResources(), drawableList.get(id));
					xing.setDrawableId(drawableList.get(id));
					xing.setMyBitMap(map);
					xing.setMyPoint(new Point(offsetX + y * pic_size, offsetY
							+ x * pic_size));
					xing.setMyPaint(null);// 初始化時不需要進行灰度變化
					xing.setRow(x);
					xing.setCol(y);
                    xing.setNeedToClear(true);
                    
	}
		handler.sendEmptyMessageDelayed(0x99, 500);
	}
	public  void  JudgeIfHaveEqual(){
		//当每一次点击消除后会有新的星星上去  这是我们需要进行判断是否有重的三个以上的星星
		//如果有 重复的星星的话  我们需要去进行消除它 然后在适当的时机去填充它
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
