package bee.corp.wepp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherPanel extends FrameLayout {
    //#202B3C
    //int x;
    //int y;
    //int width;
    //int height;
    ImageView icon;
    TextView description;
    TextView temperature;
    TextView country;
    public WeatherPanel(Context c){
        super(c);
        icon = new ImageView(c);
        icon.setLayoutParams(new FrameLayout.LayoutParams(110,110));
        description = new TextView(c);
        description.setTextColor(Color.WHITE);
        description.setTextSize(21);
        temperature = new TextView(c);
        temperature.setTextColor(Color.WHITE);
        temperature.setTextSize(18);
        country = new TextView(c);
        country.setTextColor(Color.WHITE);
        country.setTextSize(18);
    }
    public void setIcon(int i){
        icon.setBackgroundResource(i);
        this.addView(icon);
    }
    public void setDescription(String desc){
        desc = desc.replaceFirst(String.valueOf(desc.charAt(0)), String.valueOf(Character.toUpperCase(desc.charAt(0))));
        description.setText(desc);
        description.measure(0,0);
        this.addView(description);
    }
    public void setTemperature(double tmprt){
        temperature.setText((int) tmprt - 273 +"°C");
        temperature.measure(0,0);
        this.addView(temperature);
    }
    public void addCountry(String c){
        country.setText(c);
        country.measure(0,0);
        this.addView(country);
    }
    public void setBackground(int c){
        this.setBackgroundColor(c);
    }
}
