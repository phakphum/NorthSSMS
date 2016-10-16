package app.phakphuc.northssms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Phakphum on 25-Sep-16.
 */
public class MyDialog {
    public void myDialog(Context context, String strTitle, String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);   // ทำให้ปุ่ม Back เป็นอัมพาธ
        builder.setTitle(strTitle);
        //builder.setIcon(R.drawable.danger);
        builder.setMessage("\n"+strMessage);
        // ปุ่มใน Dialog มีได้สูงสุด 3 ปุ่ม (positive ,Negative ,Neutral)
        builder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();  // ทำให้ Dialog หายไป
            }
        });
        builder.show();
    }   // myDialog Method
}
