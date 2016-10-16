package app.phakphuc.northssms;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    TableSiteData tbsitedata ;
    TableVersion tbversion;
    private String var;
    private String[] spinnerValues = {"CHECK RECTIFIER","CHECK BATTERY","CHECK IP CONFIG"};
    private Spinner spinner;
    public static Integer spinner_pos,chkApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text_credit = (TextView) findViewById(R.id.text_credit);
        text_credit.setText("Developer by c.phakphum2");
        final EditText edit_site = (EditText) findViewById(R.id.edit_site);
        Button button_send = (Button) findViewById(R.id.button_send);
        Button button_data = (Button) findViewById(R.id.button_data);

        // Begin Spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        // spinner adapter
        spinner.setAdapter(new spinnerAdapter(this,R.layout.item_spinner,spinnerValues));
        // spinner event
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_pos=spinner.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mDBHelper = new DatabaseHelper(this);
        mDBHelper.getWritableDatabase();


        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DB_NAME);

        //Toast.makeText(this, database.toString(), Toast.LENGTH_LONG).show();

        copyDatabase(this);
        //Check exists database
        /*if(!database.exists()){
            copyDatabase(this);
            //Toast.makeText(this, "No Have", Toast.LENGTH_LONG).show();
            //if(copyDatabase(this)) {
            //    Toast.makeText(this, "Copy database success", Toast.LENGTH_LONG).show();
            //} else {
            //    Toast.makeText(this, "Copy data error", Toast.LENGTH_LONG).show();
            //    return;
            //}
        }else{
            //Toast.makeText(this, "Have", Toast.LENGTH_LONG).show();

            tbversion = new TableVersion();
            tbversion = mDBHelper.Get_Version();
            if (!DatabaseHelper.DB_VERSION.equals(tbversion.getVersion())) {
               deleteDatabase(database.toString());
                copyDatabase(this);
                //if(copyDatabase(this)) {
                //    Toast.makeText(this, "Copy database new version success", Toast.LENGTH_LONG).show();
                //} else {
                //    Toast.makeText(this, "Copy data error", Toast.LENGTH_LONG).show();
                //    return;
                //}
              } else {
                //Toast.makeText(this, "Last Version: "+DatabaseHelper.DB_VERSION +" = "+tbversion.getVersion(), Toast.LENGTH_LONG).show();
            }
        }*/

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check EditText
                if (edit_site.getText().toString().isEmpty() || edit_site.getText().length()<5) {
                    MyDialog alert = new MyDialog();
                    alert.myDialog(MainActivity.this,"Alert" , "กรุณาป้อน site code ให้ถูกต้อง");
                    return;
                }
                //Get data
                tbsitedata = new TableSiteData();
                tbsitedata = mDBHelper.Get_SiteData(edit_site.getText().toString());

                String msg1 = "";
                String msg2 = "";
                chkApp = 0;
                if (spinner_pos == 0 && tbsitedata != null) {
                    msg1 = "Pat " + edit_site.getText().toString() + " " + tbsitedata.getIP();
                } else if (spinner_pos == 0 && tbsitedata == null) {
                    msg1 = edit_site.getText().toString();
                } else if (spinner_pos == 1) {
                    msg1 = "Checkbatt " + edit_site.getText().toString();
                } else if (spinner_pos == 2) {
                    msg1 = "Plan " + edit_site.getText().toString();
                }

                if (tbsitedata != null) {
                    msg2="IP : " + tbsitedata.getIP();
                } else {
                    msg2="IP : No Data";
                }
                var = msg2 + "\nSyntex : " + msg1 +"\n\nตรวจสอบผลที่กล่องข้อความ";
                MyDialog alert = new MyDialog();
                alert.myDialog(MainActivity.this,"SSMS Send" , var);
                sendSMS("4822330", msg1); //custom class sms
                Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_LONG).show();
                edit_site.setText("");
            }
        });

        button_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check EditText
                if (edit_site.getText().toString().isEmpty() || edit_site.getText().length()<5) {
                    MyDialog alert = new MyDialog();
                    alert.myDialog(MainActivity.this,"Alert" , "กรุณาป้อน site code ให้ถูกต้อง");
                    return;
                }
                //Get data
                tbsitedata = new TableSiteData();
                tbsitedata = mDBHelper.Get_SiteData(edit_site.getText().toString());
                if (tbsitedata != null) {
                      var = "Site : " + tbsitedata.getSite() +
                            "\nIP : " + tbsitedata.getIP()+
                            "\nBrand : " + tbsitedata.getBrand()+
                            "\nZone : " + tbsitedata.getZone();
                } else {    var = "No Data";    }
                MyDialog alert = new MyDialog();
                alert.myDialog(MainActivity.this,"SSMS Data" , var);
                //edit_site.setText("");
            }
        });

    }// Main Method

    private boolean copyDatabase(Context context) {
        //Toast.makeText(this, "test1", Toast.LENGTH_LONG).show();
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DB_NAME);
            String outFileName = DatabaseHelper.DB_LOCATION + DatabaseHelper.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            //Log.w("MainActivity","DB copied");
            //Toast.makeText(this, "DB copied", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
            return false;
        }
    }   // Method copyDatabase

    private class spinnerAdapter extends ArrayAdapter<String> {
        public spinnerAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.item_spinner, parent, false);
            TextView item_spinner_title = (TextView) mySpinner.findViewById(R.id.item_spinner_title);
            item_spinner_title.setText(spinnerValues[position]);

            return mySpinner;
        }
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }   // inner Class spinnerAdapter

    private void sendSMS(String phoneNo,String msgSMS){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, msgSMS, null, null);
        chkApp=1;
    }   // Method sendSMS

}   // Main Class
