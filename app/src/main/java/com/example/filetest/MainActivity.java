package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private final static String FILE_NAME = "content.txt";
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editor);
        textView = (TextView) findViewById(R.id.text);
    }


    // сохранение файла
    public void onClickSaveFile(View view) {
        try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND)) { // MODE_PRIVATE - перезапись файла / MODE_APPEND - запись данных в конец файла
            String text = editText.getText().toString() + "\n";
            fileOutputStream.write(text.getBytes());
            Toast.makeText(this, "Файл сохранён", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // чтение файла
    public void onClickReadFile(View view) {
        try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String text = new String(bytes);
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}