package com.example.programmer.myapplicationv20;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    private ListView lv;
    String title;
    final String stra[]=new  String[5];
    String[] strt;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> arr1=new ArrayList<>();
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView)findViewById(R.id.lv1);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        MyTask my=new MyTask();
        my.execute();

        try {
            //кривой способ, правильнее заполнять listview в методе onPostExecute, но пока так. не забыть!
            arr1=my.get();
            //adapter.addAll(arr1);
        }catch (Exception e){

        }

        lv.setAdapter(adapter);
    }

    class MyTask extends AsyncTask<Void, Void, ArrayList<String>> {
        //String title;
        String s; int i=0;



        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try{
                //Document doc=Jsoup.connect("https://home.mephi.ru/study_groups/2888/schedule").get();
                //title=doc.title();

                //Вытаскиваем рассписание
                Date DNow=new Date();
                SimpleDateFormat FormatforDateNow = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("текущая дата "+FormatforDateNow.format(DNow));

                Document doc = Jsoup.connect("https://home.mephi.ru/study_groups/2888/day?date="+FormatforDateNow.format(DNow)).get();
                //Document doc = Jsoup.connect("https://home.mephi.ru/study_groups/2888/day?date=2017-12-15").get();

                //list-group-item
                //Document doc = Jsoup.connect("https://home.mephi.ru/study_groups/2888/day?date=2017-12-13").get();
                //title=doc.title();
                title=FormatforDateNow.format(DNow);


                //Elements LInfo = doc.getElementsByClass("lesson lesson-lecture");
                Elements LInfo = doc.getElementsByClass("list-group-item");
                Elements LTime=doc.getElementsByClass("lesson-time");

                /*for (Element el: LTime){
                    arr.add(el.text());
                }*/

                for (Element el: LInfo){
                    arr.add(el.text());
                }

               /* for (Element el : LInfo) {
                    s=el.text();
                    arr.set(i,arr.get(i)+" "+s);
                    //title=title+" "+i+":"+arr.get(i)+"; ";
                    i++;
                }*/
                adapter.addAll(arr);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return arr;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            //tv.setText(title);
            //lv.setAdapter(adapter);

            //Тут выводим итоговые данные
        }
    }
}
