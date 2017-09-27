package com.example.user.myapplication.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.util.Constants;
import com.example.user.myapplication.weather.WeatherCalender;
import com.example.user.myapplication.weather.LatXLngY;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



import org.w3c.dom.Element;

public class WeatherActivity extends AppCompatActivity  implements LocationListener {
    LinearLayout weatherLayout;
   // private WeatherActivity weatherActivity;
   // private final Context mContext;
    Calendar oCalendar = Calendar.getInstance(); //현재 날짜등 정보들 저장
    public int num = oCalendar.get(Calendar.DAY_OF_WEEK)-1;

    boolean isGetLocation = false;
    public static final int TO_GRID = 0;
    public static final int TO_GPS = 1;
    //위치 관련
    LocationManager locationManager;
    Location location ;
    String thoroughfare;
    String placeName;
    String country;
    double lat=0;
     double lon=0;

    //날씨 관련
    Document doc = null;
    TextView weatherParse;
        //금일 날씨
       TextView todayTemp;
       TextView todayReh;
       TextView todayWs;
       TextView todayRain;
       ImageView todayWI;
        //동네 예보
        public String[][] weatherThree = new String[20][4]; // 0 : 오늘or내일or모레 , 1 : 시간 , 2 : 온도 , 3 : 이미지뷰
        public LinearLayout ll;
        public TextView seq0Time;
        public TextView seq0Temp;
        public ImageView seq0IV;

        TextView[] dayTv;
        ImageView[] dayIv;
        TextView[] dayTmx;
        TextView[] dayTmn;
        //중기 예보
        TextView firstDayTv;
        ImageView firstDayIv;
        TextView firstDayTmx;
        TextView firstDayTmn;

        TextView secondDayTv;
        ImageView secondDayIv;
        TextView secondDayTmx;
        TextView secondDayTmn;

        TextView thirdDayTv;
        ImageView thirdDayIv;
        TextView thirdDayTmx;
        TextView thirdDayTmn;

        TextView forthDayTv;
        ImageView forthDayIv;
        TextView forthDayTmx;
        TextView forthDayTmn;

        TextView fifthDayTv;
        ImageView fifthDayIv;
        TextView fifthDayTmx;
        TextView fifthDayTmn;

        TextView sixthDayTv;
        ImageView sixthDayIv;
        TextView sixthDayTmx;
        TextView sixthDayTmn;

        TextView seventhDayTv;
        ImageView seventhDayIv;
        TextView seventhDayTmx;
        TextView seventhDayTmn;



    //private inner class extending AsyncTask
    private class WeatherTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            for(int i = 0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element todElmt = (Element)node;

                if(i==0){ //금일 날씨 설정


                    //이미지 뷰
                    NodeList nameList = todElmt.getElementsByTagName("wfKor");
                    Element nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();

                    todayWI = (ImageView)findViewById(R.id.todayWI);
                    String todayWf = nameList.item(0).getNodeValue();
                    switch (todayWf) { //한국어 날씨를 가지고 비교
                        case "맑음":
                            todayWI.setImageResource(R.drawable.sunny);
                            break;
                        case "구름 조금":
                            todayWI.setImageResource(R.drawable.partialy_cloudy);
                            break;
                        case "구름 많음":
                            todayWI.setImageResource(R.drawable.cloudy_day);
                            break;
                        case "흐림":
                            todayWI.setImageResource(R.drawable.cloudy);
                            break;
                        case "비":
                            todayWI.setImageResource(R.drawable.rainy);
                            break;
                        case "눈/비":
                            todayWI.setImageResource(R.drawable.sleet);
                            break;
                        case "눈":
                            todayWI.setImageResource(R.drawable.snowy);
                            break;
                        default:
                            todayWI.setImageResource(R.mipmap.error);
                            break;
                    }

                    nameList = todElmt.getElementsByTagName("temp");// 데이터 명이 "-"로 걸리는 정보를 찾는다.
                    nameElement = (Element)nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    todayTemp = (TextView)findViewById(R.id.todayTemp);
                    String toTemp = nameList.item(0).getNodeValue();
                    todayTemp.setText(toTemp);
                    todayTemp.append("℃");

                    nameList = todElmt.getElementsByTagName("reh");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    todayReh = (TextView)findViewById(R.id.todayReh);
                    String toReh =nameList.item(0).getNodeValue();
                    todayReh.setText(toReh);
                    todayReh.append("%");

                    nameList = todElmt.getElementsByTagName("ws");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    todayWs = (TextView)findViewById(R.id.todayWs);
                    double toWs = Double.valueOf(nameList.item(0).getNodeValue());
                    toWs = Double.parseDouble(String.format("%.2f",toWs));
                    todayWs.setText(""+toWs);
                    todayWs.append("m/s");

                     if((todayWf=="비")||(todayWf=="눈비")) {
                         nameList = todElmt.getElementsByTagName("r12");
                         nameElement = (Element) nameList.item(0);
                         nameList = nameElement.getChildNodes();
                         todayRain = (TextView) findViewById(R.id.todayRain);
                         int toRain = Integer.parseInt(nameList.item(0).getNodeValue());
                         if((0<=toRain)&&(toRain<0.1)){
                             todayRain.setText("0.1mm미만");
                         }
                         else if((0.1<=toRain)&&(toRain<1)){
                             todayRain.setText("0.1mm 이상 1mm미만");
                         }
                         else if((1<=toRain)&&(toRain<5)){
                             todayRain.setText("1mm 이상 5mm미만");
                         }
                         else if((5<=toRain)&&(toRain<10)){
                             todayRain.setText("5mm 이상 10mm미만");
                         }
                         else if((10<=toRain)&&(toRain<25)){
                             todayRain.setText("10mm 이상 25mm미만");
                         }
                         else if((25<=toRain)&&(toRain<50)){
                             todayRain.setText("25mm 이상 50mm미만");
                         }
                         else{
                             todayRain.setText("50mm 이상");
                         }
                     }
                     else {
                         todayRain = (TextView) findViewById(R.id.todayRain);

                         todayRain.setText("0");
                         todayRain.append("mm");
                     }

                }

                node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;
                NodeList nameList;
                Element nameElement;

                    // weatherTree 에 넣기 (동네 예보 종결)
                    nameList = fstElmnt.getElementsByTagName("day");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    weatherThree[i][0] = nameList.item(0).getNodeValue();


                    nameList = fstElmnt.getElementsByTagName("hour");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    weatherThree[i][1] = nameList.item(0).getNodeValue();



                    nameList = fstElmnt.getElementsByTagName("temp");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    weatherThree[i][2] = nameList.item(0).getNodeValue();

                    nameList = fstElmnt.getElementsByTagName("wfKor");
                    nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    weatherThree[i][3] = nameList.item(0).getNodeValue();




                    if(weatherThree[i][0].equals("1")&&weatherThree[i][1].equals("12")){
                        firstDayTv = (TextView)findViewById(R.id.firstDayTv);
                        firstDayTv.setText(Constants.yoil[(num+1)%7]);
                        nameList = fstElmnt.getElementsByTagName("tmx");
                        firstDayTmx = (TextView)findViewById(R.id.firstDayTmx);
                        firstDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                        nameList = fstElmnt.getElementsByTagName("tmn");
                        firstDayTmn = (TextView)findViewById(R.id.firstDayTmn);
                        firstDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                        firstDayIv = (ImageView)findViewById(R.id.firstDayIv);
                        nameList = fstElmnt.getElementsByTagName("wfKor");
                        switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                            case "맑음":
                                firstDayIv.setImageResource(R.drawable.sunny);
                                break;
                            case "구름 조금":
                                firstDayIv.setImageResource(R.drawable.partialy_cloudy);
                                break;
                            case "구름 많음":
                                firstDayIv.setImageResource(R.drawable.cloudy_day);
                                break;
                            case "흐림":
                                firstDayIv.setImageResource(R.drawable.cloudy);
                                break;
                            case "비":
                                firstDayIv.setImageResource(R.drawable.rainy);
                                break;
                            case "눈/비":
                                firstDayIv.setImageResource(R.drawable.sleet);
                                break;
                            case "눈":
                                firstDayIv.setImageResource(R.drawable.snowy);
                                break;
                            default:
                                firstDayIv.setImageResource(R.mipmap.error);
                                break;
                        }


                    }
                    else if(weatherThree[i][0].equals("2")&&weatherThree[i][1].equals("12")){
                        secondDayTv = (TextView)findViewById(R.id.secondDayTv);
                        secondDayTv.setText(Constants.yoil[(num+2)%7]);
                        nameList = fstElmnt.getElementsByTagName("tmx");
                        secondDayTmx = (TextView)findViewById(R.id.secondDayTmx);
                        secondDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                        nameList = fstElmnt.getElementsByTagName("tmn");
                        secondDayTmn = (TextView)findViewById(R.id.secondDayTmn);
                        secondDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                        secondDayIv = (ImageView)findViewById(R.id.secondDayIv);
                        nameList = fstElmnt.getElementsByTagName("wfKor");
                        switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                            case "맑음":
                                secondDayIv.setImageResource(R.drawable.sunny);
                                break;
                            case "구름 조금":
                                secondDayIv.setImageResource(R.drawable.partialy_cloudy);
                                break;
                            case "구름 많음":
                                secondDayIv.setImageResource(R.drawable.cloudy_day);
                                break;
                            case "흐림":
                                secondDayIv.setImageResource(R.drawable.cloudy);
                                break;
                            case "비":
                                secondDayIv.setImageResource(R.drawable.rainy);
                                break;
                            case "눈/비":
                                secondDayIv.setImageResource(R.drawable.sleet);
                                break;
                            case "눈":
                                secondDayIv.setImageResource(R.drawable.snowy);
                                break;
                            default:
                                secondDayIv.setImageResource(R.mipmap.error);
                                break;
                        }

                    }




            }

            for (int k = 0; k < 15; k++) {      //동네예보 종결자 대입
                ll = new LinearLayout(WeatherActivity.this);
                seq0Time = new TextView(WeatherActivity.this);
                seq0Temp = new TextView(WeatherActivity.this);
                seq0IV = new ImageView(WeatherActivity.this);
                //style 들
                seq0IV.setLayoutParams(new LinearLayout.LayoutParams(125,125));
                seq0Time.setGravity(Gravity.LEFT);
                seq0Time.setTextSize(10);
                seq0Temp.setGravity(Gravity.CENTER_HORIZONTAL);
                seq0Time.setTextColor(0xFF000000);
                seq0Temp.setTextColor(0xFF000000);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setPadding(40,10,40,10);

                switch (weatherThree[k][0]){                    //금일 or 내일 or 모레
                    case "0" : seq0Time.setText("오늘 "); break;
                    case "1" : seq0Time.setText("내일 "); break;
                    case "2" : seq0Time.setText("모레 "); break;
                    default: seq0Time.setText("");break;
                }

                switch (weatherThree[k][1]){// 시간
                    case "24" : seq0Time.append("오전 0시" );; break;
                    case "3" : seq0Time.append("오전 3시"); break;
                    case "6" : seq0Time.append("오전 6시"); break;
                    case "9" : seq0Time.append("오전 9시");; break;
                    case "12" : seq0Time.append("오후 12시"); break;
                    case "15" : seq0Time.append("오후 3시"); break;
                    case "18" : seq0Time.append("오후 6시");; break;
                    case "21" : seq0Time.append("오후 9시"); break;
                    default: seq0Time.setText("에러욤");break;
                }


                seq0Temp.setText(weatherThree[k][2]+"℃");

                switch (weatherThree[k][3]){ //한국어 날씨를 가지고 비교
                    case "맑음":
                        seq0IV.setImageResource(R.drawable.sunny);
                        break;
                    case "구름 조금":
                        seq0IV.setImageResource(R.drawable.partialy_cloudy);
                        break;
                    case "구름 많음":
                        seq0IV.setImageResource(R.drawable.cloudy_day);
                        break;
                    case "흐림":
                        seq0IV.setImageResource(R.drawable.cloudy);
                        break;
                    case "비":
                        seq0IV.setImageResource(R.drawable.rainy);
                        break;
                    case "눈/비":
                        seq0IV.setImageResource(R.drawable.sleet);
                        break;
                    case "눈":
                        seq0IV.setImageResource(R.drawable.snowy);
                        break;
                    default:
                        seq0IV.setImageResource(R.mipmap.error);
                        break;
                }

                ll.addView(seq0Time);
                ll.addView(seq0IV);
                ll.addView(seq0Temp);
                weatherLayout.addView(ll);

            }

            weatherParse.setText(s);

            super.onPostExecute(doc);
        }


    }//end inner class - GetXMLTask

    private class MidleForeTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("location");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0 ; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList nameList;
                Element nameElement;
                nameList = fstElmnt.getElementsByTagName("city");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                String cityName = nameList.item(0).getNodeValue();

                if(cityName.equals("서울")) {     //해당 지역을 찾아야함.
                    NodeList nodeListd = fstElmnt.getElementsByTagName("data");
                    for(int j = 1; j<nodeListd.getLength();j+=2) {
                        Node noded = nodeListd.item(j);
                        Element fstElmntd = (Element) noded;
                        if(j==1) {
                            thirdDayTv = (TextView) findViewById(R.id.thirdDayTv);
                            thirdDayTv.setText(Constants.yoil[(num + 3) % 7]);
                            nameList = fstElmntd.getElementsByTagName("tmx");
                            thirdDayTmx = (TextView) findViewById(R.id.thirdDayTmx);
                            thirdDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            nameList = fstElmntd.getElementsByTagName("tmn");
                            thirdDayTmn = (TextView) findViewById(R.id.thirdDayTmn);
                            thirdDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            thirdDayIv = (ImageView) findViewById(R.id.thirdDayIv);
                            nameList = fstElmntd.getElementsByTagName("wf");
                            switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                                case "맑음":
                                    thirdDayIv.setImageResource(R.drawable.sunny);
                                    break;
                                case "구름조금":
                                    thirdDayIv.setImageResource(R.drawable.partialy_cloudy);
                                    break;
                                case "구름많음":
                                    thirdDayIv.setImageResource(R.drawable.cloudy_day);
                                    break;
                                case "흐림":
                                    thirdDayIv.setImageResource(R.drawable.cloudy);
                                    break;
                                case "구름많고 비":
                                    thirdDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "비":
                                    thirdDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "눈/비":
                                    thirdDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비":
                                    thirdDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "구름많고 비/눈":
                                    thirdDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비/눈":
                                    thirdDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 눈/비":
                                    thirdDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "구름많고 눈/비":
                                    thirdDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "눈":
                                    thirdDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "흐리고 눈":
                                    thirdDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "구름많고 눈":
                                    thirdDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                default:
                                    thirdDayIv.setImageResource(R.mipmap.error);
                                    break;
                            }
                        }
                        if(j==3) {
                            forthDayTv = (TextView) findViewById(R.id.forthDayTv);
                            forthDayTv.setText(Constants.yoil[(num + 4) % 7]);
                            nameList = fstElmntd.getElementsByTagName("tmx");
                            forthDayTmx = (TextView) findViewById(R.id.forthDayTmx);
                            forthDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            nameList = fstElmntd.getElementsByTagName("tmn");
                            forthDayTmn = (TextView) findViewById(R.id.forthDayTmn);
                            forthDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            forthDayIv = (ImageView) findViewById(R.id.forthDayIv);
                            nameList = fstElmntd.getElementsByTagName("wf");
                            switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                                case "맑음":
                                    forthDayIv.setImageResource(R.drawable.sunny);
                                    break;
                                case "구름조금":
                                    forthDayIv.setImageResource(R.drawable.partialy_cloudy);
                                    break;
                                case "구름많음":
                                    forthDayIv.setImageResource(R.drawable.cloudy_day);
                                    break;
                                case "흐림":
                                    forthDayIv.setImageResource(R.drawable.cloudy);
                                    break;
                                case "구름많고 비":
                                    forthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "비":
                                    forthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "눈/비":
                                    forthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비":
                                    forthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "구름많고 비/눈":
                                    forthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비/눈":
                                    forthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 눈/비":
                                    forthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "구름많고 눈/비":
                                    forthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "눈":
                                    forthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "흐리고 눈":
                                    forthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "구름많고 눈":
                                    forthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                default:
                                    forthDayIv.setImageResource(R.mipmap.error);
                                    break;
                            }
                        }
                        if(j==5) {
                            fifthDayTv = (TextView) findViewById(R.id.fifthDayTv);
                            fifthDayTv.setText(Constants.yoil[(num + 5) % 7]);
                            nameList = fstElmntd.getElementsByTagName("tmx");
                            fifthDayTmx = (TextView) findViewById(R.id.fifthDayTmx);
                            fifthDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            nameList = fstElmntd.getElementsByTagName("tmn");
                            fifthDayTmn = (TextView) findViewById(R.id.fifthDayTmn);
                            fifthDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            fifthDayIv = (ImageView) findViewById(R.id.fifthDayIv);
                            nameList = fstElmntd.getElementsByTagName("wf");
                            switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                                case "맑음":
                                    fifthDayIv.setImageResource(R.drawable.sunny);
                                    break;
                                case "구름조금":
                                    fifthDayIv.setImageResource(R.drawable.partialy_cloudy);
                                    break;
                                case "구름많음":
                                    fifthDayIv.setImageResource(R.drawable.cloudy_day);
                                    break;
                                case "흐림":
                                    fifthDayIv.setImageResource(R.drawable.cloudy);
                                    break;
                                case "구름많고 비":
                                    fifthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "비":
                                    fifthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "눈/비":
                                    fifthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비":
                                    fifthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "구름많고 비/눈":
                                    fifthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비/눈":
                                    fifthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 눈/비":
                                    fifthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "구름많고 눈/비":
                                    fifthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "눈":
                                    fifthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "흐리고 눈":
                                    fifthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "구름많고 눈":
                                    fifthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                default:
                                    fifthDayIv.setImageResource(R.mipmap.error);
                                    break;
                            }
                        }
                        if(j==7) {
                            sixthDayTv = (TextView) findViewById(R.id.sixthDayTv);
                            sixthDayTv.setText(Constants.yoil[(num + 6) % 7]);
                            nameList = fstElmntd.getElementsByTagName("tmx");
                            sixthDayTmx = (TextView) findViewById(R.id.sixthDayTmx);
                            sixthDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            nameList = fstElmntd.getElementsByTagName("tmn");
                            sixthDayTmn = (TextView) findViewById(R.id.sixthDayTmn);
                            sixthDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            sixthDayIv = (ImageView) findViewById(R.id.sixthDayIv);
                            nameList = fstElmntd.getElementsByTagName("wf");
                            switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                                case "맑음":
                                    sixthDayIv.setImageResource(R.drawable.sunny);
                                    break;
                                case "구름조금":
                                    sixthDayIv.setImageResource(R.drawable.partialy_cloudy);
                                    break;
                                case "구름많음":
                                    sixthDayIv.setImageResource(R.drawable.cloudy_day);
                                    break;
                                case "흐림":
                                    sixthDayIv.setImageResource(R.drawable.cloudy);
                                    break;
                                case "구름많고 비":
                                    sixthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "비":
                                    sixthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "눈/비":
                                    sixthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비":
                                    sixthDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "구름많고 비/눈":
                                    sixthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비/눈":
                                    sixthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 눈/비":
                                    sixthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "구름많고 눈/비":
                                    sixthDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "눈":
                                    sixthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "흐리고 눈":
                                    sixthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "구름많고 눈":
                                    sixthDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                default:
                                    sixthDayIv.setImageResource(R.mipmap.error);
                                    break;
                            }
                        }
                        if(j==9) {
                            seventhDayTv = (TextView) findViewById(R.id.seventhDayTv);
                            seventhDayTv.setText(Constants.yoil[(num + 7) % 7]);
                            nameList = fstElmntd.getElementsByTagName("tmx");
                            seventhDayTmx = (TextView) findViewById(R.id.seventhDayTmx);
                            seventhDayTmx.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            nameList = fstElmntd.getElementsByTagName("tmn");
                            seventhDayTmn = (TextView) findViewById(R.id.seventhDayTmn);
                            seventhDayTmn.setText(nameList.item(0).getChildNodes().item(0).getNodeValue());
                            seventhDayIv = (ImageView) findViewById(R.id.seventhDayIv);
                            nameList = fstElmntd.getElementsByTagName("wf");
                            switch (nameList.item(0).getChildNodes().item(0).getNodeValue()) { //한국어 날씨를 가지고 비교
                                case "맑음":
                                    seventhDayIv.setImageResource(R.drawable.sunny);
                                    break;
                                case "구름조금":
                                    seventhDayIv.setImageResource(R.drawable.partialy_cloudy);
                                    break;
                                case "구름많음":
                                    seventhDayIv.setImageResource(R.drawable.cloudy_day);
                                    break;
                                case "흐림":
                                    seventhDayIv.setImageResource(R.drawable.cloudy);
                                    break;
                                case "구름많고 비":
                                    seventhDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "비":
                                    seventhDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "눈/비":
                                    seventhDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비":
                                    seventhDayIv.setImageResource(R.drawable.rainy);
                                    break;
                                case "구름많고 비/눈":
                                    seventhDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 비/눈":
                                    seventhDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "흐리고 눈/비":
                                    seventhDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "구름많고 눈/비":
                                    seventhDayIv.setImageResource(R.drawable.sleet);
                                    break;
                                case "눈":
                                    seventhDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "흐리고 눈":
                                    seventhDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                case "구름많고 눈":
                                    seventhDayIv.setImageResource(R.drawable.snowy);
                                    break;
                                default:
                                    seventhDayIv.setImageResource(R.mipmap.error);
                                    break;
                            }
                        }

                        //날씨 데이터를 추출
                        s += "" + j + ": 날씨 정보: ";
                        NodeList nameListd = fstElmntd.getElementsByTagName("tmEf");
                        s += " 시간 = " + nameListd.item(0).getChildNodes().item(0).getNodeValue() + " ,";


                        NodeList websiteListd = fstElmntd.getElementsByTagName("wf");
                        //<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
                        s += "날씨 = " + websiteListd.item(0).getChildNodes().item(0).getNodeValue() + "\n";


                        weatherParse.setText(s);

                        super.onPostExecute(doc);
                    }
                }


            }
        }


    }//end inner class - GetXMLTask



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        final Intent intent = new Intent(this, WeatherCalender.class);
        weatherParse = (TextView)findViewById(R.id.parseWeather);

        //button 입력
        Button calButton = (Button)findViewById(R.id.calbutton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

       // weatherActivity = new WeatherActivity(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//위치 정보를 컨트롤 할 수 있는 LocationManager 인스턴스 생성

        Iterator<String> providers = locationManager.getAllProviders().iterator();// 모든 프로바이더를 가져와서 providers 에 저장
        while (providers.hasNext()) {
            Log.v("Location", providers.next());
        }

        weatherLayout = (LinearLayout) findViewById(R.id.weatherLayout);
        Criteria criteria = new Criteria();//원하는 프로바이더의 조건을 설정하는 클래스

        criteria.setAccuracy(Criteria.NO_REQUIREMENT);//정확도를 설정
        criteria.setPowerRequirement(Criteria.POWER_LOW);//전원 사용량을설정

        String best = locationManager.getBestProvider(criteria, true);// 설정한 프로바이더를 best에 저장

        this.isGetLocation = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(best, 1000, 1, (LocationListener) WeatherActivity.this);
        location = locationManager.getLastKnownLocation(best);
        lat = location.getLatitude();
        lon = location.getLongitude();

        WeatherTask task = new WeatherTask();
        com.example.user.myapplication.weather.LatXLngY convert = new com.example.user.myapplication.weather.LatXLngY();
        LatXLngY latXLngY  =  com.example.user.myapplication.weather.LatXLngY.convertGRID_GPS(TO_GRID, lat , lon);
        Toast.makeText(getApplicationContext(), lat+" , " +lon, Toast.LENGTH_LONG).show();
        task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx="+(int)latXLngY.x+"&gridy="+(int)latXLngY.y+"");

        MidleForeTask midleForeTask = new MidleForeTask();
        midleForeTask.execute("http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?gridx="+(int)latXLngY.x+"&gridy="+(int)latXLngY.y+"");
    }


    Location lastLocation =null;


    protected void onResume(){
        super.onResume();


        //현재 시간 textView로 출력
        TextView textTime = (TextView) findViewById(R.id.textTime);
        String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        textTime.setText(currentTime);

        search(lastLocation);



    }


    public void search(Location location){
        Geocoder coder = new Geocoder(this, Locale.KOREA);
        try {
            Iterator<Address> addresses = coder.getFromLocation(lat,lon,3).iterator();
            if (addresses !=null){
                while(addresses.hasNext()){
                    Address nameLoc = addresses.next();
                    thoroughfare = nameLoc.getThoroughfare();
                    placeName = nameLoc.getLocality();
                    country = nameLoc.getCountryName();
                    TextView locationName = (TextView)findViewById(R.id.locationName);
                    if(country != null){

                        locationName.setText(country);
                    }
                    if(placeName != null){                                                          // "if(XX!=null)" : very important (적지 않으면, localname 값이 'null' 값이된다.

                        locationName.append(" "+placeName);
                    }
                    if (thoroughfare != null){

                        locationName.append(" "+thoroughfare);
                    }


                }
            }
        } catch (IOException e) {
            Log.e("GPS","Failed to get address",e);
        }
    }
    @Override
    public void onLocationChanged(Location location){

        lastLocation = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





    public static class LatXLngY
    {
        public double lat;
        public double lng;

        public double x;
        public double y;

    }

/*
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");
                // OK 를 누르게 되면 설정창으로 이동합니다.
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
*/


}
