package chat.homework.com.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {
    private static final int SLEEP_TIME = 1000;
    private static final int READ_ATTEMPTS = 10;
     static boolean run=false;
    static ClientThread clientThread;
    static int count=0;

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
        private boolean finished = false;

        private static final int SERVER_PORT = 4444;
        private static final String SERVER_IP = "46.101.96.234";

        @Override
        public void run() {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!finished) {
                    try {
                        Thread.sleep(1000);
                        if(count>0)
                            addNumber(getString());
                        count++;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                socket.close();
            } catch (IOException  e) {
                e.printStackTrace();
            }
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public String getString() throws IOException {
            if (reader.ready())
                return reader.readLine();
            return "No data";
        }

        public String getStringForce() throws IOException {
            return reader.readLine();
        }


    }

    public void addNumber(String s){
        final String s1=s;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout ll=(LinearLayout)findViewById(R.id.linlayout);
                TextView number=new TextView(MainActivity.this);
                number.setText(s1);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.addView(number,lp);

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(count>0)
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
