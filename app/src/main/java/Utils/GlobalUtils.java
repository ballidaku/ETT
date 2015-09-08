package Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ameba.sharanpal.ett.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ameba on 7/9/15.
 */
public class GlobalUtils
{

    public static Toast t;





    public static void show_Toast(String text, Context con)
    {
        if(t != null)
        {
            t.cancel();
        }
        t = Toast.makeText(con, text, Toast.LENGTH_SHORT);

        t.show();

    }

    public static void show_ToastCenter(String text, Context con)
    {
        if(t != null)
        {
            t.cancel();
        }
        t = Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();

    }







    public static boolean is_valid_email(String g)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(g);
        return matcher.matches();
    }
    public static void put_to_sharedprefrences(Context context,String key,String value)
    {
        SharedPreferences sp = context.getSharedPreferences("ETT", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static Dialog dialog=null;
    public static void showAlertDialog(final Context context,String title,String message,boolean yesNo,View.OnClickListener onClickG)
    {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.dialogText);
        text.setText(message);
        final TextView btnyes= (TextView) dialog.findViewById(R.id.btnyes);
        final TextView btnno =(TextView)dialog.findViewById(R.id.btnno);
        if(yesNo)
        {
            btnyes.setText("Yes");
            btnno.setVisibility(View.VISIBLE);

            btnyes.setOnClickListener(onClickG);

            btnno.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    return false;
                }
            });
            btnno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    dialog.dismiss();
                }
            });
        }
        else
        {
            btnyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });

        }

        dialog.show();

    }


//    private static void loadGrayBitmap(Bitmap src) {
//        if (src != null) {
//
//            int w = src.getWidth();
//            int h = src.getHeight();
//            RectF rectF = new RectF(w/4, h/4, w*3/4, h*3/4);
//            float blurRadius = 100.0f;
//
//            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            Canvas canvasResult = new Canvas(bitmapResult);
//
//            Paint blurPaintOuter = new Paint();
//            blurPaintOuter.setColor(0xFFffffff);
//            blurPaintOuter.setMaskFilter(new
//                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.INNER));
//            canvasResult.drawBitmap(bitmapMaster, 0, 0, blurPaintOuter);
//
//            Paint blurPaintInner = new Paint();
//            blurPaintInner.setColor(0xFFffffff);
//            blurPaintInner.setMaskFilter(new
//                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
//            canvasResult.drawRect(rectF, blurPaintInner);
//
//            imageResult.setImageBitmap(bitmapResult);
//        }
//    }

    public static Bitmap StringToBitMap(String encodedString) {
        try
        {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        }
        catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }


}
