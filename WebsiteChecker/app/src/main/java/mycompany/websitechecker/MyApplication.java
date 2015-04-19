package mycompany.websitechecker;

import android.app.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Asilcott on 3/9/2015.
 */
public class MyApplication extends Application {
    MainActivity curAct = null;
    boolean running;
    boolean resultNeedUpdate;
    boolean previousResult;
    URL resultUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        running = false;
        resultNeedUpdate = false;
    }
    public void attachActivity(MainActivity ma){
        curAct = ma;
        if(resultNeedUpdate){
            if(previousResult){
                curAct.postSuccess(resultUrl);
            }
            else{
                curAct.postFail(resultUrl);
            }
        }
    }
    public void parseUrl(final URL url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                running = true;
                boolean result = false;
                BufferedReader in = null;
                try{
                    in = new BufferedReader( new InputStreamReader( url.openStream()));
                    String line = in.readLine();
                    while (line != null){
                        if (line.toLowerCase().contains("tiger")){
                            result = true;
                            break;
                        }

                        line = in.readLine();
                    }
                    in.close();
                }
                catch (IOException e){

                }
                if(curAct != null){
                    if (result) {
                        curAct.postSuccess(url);
                    }
                    else{
                        curAct.postFail(url);
                    }
                }
                else{
                    resultNeedUpdate = true;
                    previousResult = result;
                    resultUrl= url;
                }
                running = false;
            }
        }).start();
    }
    public boolean threadRunning(){
        return running;
    }
}
