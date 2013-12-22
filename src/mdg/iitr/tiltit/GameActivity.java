package mdg.iitr.tiltit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements SensorEventListener {

	private SensorManager sensor_m;
	private long last_update;
	private Game my_game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		my_game = new Game(this);
		my_game.setKeepScreenOn(true);
		setContentView(my_game);
		
		sensor_m = (SensorManager) getSystemService(SENSOR_SERVICE);
		last_update = System.currentTimeMillis();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			accelerometer_changed(event);
	}
	
	public void accelerometer_changed(SensorEvent event)
	{
		
		float[] values = event.values;
		
		float x = values[0];
		float y = values[1];
		float z = values[2];		
		
		my_game.sensors();
		
		long t_now = System.currentTimeMillis();
		if(t_now - last_update > 500)
		{
			double result =(x/(Math.sqrt(y*y +z*z)));
			Globals.x_tilt =(float) Math.atan(result);
			last_update = t_now;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		sensor_m.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		sensor_m.registerListener(this, sensor_m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensor_m.SENSOR_DELAY_UI);
	}
	
	
	
}
