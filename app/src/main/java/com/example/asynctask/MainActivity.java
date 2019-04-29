package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chronoValue)
    EditText chronoValue;
    @BindView(R.id.chronoText)
    TextView chronoText;
    @BindView(R.id.start)
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start)
    public void onClick() {
        int value = Integer.parseInt(chronoValue.getText().toString());
        if(value >= 0){
            new Chronograph().execute(value);
        }else{
            Toast.makeText(this, "请输入大于0的数", Toast.LENGTH_SHORT).show();
        }
    }

    private class Chronograph extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chronoValue.setEnabled(false);
            start.setEnabled(false);
            chronoText.setText("0:0");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                for (int j = 0; j <= 60; j++) {
                    publishProgress(i,j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            chronoText.setText(values[0] + ":" + values[1]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            chronoValue.setEnabled(false);
            start.setEnabled(false);
        }
    }
}
