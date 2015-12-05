package com.xiaoxiaochu;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class XiaoChuView extends View {
	private ArrayXing arrayXing;
	private boolean isInit = false;
	private Context context;
	private int pic_size;
	private int offset_x;
	private int offset_y;
	private int index_x = -1;
	private int index_y = -1;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// 延迟0.2秒跟新
			if (msg.what == 0x88) {
				if (arrayXing.isChange()) {
					if (arrayXing.isInLineOverThree(index_x, index_y)
							| arrayXing.isInLineOverThree(
									arrayXing.getPreChangeX(),
									arrayXing.getPreChangeY())) {
						// 如果有三个以上的相同 就进行消除吧
						// 主逻辑实现
						
						//arrayXing.setBeforeClear();
                        arrayXing.setMove();
						arrayXing.setChange(false);
						
					} else {
						arrayXing.reverseChange();
					}

				}

				XiaoChuView.this.invalidate();

			}

			if(msg.what==0x99){
				
				arrayXing.JudgeIfHaveEqual();
				XiaoChuView.this.invalidate();
				
			}
			
		}

	};

	/*
	 * public XiaoChuView(Context context, AttributeSet attrs, int defStyleAttr)
	 * { super(context, attrs, defStyleAttr); this.context=context; arrayXing=
	 * new ArrayXing(8, 8, context); offset_x=arrayXing.getOffsetX();
	 * offset_y=arrayXing.getOffsetY(); pic_size=arrayXing.getPic_size(); }
	 */
	public XiaoChuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// super()
		this.context = context;
		arrayXing = new ArrayXing(8, 8, context);
		offset_x = arrayXing.getOffsetX();
		offset_y = arrayXing.getOffsetY();
		pic_size = arrayXing.getPic_size();
		arrayXing.setHandler(handler);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 主要实现代码结构
		// 主要游戏逻辑结构
		super.onDraw(canvas);
		arrayXing.drawChild(canvas);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();
		int true_x = x - offset_x;
		int true_y = y - offset_y;
		int index_y = true_x / pic_size;
		int index_x = true_y / pic_size;
		this.index_x = index_x;
		this.index_y = index_y;
		Log.d("index--------", "--x index ---" + index_x + "--y index---"
				+ index_y);
		arrayXing.setDrawableAlpha(index_x, index_y);
		this.invalidate();
		if (arrayXing.isChange())
			handler.sendEmptyMessageDelayed(0x88, 200);
		return super.onTouchEvent(event);
	}

}
