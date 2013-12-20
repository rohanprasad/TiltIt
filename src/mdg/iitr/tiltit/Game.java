package mdg.iitr.tiltit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Game extends View {

	private Bitmap Hand;
	private Bitmap Plate;
	private Paint Score_paint;
	private Paint rod_paint;
	private int scr_w;
	private int scr_h;
	private float tilts;
	
	private float hl;
	private float ht;
	
	public Game(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		Score_paint = new Paint();
		Score_paint.setAntiAlias(true);
		Score_paint.setStyle(Paint.Style.FILL);
		Score_paint.setTextSize(30);
		
		rod_paint = new Paint();
		rod_paint.setColor(Color.DKGRAY);
		rod_paint.setAntiAlias(true);
		
		
		Hand = BitmapFactory.decodeResource(getResources(), R.drawable.hand);
		Plate = BitmapFactory.decodeResource(getResources(), R.drawable.plate);
		
		hl=(float) (scr_w-Hand.getWidth())/2;
		ht=(float) scr_h-Hand.getHeight();
		
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		tilts = Globals.x_tilt;
		String show = "" + tilts;
		System.out.println("Tilts :" + tilts);
		canvas.drawText("SCORE: ", (float)0.1*scr_w, 40f, Score_paint);
		canvas.drawText(show, 100f, 100f, Score_paint);
		canvas.drawBitmap(Hand,hl,ht, null);
		canvas.drawRect((float) ((scr_w-Hand.getWidth())/2 + (scr_w*0.02)),	// left 
						(float) (scr_h*0.3),								// top 
						(float) ((scr_w-Hand.getWidth())/2 + (scr_w*0.05)),	// right
						(float) (scr_h-Hand.getHeight()),					// bottom 
						rod_paint);
		canvas.drawBitmap(Plate, 
						(float) ((scr_w-Hand.getWidth())/2 + (scr_w*0.035)) - Plate.getWidth()/2,//(float) ((scr_w-Hand.getWidth())/2 - (scr_w*0.1))/2, 
						(float) (scr_h*0.3) - Plate.getHeight(),//(float) ((scr_h-Hand.getHeight()) + (scr_h*0.5)) - Plate.getHeight(),
						null);
	}



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		scr_w = w;
		scr_h = h;
		hl=(float) (scr_w-Hand.getWidth())/2;
		ht=(float) scr_h-Hand.getHeight();
	}
	
	public void sensors()
	{
		invalidate();
	}

}
