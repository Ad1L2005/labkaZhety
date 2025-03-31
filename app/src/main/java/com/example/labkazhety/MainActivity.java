package com.example.labkazhety;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class MainActivity extends AppCompatActivity {

    private EditText editFileData;
    private TextView textFileData;
    private ListView listViewFiles;
    private String selectedFileName = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFileData = findViewById(R.id.editFileData);
        textFileData = findViewById(R.id.textFileData);
        listViewFiles = findViewById(R.id.listViewFiles);
        Button btnSaveFile = findViewById(R.id.btnSaveFile);
        Button btnLoadFile = findViewById(R.id.btnLoadFile);

        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> displayFileList());

        displayFileList();


        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            loadAllFiles();
        });
    }


    private void saveToFile() {
        String data = editFileData.getText().toString().trim();
        if (data.isEmpty()) {
            Toast.makeText(this, "Введите текст перед сохранением", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(getExternalFilesDir(null), selectedFileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Файл сохранён: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            displayFileList();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadAllFiles() {
        File directory = getExternalFilesDir(null);
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            Toast.makeText(this, "Нет файлов для отображения", Toast.LENGTH_SHORT).show();
            textFileData.setText("Нет файлов.");
            return;
        }

        StringBuilder allContent = new StringBuilder();

        for (File file : files) {
            allContent.append("Файл: ").append(file.getName()).append("\n");

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    allContent.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                allContent.append("Ошибка чтения файла\n");
            }

            allContent.append("\n----------------------\n");
        }

        textFileData.setText(allContent.toString());
    }
    private void displayFileList() {
        File directory = getExternalFilesDir(null);
        File[] files = directory.listFiles();
        List<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }

        if (fileList.isEmpty()) {
            fileList.add("Файлов нет");
            listViewFiles.setEnabled(false);
        } else {
            listViewFiles.setEnabled(true);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listViewFiles.setAdapter(adapter);
    }
}