package gr313.yanev.lab03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    RadioGroup radioGroup;
    RadioButton rbTime;
    RadioButton rbMass;
    RadioButton rbCurrency;
    RadioButton rbDistance;

    EditText textFrom;
    EditText textTo;

    Spinner spinFrom;
    Spinner spinTo;

    Button btnConvert;


    ArrayAdapter<Unit> adpTime, adpMass, adpCurrency, adpDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menu with themes

        radioGroup = findViewById(R.id.radios);
        // Listening for a RadioButton change
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> onRadioGroupCheckChanged());

        rbTime = findViewById(R.id.rbTime);
        rbMass = findViewById(R.id.rbMass);
        rbCurrency = findViewById(R.id.rbCurrency);
        rbDistance = findViewById(R.id.rbDistance);

        textFrom = findViewById(R.id.textFrom);
        textTo = findViewById(R.id.textTo);

        spinFrom = findViewById(R.id.spinFrom);
        spinTo = findViewById(R.id.spinTo);

        btnConvert = findViewById(R.id.btnConvert);

        initializeAdapters();           // Initialize the adapters with units

        rbTime.setChecked(true);
        textTo.setEnabled(false);       // Don't allow edit the "To" field
        textTo.setTextColor(ContextCompat.getColor(this, R.color.black));

        spinFrom.setAdapter(adpTime);
        spinTo.setAdapter(adpTime);
    }

    private void initializeAdapters() {
        adpTime = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adpMass = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adpCurrency = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adpDistance = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        adpTime.add(new Unit("ms", 1000));
        adpTime.add(new Unit("s", 1));
        adpTime.add(new Unit("m", (double) 1/60));
        adpTime.add(new Unit("h", (double) 1/60/60));

        adpMass.add(new Unit("mg", (double) 1000*1000));
        adpMass.add(new Unit("g", (double) 1000));
        adpMass.add(new Unit("kg", 1));
        adpMass.add(new Unit("t", (double) 1/1000));

        adpCurrency.add(new Unit("₽", 89.29));
        adpCurrency.add(new Unit("¥", 147.32));
        adpCurrency.add(new Unit("$", 1));
        adpCurrency.add(new Unit("€", 0.92));

        adpDistance.add(new Unit("mm", 10*100));
        adpDistance.add(new Unit("cm", 100));
        adpDistance.add(new Unit("m", 1));
        adpDistance.add(new Unit("km", (double) 1/1000));
    }

    public void onRadioGroupCheckChanged() {
        RadioButton currentRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String title = currentRadioButton.getText().toString();

        switch (title) {
            case "Time":
                spinFrom.setAdapter(adpTime);
                spinTo.setAdapter(adpTime);
                textTo.setText("");
                break;
            case "Mass":
                spinFrom.setAdapter(adpMass);
                spinTo.setAdapter(adpMass);
                textTo.setText("");
                break;
            case "Currency":
                spinFrom.setAdapter(adpCurrency);
                spinTo.setAdapter(adpCurrency);
                textTo.setText("");
                break;
            case "Distance":
                spinFrom.setAdapter(adpDistance);
                spinTo.setAdapter(adpDistance);
                textTo.setText("");
                break;
        }
    }

    public void onConvertClick(View v) {
        if (textFrom.getText().toString().isEmpty()) {
            createMessage("Fill \"From\" field!");
            textTo.setText("");
            return;
        }

        double input = Double.parseDouble(textFrom.getText().toString());

        Unit fromUnit = (Unit) spinFrom.getSelectedItem();
        double fromValue = fromUnit.getValue();

        Unit toUnit = (Unit) spinTo.getSelectedItem();
        double toValue = toUnit.getValue();

        try {
            double output = input / fromValue * toValue;
            textTo.setText(String.format("%.12f", output).replaceAll("0*$", "").replaceAll("\\.$", ""));

        } catch (Exception ex) {
            createMessage("Error: invalid input");
        }
    }


    public void onCopyIconClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", textTo.getText().toString());
        clipboard.setPrimaryClip(clip);
        createMessage("Copied!");
    }

    private void createMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}