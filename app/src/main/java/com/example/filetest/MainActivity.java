package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFileContent;
    private EditText editTextFileName;
    private ListView listViewFiles;
    private ArrayList<String> fileArrayList;
    private String fileName;
    private String fileContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextFileContent = findViewById(R.id.editTextFileContent);
        editTextFileName = findViewById(R.id.editTextFileName);
        listViewFiles = findViewById(R.id.listViewFiles);
        fileArrayList = new ArrayList<>();

        showListFiles();


        listViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fileName = fileArrayList.get(position);
                readFile(view);
                editTextFileName.setText(fileName);
                editTextFileContent.setText(fileContent);
            }
        });
    }

    // сохранение файла
    public void onClickSaveFile(View view) {
        if (editTextFileName.getText() != null) {
            fileName = editTextFileName.getText().toString().trim();
        } else {
            fileName = null;
        }
        try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) { // MODE_PRIVATE - перезапись файла / MODE_APPEND - запись данных в конец файла
            fileOutputStream.write(editTextFileContent.getText().toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // чтение файла
    public void readFile(View view) {
        try (FileInputStream fileInputStream = openFileInput(fileName)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            fileContent = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showListFiles() {
        File[] filesArray = getFilesDir().listFiles();
        for (File f : filesArray) {
            fileArrayList.add(f.getName());
        }
        ArrayAdapter<String> fileArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fileArrayList);
        listViewFiles.setAdapter(fileArrayAdapter);
    }
}