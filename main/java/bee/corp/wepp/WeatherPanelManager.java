package bee.corp.wepp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class WeatherPanelManager {
    int x;
    float width;
    int height;
    int newY;
    int oldY;
    Rect textBounds;
    ArrayList<WeatherPanel> weps;
    Context context;
    public WeatherPanelManager(Context c,int x, int y, float width, int height){
        weps = new ArrayList<>();
        textBounds = new Rect();
        this.x = x;
        this.newY = y;
        this.oldY=y;
        this.width = width;
        this.height = height;
        this.context = c;
    }
    public void setWep(String country, String description, double temperature, int index){
        if(weps.size()>0){
            weps.remove(0);
        }
        weps.add(index,new WeatherPanel(this.context));
        weps.get(index).setLayoutParams(new FrameLayout.LayoutParams((int)this.width,this.height));
        weps.get(index).setBackgroundResource(R.drawable.rounding);
        weps.get(index).setX(x);
        weps.get(index).setY(newY);

        addCountry(index,country);
        addTemperature(index, temperature);
        addDescription(index, description);

        checkDescriptionAndSetIcon(index);
    }
    public void addWep(){
        weps.add(0,new WeatherPanel(this.context));
        weps.get(0).setLayoutParams(new FrameLayout.LayoutParams((int)this.width,this.height));
        weps.get(0).setBackground(R.drawable.rounding);
        weps.get(0).setX(x);
        weps.get(0).setY(newY);
        newY+=(height + 5);
    }
    public void addCountry(int index, String c){
        weps.get(index).addCountry(c);
        weps.get(index).country.getPaint().getTextBounds(weps.get(index).description.getText().toString(),0,weps.get(index).description.getText().length(), textBounds);
        weps.get(index).country.setX(25);
        if(weps.get(index).country.getX()<1){
            weps.get(index).country.setX(1);
        }
        weps.get(index).country.setY(5);
    }
    public void addDescription(int index, String desc){
        weps.get(index).setDescription(desc);
        weps.get(index).description.getPaint().getTextBounds(weps.get(index).description.getText().toString(),0,weps.get(index).description.getText().length(), textBounds);
        weps.get(index).description.setX(this.width/2 - textBounds.width()/2);
        if(weps.get(index).description.getX()<1){
            weps.get(index).description.setX(1);
        }
        weps.get(index).description.setY(weps.get(index).temperature.getTextSize() + weps.get(index).temperature.getY() + 28);
    }
    public void addTemperature(int index, double tmprt){
        weps.get(index).setTemperature(tmprt);
        weps.get(index).temperature.setX(this.width/2 + weps.get(index).temperature.getTextSize());
        weps.get(index).temperature.setY(55);
    }
    public void addIcon(int index, int i){
        weps.get(index).setIcon(i);
        weps.get(index).icon.setX(25);
        weps.get(index).icon.setY(10);
    }
    public void checkDescriptionAndSetIcon(int index){
        if(weps.get(index).description.getText().toString().contains("thunderstorm")){
            if(weps.get(index).description.getText().toString().contains("heavy")||
                    weps.get(index).description.getText().toString().contains("ragged")){
                addIcon(index, WeatherIcons.StormyWeather);
            } else{
                addIcon(index, WeatherIcons.ThunderingWeather);
            }
        } else if(weps.get(index).description.getText().toString().contains("rain")){
            addIcon(index, WeatherIcons.RainyWeather);
        }else if(weps.get(index).description.getText().toString().contains("snow")||
                weps.get(index).description.getText().toString().contains("sleet")){
            if(weps.get(index).description.getText().toString().contains("heavy")){
                addIcon(index, WeatherIcons.VerySnowyWeather);
            } else{
                addIcon(index, WeatherIcons.SnowyWeather);
            }
        } else if(weps.get(index).description.getText().toString().contains("clouds")){
            addIcon(index, WeatherIcons.LittleCloudyWeather);
        } else if(weps.get(index).description.getText().toString().contains("sky")){
            addIcon(index, WeatherIcons.SunnyWeather);
        }
    }
}
