package chat.homework.com.chat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final int SLEEP_TIME = 1000;
    private static final int READ_ATTEMPTS = 10;
    static ClientThread clientThread;
    static int count=0;
    public static String name="";
    static String lastName="";
    static LinearLayout ll;
    static LayoutInflater inflator;
    static ArrayList<String> names=new ArrayList<String>();
    static int [] colorNumbers=new int[13];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text=(EditText)findViewById(R.id.editText);
        text.setMaxHeight(250);
        ll=(LinearLayout)findViewById(R.id.linlayout);
        inflator=getLayoutInflater();
        names.add("Server");
        colorNumbers[0]=0;
        try {
            onClick();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick() throws  InterruptedException,IOException{
        for(int i=1;i<13;i++){
            colorNumbers[i]=1+(int)(Math.random()*12);
        }

            ClientThread clientThread = new ClientThread();
            Thread thread = new Thread(clientThread);
            thread.start();

        }


    class ClientThread implements Runnable {
        public Socket socket;
        private BufferedReader reader;
        private PrintWriter pWriter;
        private boolean finished = false;

        private static final int SERVER_PORT = 4444;
        private static final String SERVER_IP = "46.101.96.234";

        @Override
        public void run() {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                String s = "";

                ImageButton ib=(ImageButton)findViewById(R.id.imageButton);
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText text=(EditText)findViewById(R.id.editText);
                        String s=text.getText().toString();
                        if(!s.equals("")) {
                            pWriter.println(s);
                            pWriter.flush();
                            text.setText("");
                        }

                    }
                });
                while (!finished) {
                    try {
                        Thread.sleep(100);
                            s = getString();
                        final String s1=s;
                        if(!s.equals("")){
                            if(s.contains(":")){
                            if(getName(s).equals(name)) {
                                addMsg(s,true);
                            }else{
                                addMsg(s, false);
                            }}else{
                                    addMsg(s, false);
                            }
                        }


                        count++;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException  e) {
                e.printStackTrace();
            }finally{
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public String getString() throws IOException {
            if (reader.ready())
                return reader.readLine();
            return "";
        }

        public void write(){


        }

        public String getStringForce() throws IOException {
            return reader.readLine();
        }


    }

    public void addMsg(String s,boolean isMy){
        if(!s.contains(":") && name==""){
            s="Server: "+s;
            name = s.substring(14);
        }
        final boolean isMy1=isMy;
        final String s1=s;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String s1Name=getName(s1);

                if(names.indexOf(s1Name)==-1){
                    names.add(s1Name);
                }
                String s1Text=getText(s1);
                MyMessage msg=new MyMessage(isMy1,s1Name,s1Text,getApplicationContext(),colorNumbers[names.indexOf(s1Name)]);
                msg.addMessage();
            }
        });

    }


    public String getName(String s){
        if(s.contains(":"))
        s=s.substring(0,s.indexOf(":"));
        else
        s="";
        return s;
    }

    public String getText(String s){
        if(s.contains(":"))
            s=s.substring(s.indexOf(':')+2,s.length());
        return s;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(clientThread.socket!=null)
            clientThread.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
