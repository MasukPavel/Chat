package chat.homework.com.chat;

import android.content.Context;
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
public class MyMessage {
    boolean isMy;
    Date date;
    String name;
    String text;
    ImageView picture;
    Context c;
    int colorNumber;

    public MyMessage(boolean isMy, String name, String text, Context c,int colorNumber){
        this.c=c;
        this.isMy=isMy;
        this.name=name;
 //       this.date=date;
        this.text=text;
 //       this.picture=getUsersAvatar(name);
        this.colorNumber=colorNumber;

    }

    public void addMessage(){
        if(name.equals("")) {
            name = MainActivity.lastName;
            if(name.equals(MainActivity.name)){
                isMy=true;
            }
        }

        LinearLayout ll=MainActivity.ll;
        LayoutInflater lf=MainActivity.inflator;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View item;
        if(isMy) {
            item = lf.inflate(R.layout.my_message, ll, false);
            TextView msg=(TextView)item.findViewById(R.id.textView3);
            msg.setBackgroundResource(R.drawable.my_message_2);
            msg.setText(text);
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
                TextView msg=(TextView)item.findViewById(R.id.textView4);
                lp.gravity = Gravity.LEFT;
                lp.rightMargin=200;
                lp.topMargin = 10;
                msg.setText(text);
            }else{
                item=lf.inflate(R.layout.first_message,ll,false);
                TextView msg=(TextView)item.findViewById(R.id.textView6);
                TextView nameView=(TextView)item.findViewById(R.id.name);
                ImageView avatar=(ImageView)item.findViewById(R.id.imageView);
                switch (colorNumber){
                    case 0: avatar.setImageResource(R.drawable.server);
                        break;
                    case 1: avatar.setImageResource(R.drawable.user_1);
                        break;
                    case 2: avatar.setImageResource(R.drawable.user_2);
                        break;
                    case 3: avatar.setImageResource(R.drawable.user_3);
                        break;
                    case 4: avatar.setImageResource(R.drawable.user_4);
                        break;
                    case 5: avatar.setImageResource(R.drawable.user_5);
                        break;
                    case 6: avatar.setImageResource(R.drawable.user_6);
                        break;
                    case 7: avatar.setImageResource(R.drawable.user_7);
                        break;
                    case 8: avatar.setImageResource(R.drawable.user_8);
                        break;
                    case 9: avatar.setImageResource(R.drawable.user_9);
                        break;
                    case 10: avatar.setImageResource(R.drawable.user_10);
                        break;
                    case 11: avatar.setImageResource(R.drawable.user_11);
                        break;
                    case 12: avatar.setImageResource(R.drawable.user_12);
                        break;
                }
                nameView.setText(name);
                lp.topMargin = 50;
                lp.rightMargin=200;
                lp.gravity = Gravity.LEFT;
                msg.setText(text);
                MainActivity.lastName=name;
            }
        }
        ll.addView(item,lp);

    }
}
