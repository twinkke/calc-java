package by.bsuir.calc_rad;


import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashSet;//пакет класса HashSet, реализация интерфейса Set, базирующаяся на HashMap
import java.util.List;
import java.util.ArrayList;// объект класса List(реализация интерфейса)
import java.util.Set;
//RecyclerView

public class MainActivity extends AppCompatActivity {


    TextView historyOut; // текстовое поле для вывода результата
    EditText display;   // поле для ввода числа
    String operand = null;
    int index = 0;
    List <String> list = new ArrayList <>();// по умолчанию 10
    LinearLayout linearLayout;
    ScrollView scroll;
    SharedPreferences sPref ;
    String key1 = "key1";
    String key2 = "key2";
    Set <String> lister = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate принимает объект Bundle,
        // содержащий состояние пользовательского интерфейса, сохранённое в последнем вызове обработчика onSaveInstanceState
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (EditText) findViewById(R.id.inPut);
        display.setShowSoftInputOnFocus(false);//скрытие выплывающего дисплея
        linearLayout = (LinearLayout) findViewById(R.id.llContainer);
        scroll = (ScrollView) findViewById(R.id.scroll);
      //  onLoad();
    }

//выход из приложения
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // onSave();
    }
    @Override
    protected void onStop(){
        super.onStop();
        //onSave();
    }

    //сохранение при повороте
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("str",display.getText().toString());
        outState.putStringArrayList(key2, (ArrayList<String>) list);
        super.onSaveInstanceState(outState);
       // onSave();
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operand = savedInstanceState.getString("str");
        ArrayList <String> saveText =  savedInstanceState.getStringArrayList(key2);
        display.setText("");
        display.setText(operand);
        display.setSelection(operand.length());//перемещенеие курсора в конец
        for(String res : saveText) {
            updateHistory(res.toString());
        }
    }
    //сохранение данных в памяти
    private void onSave(){
        sPref = getPreferences(MODE_PRIVATE);//констаната только приложение имеет доступ к настройкам
        SharedPreferences.Editor ed = sPref.edit();//какие-то изменения; объект Editor
        ed.putStringSet(key1, lister);
        ed.putString("str",display.getText().toString());
        ed.commit();//подтверждение изменения
    }
    //выгрузка из памяти при загрузки приложения
    private void onLoad(){
        sPref = getPreferences(MODE_PRIVATE);
        Set <String> saveText = sPref.getStringSet(key1, new HashSet<String>());
        System.out.print(saveText);
        display.setText("");
        display.setText(sPref.getString("str",""));
        for(String res : saveText) {
            updateHistory(res.toString());
        }

    }
    //вывод текста
    private void updateText(String addText){
        String oldText = display.getText().toString();
        int cousorPos = display.getSelectionStart();
        String leftStr = oldText.substring(0, cousorPos);
        String rightStr = oldText.substring(cousorPos);
        if ("0".equals(display.getText().toString())){
            display.setText(addText);

        }else{
            display.setText(String.format("%s%s%s", leftStr, addText , rightStr));
            display.setSelection(cousorPos + 1);
        }
    }

    // обработка нажатия на числовую кнопку
    public void onNumberClick(View v) {
        Button button = (Button) v;
        updateText(button.getText().toString());
    }

    public void backSpace(View view) {
        int cousor = display.getSelectionStart();
        int textLeg = display.getText().length();
        if (cousor != 0 && textLeg != 0) {
            SpannableStringBuilder selecion = (SpannableStringBuilder) display.getText();
            selecion.replace(cousor - 1, cousor, "");
            display.setText(selecion);
            display.setSelection(cousor -1);
        }}


    public void onClear(View view){
        display.setText("");
    }

        public void sin_btn(View view){
            String str = "sin(rad(";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }

        public void cos_btn(View view){
            String str = "cos(rad(";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }
        public void tg_btn(View view){
            String str = "cos(rad(";
            updateText(str);
             display.setSelection(display.getText().toString().length());
        }
        public void arg_btn(View view){
            String str = "arg(rad(";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }
        public void ln_btn(View view){
            String str = "ln(";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }
        public void pi_btn(View view){
            String str = "pi";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }
        public void exp_btn(View view){
            String str = "e";
            updateText(str);
            display.setSelection(display.getText().toString().length());
        }
        public void pers_btn(View view){
            String str = "%%%";
        }

//вывод истории
        public void updateHistory( String args){
        index +=1;
            TextView textView1 = new TextView(this);
            textView1.setTextColor(0xff66ff66);
            textView1.setText(args);
            list.add(args);
            linearLayout.addView (textView1);
            scroll.fullScroll(ScrollView.FOCUS_DOWN);
        }//вывод новой истории
        public void updateHS( String args) {
        lister.add(args + "=" + display.getText());
            TextView textView1 = new TextView(this);
            textView1.setTextColor(0xff66ff66);
                textView1.setText(args + "=" + display.getText());
            linearLayout.addView (textView1);
            scroll.fullScroll(ScrollView.FOCUS_DOWN);
        }


//равно
    public void equalBtn (View view){
            String userExpresion = String.valueOf(display.getText());
            Expression e1 = new Expression(userExpresion);
            String result = String.valueOf(e1.calculate());
            if (result.equals("NaN")){
                display.setText("Error");
            }
            else{
                updateHS(result);
                display.setText(result);
                display.setSelection(result.length());
            }
        }
    }
