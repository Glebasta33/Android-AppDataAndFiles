package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static String FILE_NAME_1 = "file_1.txt";
    private final static String FILE_NAME_2 = "file_2.txt";
    public static final String ROOT_DIR = "root";
    private EditText editText;
    private TextView textView;
    private TextView textViewListFiles;
    private File file_1;
    private File file_2;

    private File rootDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editor);
        textView = findViewById(R.id.text);
        textViewListFiles = findViewById(R.id.textViewListFiles);
        createFiles();
    }

    // сохранение файла
    public void onClickSaveFile(View view) {
        try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME_2, MODE_PRIVATE)) { // MODE_PRIVATE - перезапись файла / MODE_APPEND - запись данных в конец файла
            String text = editText.getText().toString() + "\n";
            fileOutputStream.write(text.getBytes());
            Toast.makeText(this, "file_2.getAbsolutePath(): " + file_2.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            showListFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // чтение файла
    public void onClickReadFile(View view) {
        try (FileInputStream fileInputStream = openFileInput(FILE_NAME_2)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String text = new String(bytes);
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFiles() {
        rootDir = new File(ROOT_DIR);
        file_1 = new File(rootDir, FILE_NAME_1);
        file_2 = new File(rootDir, FILE_NAME_2);
        rootDir.mkdir();
        try {
            file_1.createNewFile();
            file_2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showListFiles() {
        File[] filesArray = getFilesDir().listFiles();
        StringBuffer stringBuffer = new StringBuffer();
        for (File f : filesArray) {
            stringBuffer.append(f.getName()).append("\n");
        }
        textViewListFiles.setText(stringBuffer.toString());
    }
}