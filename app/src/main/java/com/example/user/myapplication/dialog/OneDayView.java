package com.example.user.myapplication.dialog;
import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.myapplication.R;

/**
 * Created by JiHoon on 2017-08-15.
 */

public class OneDayView extends RelativeLayout{

    private static final String NAME = "OneDayView";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode()); //왜 하는짓?

    private TextView dayTv;
    private TextView msgTv;
    private ImageView weatherIv;
    private OneDayData one;

    public OneDayView(Context context){
        super(context);
        init(context);
    }

    public  OneDayView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context) { //만든 xml 파일 들의 instance 들 가져오기

        View v = View.inflate(context, R.layout.onday,this);

        dayTv = (TextView) v.findViewById(R.id.onday_dayTv);
        weatherIv = (ImageView) v.findViewById(R.id.onday_weatherIv);
        msgTv = (TextView) v.findViewById(R.id.onday_msgTv);
        one = new OneDayData();
    }

    public void setDay(int year , int month , int day){
        this.one.cal.set(year,month,day); // 년 , 달 ,일 정보 지정
    }

    public void setDay(Calendar cal){
        this.one.setDay((Calendar) cal.clone());
    }

    public  OneDayData getDay(){
        return one;
    }

    public void setMsg(String msg){ // 보여줄 메시지를 one class 안에다 지정
        one.setMsg(msg);
    }

    public CharSequence getMsg(){ // 보여줄 메시지를 리턴시킴
        return one.getMsg()
    }
    /**
     * Returns the value of the given field after computing the field values by
     * calling {@code complete()} first.
     *
     * @param field Calendar.YEAR or Calendar.MONTH or Calendar.DAY_OF_MONTH
     *
     * @throws IllegalArgumentException
     *                if the fields are not set, the time is not set, and the
     *                time cannot be computed from the current field values.
     * @throws ArrayIndexOutOfBoundsException
     *                if the field is not inside the range of possible fields.
     *                The range is starting at 0 up to {@code FIELD_COUNT}.
     * 혹시 발생할 상황에서의 에러 무시하위한 throws
     */
    public  int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException{
        return one.get(field);
    }

    public void setWeather(MomusWeather.Weather weather){
        this.one.setWeather(weather); // 날씨 정보 지정
    }

    public void refresh(){
        dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));
        msgTv.setText((one.getMsg()==null)?"":one.getMsg());
        switch(one.weather){
            case CLOUDY:
            case SUN_CLOUND:
                weatherIv.setImageResource(R.drawable.cloudy1);
                break;
            case RAINNY:
                weatherIv.setImageResource(R.drawable.rainy1);
                break;
            case SNOW:
                weatherIv.setImageResource(R.drawable.snowy);
                break;
            case SUNSHINE:
                weatherIv.setImageResource(R.drawable.sunny);
                break;
        }
    }
}
