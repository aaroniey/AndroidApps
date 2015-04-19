package com.example.aaron.caclulator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private byte stage;          //1 is for first number, 2 is selecting operator, 3 is selecting second number, 4 is solved with answer
    private String curNum;
    TextView textView = null;
    TextView textViewNeg = null;
    private double firNum;
    private double secNum;
    private double answer;
    boolean negative;
    private byte operator;      //1 add, 2 subtract, 3 multiply, 4 divide.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.Display);
        textView.setText("0");
        textViewNeg = (TextView) findViewById(R.id.NegSign);
        textViewNeg.setText("");
        stage = 1;
        curNum = "";
        negative = false;
        firNum = 0;
        secNum = 0;
        operator = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void numberHandler(char num){
        if(stage == 1 || stage == 3){
            int count = (decPresent()) ? 10 : 9;
            if(curNum.length() < count){
                if(curNum.length() == 1 && curNum.charAt(0) == '0'){
                    curNum = "";
                }
                curNum = curNum.concat(String.valueOf(num));
            }
        }
        else if(stage == 0){
            stage = 1;
            curNum = String.valueOf(num);
        }
        else if(stage == 2){
            stage = 3;
            negative = false;
            showNeg();
            curNum = String.valueOf(num);
        }
        else if(stage == 4){
            firNum = 0;
            negative = false;
            showNeg();
            secNum = 0;
            answer = 0;
            stage = 1;
            operator = 0;
            curNum = String.valueOf(num);
        }
        else{
            firNum = 0;
            secNum = 0;
            negative = false;
            showNeg();
            answer = 0;
            stage = 0;
            operator = 0;
            curNum = "error tacking stage numberHandler";
        }
        updateText();
        showNeg();
    }
    private void symbolHandler(byte op){
        if(stage == 1){
            firNum = Double.parseDouble(curNum);
            firNum = (negative) ? (firNum*-1) : firNum;
            operator = op;
            stage = 2;
        }
        else if(stage == 2){
            operator = op;
        }
        else if(stage == 3){
            secNum = Double.parseDouble(curNum);
            secNum = (negative) ? (secNum*-1) : secNum;
            negative = false;
            showNeg();
            boolean pass = solve();
            if(pass) {
                firNum = answer;
                answer = 0;
                stage = 2;
                operator = op;
                if(firNum < 0){
                    negative = true;
                    showNeg();
                }
                firNum = Math.abs(firNum);
                curNum = String.valueOf(Math.abs(firNum));
                updateText();
            }
        }
        else if(stage == 4){
            firNum = answer;
            answer = 0;
            stage = 2;
            operator = op;
            if(firNum < 0){
                negative = true;
                showNeg();
            }
            firNum = Math.abs(firNum);
            curNum = String.valueOf(Math.abs(firNum));
            updateText();
        }
        else if(stage == 0){
            firNum = 0;
            operator = 0;
            negative = false;
            showNeg();
            curNum = "0";
            stage = 1;
        }
        else{
            firNum = 0;
            secNum = 0;
            answer = 0;
            negative = false;
            showNeg();
            stage = 0;
            operator = 0;
            curNum = "error tacking stage symbolHandler";
        }
    }
    private void specialHandler(byte choice){
        //choice == 1(cls),2(del),3(neg), 4(equal sign),5(decimal);
        if(choice ==1){
            firNum = 0;
            secNum = 0;
            answer = 0;
            stage = 1;
            negative = false;
            operator = 0;
            curNum = "";
            updateText();
            showNeg();
        }
        else if(choice ==2){
            curNum = "";
            if(stage == 2){
                operator = 0;
                stage = 1;
            }
            negative = false;
            showNeg();
            updateText();
        }
        else if(choice ==3){
            if(stage!=4) {
                negative = !negative;
                showNeg();
            }
        }
        else if(choice ==4){
            if(stage == 0){
                //do nothing
            }
            else if(stage ==1){
                stage = 4;
                answer = Double.parseDouble(curNum);
                answer = (negative) ? (answer*-1) : answer;
                negative = false;
                firNum = 0;
                secNum = 0;
                if(answer < 0){
                    negative = true;
                }
                showNeg();
                answer = Math.abs(answer);
                curNum = String.valueOf(Math.abs(answer));

                updateText();
                operator = 0;
            }
            else if(stage == 2){
                stage = 4;
                answer = Double.parseDouble(curNum);
                answer = (negative) ? (answer*-1) : answer;
                negative = false;
                firNum = 0;
                secNum = 0;
                if(answer < 0){
                    negative = true;
                }
                showNeg();
                answer = Math.abs(answer);
                curNum = String.valueOf(Math.abs(answer));
                updateText();
                operator = 0;
            }
            else if(stage ==  3){
                stage = 4;
                secNum = Double.parseDouble(curNum);
                secNum = (negative) ? (secNum*-1) : secNum;
                negative = false;
                showNeg();
                solve();
            }
            else if(stage == 4){
                //do nothing
            }
            else{
                firNum = 0;
                secNum = 0;

                negative = false;
                showNeg();
                stage = 0;
                operator = 0;
                curNum = "error tacking stage specialHandler equal sign";
            }
            updateText();
        }
        else if(choice == 5) {
            if (stage == 0) {
                stage = 1;
                curNum = "0.";

            } else if (stage == 1) {
                curNum = curNum.concat(".");
            } else if (stage == 2) {
                stage = 3;
                negative = false;
                showNeg();
                curNum = "0.";
            } else if (stage == 3) {
                curNum = curNum.concat(".");
            } else if (stage == 4) {
                firNum = 0;
                negative = false;
                showNeg();
                secNum = 0;
                answer = 0;
                stage = 1;
                operator = 0;
                curNum = "0.";
            } else {
                firNum = 0;
                secNum = 0;
                negative = false;
                showNeg();
                answer = 0;
                stage = 0;
                operator = 0;
                curNum = "error tacking stage special handler dec";
            }
            updateText();

        }
    }


    private boolean solve(){
        if(operator == 1){
            //add
            answer = firNum + secNum;
            if (answer > 999999999 || answer < -999999999){
                //overflow/underflow
                firNum = 0;
                secNum = 0;
                negative = false;
                showNeg();
                stage = 0;
                operator = 0;

                curNum = (answer>0) ? "Error, answer larger than max value": "Error, answer smaller than min value";
                answer = 0;
                textView.setText(curNum);
                return false;
            }
            firNum = 0;
            secNum = 0;
            negative = false;
            showNeg();
            stage = 4;
            operator = 0;
            curNum = String.valueOf(answer);
            updateText();
            return true;
        }
        else if(operator == 2){
            //subtract
            answer = firNum - secNum;
            if (answer > 999999999 || answer < -999999999){
                //overflow/underflow
                firNum = 0;
                secNum = 0;
                negative = false;
                showNeg();
                stage = 0;
                operator = 0;

                curNum = (answer>0) ? "Error, answer larger than max value": "Error, answer smaller than min value";
                answer = 0;
                textView.setText(curNum);
                return false;
            }
            firNum = 0;
            secNum = 0;
            negative = false;
            showNeg();
            stage = 4;
            operator = 0;
            curNum = String.valueOf(answer);
            updateText();
            return true;
        }
        else if(operator == 3){
            //multiply
            answer = firNum * secNum;
            if (answer > 999999999 || answer < -999999999){
                //overflow/underflow
                firNum = 0;
                secNum = 0;
                negative = false;
                showNeg();
                stage = 0;
                operator = 0;

                curNum = (answer>0) ? "Error, answer larger than max value": "Error, answer smaller than min value";
                answer = 0;
                textView.setText(curNum);
                return false;
            }
            firNum = 0;
            secNum = 0;
            negative = false;
            showNeg();
            stage = 4;
            operator = 0;
            curNum = String.valueOf(answer);
            updateText();
            return true;
        }
        else if(operator == 4){
            //divide\
            if(secNum == 0 ){
                firNum = 0;
                secNum = 0;
                answer = 0;
                negative = false;
                showNeg();
                stage = 0;
                operator = 0;
                curNum = "Cannot divide by 0";
                textView.setText(curNum);
                return false;
            }
            answer = firNum / secNum;
            if (answer > 999999999 || answer < -999999999){
                //overflow/underflow
                firNum = 0;
                secNum = 0;
                negative = false;
                showNeg();
                stage = 0;
                operator = 0;

                curNum = (answer>0) ? "Error, answer larger than max value": "Error, answer smaller than min value";
                answer = 0;
                textView.setText(curNum);
                return false;
            }
            firNum = 0;
            secNum = 0;
            negative = false;
            showNeg();
            stage = 4;
            operator = 0;
            curNum = String.valueOf(answer);
            updateText();
            return true;
        }
        else{
            firNum = 0;
            secNum = 0;
            answer = 0;
            negative = false;
            showNeg();
            stage = 0;
            operator = 0;
            curNum = "operator unknown";
            return false;
        }
    }
    private boolean decPresent(){
        for(int i = 0; i< curNum.length(); i++){
            if(curNum.charAt(i) == '.'){
                return true;
            }
        }
        return false;
    }


    private void updateText(){
        if(curNum.equals("")){
            textView.setText("0");
        }
        else{
            textView.setText(curNum);
        }
    }
    private void showNeg(){
        String text = (negative) ? "-" : "";
        textViewNeg.setText(text);
    }


    public void buttonOneHandler(View view){
        numberHandler('1');
    }
    public void buttonTwoHandler(View view){
        numberHandler('2');
    }
    public void buttonThreeHandler(View view){
        numberHandler('3');
    }
    public void buttonFourHandler(View view){
        numberHandler('4');
    }
    public void buttonFiveHandler(View view){
        numberHandler('5');
    }
    public void buttonSixHandler(View view){
        numberHandler('6');
    }
    public void buttonSevenHandler(View view){
        numberHandler('7');
    }
    public void buttonEightHandler(View view){
        numberHandler('8');
    }
    public void buttonNineHandler(View view){
        numberHandler('9');
    }
    public void buttonZeroHandler(View view){
        numberHandler('0');
    }
    public void buttonClsHandler(View view){
        specialHandler((byte)1);
    }
    public void buttonDelHandler(View view){
        specialHandler((byte)2);
    }
    public void buttonNegHandler(View view){
        specialHandler((byte)3);
    }
    public void buttonEqualHandler(View view){
        specialHandler((byte)4);
    }
    public void buttonDecHandler(View view){
        specialHandler((byte)5);
    }
    public void buttonPlusHandler(View view){
        symbolHandler((byte)1);
    }
    public void buttonSubHandler(View view){
        symbolHandler((byte)2);
    }
    public void buttonMultHandler(View view){
        symbolHandler((byte)3);
    }
    public void buttonDivHandler(View view){
        symbolHandler((byte)4);
    }
}
