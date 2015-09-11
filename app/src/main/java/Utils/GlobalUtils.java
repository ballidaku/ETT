package Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ameba.sharanpal.ett.R;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ameba on 7/9/15.
 */
public class GlobalUtils
{

    public static Toast t;
    public static Dialog forgot_dialog = null;

    public static void dismiss_dialog()
    {
      /*  if(global_dialog != null)
        {
            global_dialog.dismiss();
        }
        if(internet_dialog != null)
        {
            internet_dialog.dismiss();
        }*/

        if (forgot_dialog != null)
        {
            forgot_dialog.dismiss();
        }
    }

    public static void show_Toast(String text, Context con)
    {
        if (t != null)
        {
            t.cancel();
        }
        t = Toast.makeText(con, text, Toast.LENGTH_SHORT);

        t.show();

    }

    public static void show_ToastCenter(String text, Context con)
    {
        if (t != null)
        {
            t.cancel();
        }
        t = Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();

    }

    public static class Text implements TextWatcher
    {
        EditText ed;

        public Text(EditText ed)
        {
            this.ed = ed;
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {
            String str = arg0.toString();
            //Log.e("sf", str);
            if (str.length() > 0 && str.endsWith(" "))
            {
                //Log.e("", "Cannot begin with space");
                ed.setText(str.trim());
                ed.setSelection(str.length() - 1);
            }
            //Log.e("///////////////", "ITHEEEEEEEEEEEEEEEEEEEEE");

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {

        }

        @Override
        public void afterTextChanged(Editable arg0)
        {
            // TODO Auto-generated method stub

        }
    }

    public static boolean is_valid_email(String g)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\" +
                ".[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(g);
        return matcher.matches();
    }

    public static void put_to_sharedprefrences(Context context, String key, String value)
    {
        SharedPreferences        sp     = context.getSharedPreferences("ETT", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static Dialog dialog = null;

    public static void showAlertDialog(final Context context, String title, String message,
                                       boolean yesNo, View.OnClickListener onClickG)
    {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.dialogText);
        text.setText(message);
        final TextView btnyes = (TextView) dialog.findViewById(R.id.btnyes);
        final TextView btnno  = (TextView) dialog.findViewById(R.id.btnno);
        if (yesNo)
        {
            btnyes.setText("Yes");
            btnno.setVisibility(View.VISIBLE);

            btnyes.setOnClickListener(onClickG);

            btnno.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {

                    return false;
                }
            });
            btnno.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    dialog.dismiss();
                }
            });
        }
        else
        {
            btnyes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                }
            });
            btnno.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    dialog.dismiss();
                }
            });

        }

        dialog.show();

    }

    public static void get_Hash_key(Context con)
    {
        try
        {

            PackageInfo info = con.getPackageManager().getPackageInfo(con.getPackageName(),
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.e("name not found", e.toString());
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("no such an algorithm", e.toString());
        }
    }

    public static Bitmap StringToBitMap(String encodedString)
    {
        try
        {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }

    public static EditText show_forgot_dialog(final Context con, View.OnClickListener oc)
    {

        forgot_dialog = new Dialog(con);
        forgot_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgot_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        forgot_dialog.setContentView(R.layout.dialog_forgot_password);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(forgot_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        forgot_dialog.show();
        forgot_dialog.getWindow().setAttributes(lp);

        Button         send = (Button) forgot_dialog.findViewById(R.id.send);
        final EditText forgot_password_email;
        (forgot_password_email = (EditText) forgot_dialog.findViewById(R.id
                .forgot_password_email)).addTextChangedListener(new Text(forgot_password_email));

        send.setOnClickListener(oc);

        return forgot_password_email;
    }

}
