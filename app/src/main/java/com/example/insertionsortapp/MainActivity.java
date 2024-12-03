package com.example.insertionsortapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView resultView;
    private Button sortButton;
    private Button sortAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        inputField = findViewById(R.id.inputField);
        resultView = findViewById(R.id.resultView);
        sortButton = findViewById(R.id.sortButton);
        Button quitButton = findViewById(R.id.quitButton);
        sortAgainButton = findViewById(R.id.sortAgainButton);

        
        sortAgainButton.setVisibility(Button.GONE);

        
        sortButton.setOnClickListener(v -> {
            
            String input = inputField.getText().toString().trim();
            if (input.equalsIgnoreCase("quit")) {
                finish(); 
            } else {
                processInput(input);
            }
        });
        
        quitButton.setOnClickListener(this::onClick);

        
        sortAgainButton.setOnClickListener(v -> {
            inputField.setText("");
            resultView.setText("");
            sortButton.setVisibility(Button.VISIBLE);
            sortAgainButton.setVisibility(Button.GONE);
        });
    }

    private void processInput(String input) {
        
        int[] numbers = validateAndParseInput(input);
        if (numbers == null) {
            return;
        }

        
        insertionSort(numbers);
    }

    private int[] validateAndParseInput(String input) {
        String[] elements = input.split("\\s+");
        if (elements.length < 3 || elements.length > 8) {
            Toast.makeText(this, getString(R.string.error_invalid_size), Toast.LENGTH_LONG).show();
            return null;
        }

        int[] arr = new int[elements.length];
        try {
            for (int i = 0; i < elements.length; i++) {
                int value = Integer.parseInt(elements[i]);
                if (value < 0 || value > 9) {
                    Toast.makeText(this, "Error: Array elements must be between 0 and 9.", Toast.LENGTH_LONG).show();
                    return null;
                }
                arr[i] = value;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error: Please enter valid integers only.", Toast.LENGTH_LONG).show();
            return null;
        }
        return arr;
    }

    private void insertionSort(int[] arr) {
        // Display the input array
        resultView.append("Input Array: " + Arrays.toString(arr) + "\n");
        resultView.append("Insertion Sort (Intermediate Steps)\n");

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;

            
            resultView.append(Arrays.toString(arr) + "\n");
        }

        
        String finalArrayText = "Sorted Array: " + Arrays.toString(arr) + "\n";
        SpannableString boldFinalArray = new SpannableString(finalArrayText);
        boldFinalArray.setSpan(new StyleSpan(Typeface.BOLD), 0, boldFinalArray.length(), 0); // Apply bold style

        resultView.append(boldFinalArray);
        
        sortButton.setVisibility(Button.GONE);
        sortAgainButton.setVisibility(Button.VISIBLE);
    }

    
    private void showQuitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("do you wanna repeat the sorting again or quit the application?")
                .setCancelable(false)
                .setPositiveButton("Do Again", (dialog, id) -> {
                    inputField.setText(""); 
                    resultView.setText("");
                    sortButton.setVisibility(Button.VISIBLE);
                    sortAgainButton.setVisibility(Button.GONE);
                })
                .setNegativeButton("Quit", (dialog, id) -> {
                    finish();
                })
                .show();
    }

    private void onClick(View v) {
        showQuitDialog();
    }
}
