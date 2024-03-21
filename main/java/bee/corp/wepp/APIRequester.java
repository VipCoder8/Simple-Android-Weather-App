package bee.corp.wepp;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class APIRequester {
    private URL url;
    private HttpURLConnection httpConnection;
    private BufferedReader outputReader;
    private StringBuilder outputBuilder;
    private Thread apiRequestThread;
    private String urlString;
    public APIRequester(String urlString){
        this.urlString = urlString;
        outputBuilder = new StringBuilder();
        apiRequestThread = new Thread(this::openUrlConnection);
        apiRequestThread.start();
    }
    public APIRequester(){

    }
    public void sendRequest(String urlString){
        this.urlString = urlString;
        outputBuilder = new StringBuilder();
        apiRequestThread = new Thread(this::openUrlConnection);
        apiRequestThread.start();
    }
    public String getResponse(){
        try {
            apiRequestThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return outputBuilder.toString();
    }
    private void openUrlConnection(){
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
        } catch (MalformedURLException ignored) {ignored.printStackTrace();} catch (IOException e) {e.printStackTrace();}
        String line;
        try {
            outputReader = new BufferedReader(new InputStreamReader(this.httpConnection.getInputStream()));
            while((line=outputReader.readLine())!=null){
                outputBuilder.append(line);
            }
            outputReader.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
