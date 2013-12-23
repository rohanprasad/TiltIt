package mdg.iitr.tiltit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

public class Game extends View {

	private Bitmap Hand;
	private Bitmap Plate;
	private Bitmap rod;
	private Paint Score_paint;
	private Paint rod_paint;
	private int scr_w;
	private int scr_h;
	private float angles;
	private int score;
	private Matrix m;
	private boolean playing;
	private boolean r_tilt;
	private float hl;
	private float ht;
	
	private float curr_tilt ;
	
    private SharedPreferences hi_score;
    private SharedPreferences.Editor editors;
    private String names = "h_score";
    private int h_scores;
	
	
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
		rod = BitmapFactory.decodeResource(getResources(), R.drawable.rod); 
		Plate = BitmapFactory.decodeResource(getResources(), R.drawable.plate);
		
		
		hl=(float) (scr_w-Hand.getWidth())/2;
		ht=(float) scr_h-Hand.getHeight();
		
		m = new Matrix();
		
		score = 0;
		
		playing = true;
		
		hi_score = getContext().getSharedPreferences("hi_score", Context.MODE_PRIVATE);
        editors = hi_score.edit();
        
        if(hi_score.contains(names))
        {
        	h_scores = hi_score.getInt(names, 0);
        }
        
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
		if(curr_tilt < 0)
			r_tilt = true;
		else
			r_tilt = false;
		
		if(playing)
		{
			
			if(angles>-20 && angles<20)
			{
				if(r_tilt)
					angles++;
				else
					angles--;
				score++;
			}
			else
			{
				if(angles>0 && angles!=180)
					angles+=10;
				else if(angles!=-180)
					angles-=10;
				if(angles>=180 || angles <=-180)
				{
					playing = false;
					score_update();
				}
			}
				
		}
		
		m.setTranslate((float)((scr_w-Hand.getWidth())/2 + (scr_w*0.03)) , (float)(scr_h*0.325) );	
		m.postRotate(angles,(float)((scr_w-Hand.getWidth())/2 + (scr_w*0.04)),(float)(scr_h*0.325 + rod.getHeight()));
		
		
		String show = " " + score;
		String show2 = " " + h_scores;
		canvas.drawText("SCORE: ", (float)0.1*scr_w, 40f, Score_paint);
		canvas.drawText(show, (float)0.3*scr_w, 40f, Score_paint);
		canvas.drawText("HI-SCORE: ", (float)0.1*scr_w, 80f, Score_paint);
		canvas.drawText(show2, (float)0.4*scr_w, 80f, Score_paint);

		canvas.drawBitmap(Hand,hl,ht, null);
		canvas.drawBitmap(rod, m, null);
		/*canvas.drawBitmap(Plate, 
						(float) ((scr_w-Hand.getWidth())/2 + (scr_w*0.035)) - Plate.getWidth()/2, 
						(float) (scr_h*0.3) - Plate.getHeight(),
						null);*/
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
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		int action = event.getAction();
		
		if(action == MotionEvent.ACTION_UP && playing!=true  )
		{
			playing = true;
			angles = 0;
			score = 0;
		}
		invalidate();
		return true;
	}

	public void score_update()
	{
		if(hi_score.contains(names))
        {
        	if(score >= hi_score.getInt(names, 0))
        	{
        		h_scores = score;
        		editors.remove(names);
        		editors.putInt(names, h_scores);
        	}
        }
		else
			editors.putInt(names, h_scores);

		editors.commit();
	}


	public void sensors()
	{
		curr_tilt = Globals.x_tilt;
		invalidate();
	}

}
