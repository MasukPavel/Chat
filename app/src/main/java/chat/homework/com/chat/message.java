package chat.homework.com.chat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by pasha on 14.02.16.
 */
public class message {
    boolean isMy;
    Date date;
    String name;
    String text;
    ImageView picture;

    public message(boolean isMy, String name, String text){
        this.isMy=isMy;
        this.name=name;
 //       this.date=date;
        this.text=text;
 //       this.picture=getUsersAvatar(name);

    }

    public void addMessage(){
        LinearLayout ll=MainActivity.ll;
        LayoutInflater lf=MainActivity.inflator;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View item;
        if(isMy) {
            item = lf.inflate(R.layout.my_message, ll, false);
            TextView msg=(TextView)item.findViewById(R.id.textView3);
            msg.setBackgroundResource(R.drawable.my_message_2);
            lp.gravity = Gravity.RIGHT;
            lp.leftMargin=200;
            if(MainActivity.lastName.equals(name)) {
                lp.topMargin = 20;
            }else {
                lp.topMargin = 50;
                MainActivity.lastName=name;
            }

        }else{
            if(MainActivity.lastName.equals(name)) {
                item=lf.inflate(R.layout.message,ll,false);
                TextView msg=(TextView)item.findViewById(R.id.textView3);
                msg.setBackgroundResource(R.drawable.message_2);
                lp.gravity = Gravity.LEFT;
                lp.rightMargin=200;
                lp.topMargin = 20;
            }else{
                item=lf.inflate(R.layout.first_message,ll,false);
                TextView msg=(TextView)item.findViewById(R.id.textView3);
                msg.setBackgroundResource(R.drawable.my_message_2);
                TextView nameView=(TextView)item.findViewById(R.id.name);
                nameView.setText(name);
                lp.topMargin = 50;
                lp.rightMargin=200;
                lp.gravity = Gravity.LEFT;
            }
        }
        ll.addView(item,lp);

    }
}
