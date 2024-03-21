package bee.corp.wepp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    WeatherPanelManager wepManager;
    ConstraintLayout mainLayout;
    EditText searchText;
    APIRequester ar;
    JSONParser jParser;
    String oldUserCountryRequest;
    //weather data: https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    //geocoding: http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        jParser = new JSONParser();
        wepManager = new WeatherPanelManager(getApplicationContext(), (int)((int)getWindowManager().getDefaultDisplay().getWidth()/2f-(getWindowManager().getDefaultDisplay().getWidth()/2.5f)/2), (int) ((int)40*1.5f)*2, getWindowManager().getDefaultDisplay().getWidth()/2.5f, 300);
        ar = new APIRequester();
        addWepOnTextChange();
    }
    public void setupViews(){
        mainLayout = findViewById(R.id.MainLayout);
        searchText = findViewById(R.id.searchText);
    }
    public void addWepOnTextChange(){
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(!Objects.equals(oldUserCountryRequest, searchText.getText().toString())){
                        ar.sendRequest("https://api.openweathermap.org/geo/1.0/direct?q="+searchText.getText()+"&limit=5&appid=8e12082096ae35958104f65e62ba42b1");
                        ar.sendRequest("https://api.openweathermap.org/data/2.5/weather?lat="+jParser.parseArray(ar.getResponse(), "lat") +"&lon="+jParser.parseArray(ar.getResponse(), "lon")+"&appid=43e1a2bea110324efd9b6dcedfbcabbc");
                        if(jParser.parseArray(jParser.parseObject(ar.getResponse(), "weather"), "description")!="null") {
                            wepManager.setWep(oldUserCountryRequest,
                            jParser.parseArray(jParser.parseObject(ar.getResponse(), "weather"), "description"),
                            Double.valueOf(jParser.parseObject(jParser.parseObject(ar.getResponse(), "main"), "temp")),
                                    0);
                            mainLayout.addView(wepManager.weps.get(0));
                        }
                        oldUserCountryRequest = searchText.getText().toString();
                        searchText.setText("");
                    }
                }
                return true;
            }
        });
    }
}