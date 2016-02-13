package chat.homework.com.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {
    private static final int SLEEP_TIME = 1000;
    private static final int READ_ATTEMPTS = 10;
     static boolean run=false;
    static ClientThread clientThread;
    static int count=0;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) throws  InterruptedException,IOException{
        if(run){
            Toast.makeText(this,"it is already started",Toast.LENGTH_SHORT).show();
        }else {
            ClientThread clientThread = new ClientThread();
            Thread thread = new Thread(clientThread);
            thread.start();
            TableRow tr = (TableRow) findViewById(R.id.tableRow);
            tr.setBackgroundColor(Color.GREEN);
            run = true;

        }
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
                        if(!s.equals(""))
                        pWriter.write(s);
                        text.setText("");
                    }
                });
                while (!finished) {
                    try {
                        Thread.sleep(100);
                        if(count>0)
                            s = getString();

                        if(!s.equals("")){
                            addMsg(s);
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
            return "no data";
        }

        public void write(){


        }

        public String getStringForce() throws IOException {
            return reader.readLine();
        }


    }

    public void addMsg(String s){
        final String s1=s;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout ll=(LinearLayout)findViewById(R.id.linlayout);
                TextView msg=new TextView(MainActivity.this);
                msg.setText(s1);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.addView(msg,lp);

            }
        });

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
