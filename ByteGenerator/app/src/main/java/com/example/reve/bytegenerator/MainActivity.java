package com.example.reve.bytegenerator;

import android.os.AsyncTask;
import android.support.annotation.Size;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

import static com.example.reve.bytegenerator.MainActivity.len;
import static com.example.reve.bytegenerator.R.id.text;
import static com.example.reve.bytegenerator.R.id.textView2;

public class MainActivity extends AppCompatActivity {
    public static int offset,len;
    public static boolean check=true;

    public EditText et1,et2;
    public static TextView textView1;
    public static TextView textView2;
    public static int sendCount=0;
    public static int receivedCount=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

    }

    void stopSendingData(View w){
        check=false;
    }


    void send_data(View v) throws SocketException {
        sendCount=0;
        receivedCount=0;
        offset=Integer.parseInt(et1.getText().toString());
        len=Integer.parseInt(et2.getText().toString());

        DatagramSocket ds=new DatagramSocket();

        Thread mySender=new MySender(ds);
        mySender.start();
        Thread myReceiver=new MyReceiver(ds);
        myReceiver.start();

    }






    class MySender extends Thread {
        DatagramSocket ds;
        public MySender(DatagramSocket ds) {
            this.ds=ds;
        }

        @Override
        public void run() {

            int i=0;
            System.out.println("Udp Client Started...........");
            try {
                byte[] b = new byte[2048];
                InetAddress ia = InetAddress.getByName("191.101.189.83");
                DatagramPacket dp = new DatagramPacket(b, len, ia, 1111);
                while (i<100) {
                    int size = Utility.getRandomData(b, 0, len);
                    String message=Utility.bytesToHex(b,0,len);
                    dp.setData(b,0,len);
                    ds.send(dp);
                    System.out.println("Send from client  :-->  " + message);
                    sendCount+=len;
                    System.out.println("Number of Packet Sent:----> "+sendCount);
                    i++;
                    Thread.sleep(200);
                }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }


        }

    class MyReceiver extends Thread {
        DatagramSocket ds;
        public MyReceiver(DatagramSocket ds) {
            this.ds=ds;
        }

        @Override
        public void run() {
            try{
                byte[] b1= new byte[2048];
                DatagramPacket dp1 = new DatagramPacket(b1, 0, len);
                while(true) {

                    ds.receive(dp1);

                    String m = Utility.bytesToHex(b1);

                    System.out.println("Received at client:---> " + m);
                    receivedCount+=dp1.getLength();
                    System.out.println("Number of Packet Received:----> "+receivedCount);
//                    System.out.println("\n");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

//    public class MyThread extends Thread{
//
//
//        @Override
//        public void run() {
//
//            byte[] b=new byte[2048];
//            int size=Utility.getRandomData(b,0,len);
//            message=Utility.bytesToHex(b,0,len);
//            for(int j=offset;j<len-1;j++){
//                System.out.print(b[j]+" ");
//            }
//            System.out.println(b[len-1]);
//
//            System.out.println("Udp Client Started...........");
//            try{
////                byte[] b2=message.getBytes();
//                DatagramSocket ds=new DatagramSocket();
//                InetAddress ia=InetAddress.getByName("191.101.189.83");
//                DatagramPacket dp=new DatagramPacket(b,len,ia,1111);
//                ds.send(dp);
////                System.out.println("send from client---> : "+b2.length);
//                System.out.println("send from client---> : "+len);
//                System.out.println("Send from client :-->  "+ message);
////                sendCount=sendCount+b2.length;
//                sendCount=sendCount+len;
//                byte[] b1= new byte[2048];
//                DatagramPacket dp1=new DatagramPacket(b1, dp.getOffset(),dp.getLength());
//                ds.receive(dp1);
//
//                String received= new String(dp1.getData(),0,dp1.getLength());
//                System.err.println("Received at client:-->  "+ received);
//
//                String s1=Utility.bytesToHex(b1);
//                String s2=Utility.bytesToHex(b1,offset,len);
//
////                System.out.println(s1);
////                System.out.println(s2);
//
//                message=s2;
//                System.out.println("Received Length of message------- >>>>>>  "+message.length());
//
//                receivedCount=receivedCount+dp1.getLength();
//
//                getCount(sendCount+"",receivedCount+"");
//
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//            System.out.println("---sent bits---> "+sendCount+"---------received bits---> "+receivedCount);
//        }
//    }

//    public class Download extends AsyncTask<String, Void, String>{
//        String message;
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            System.out.println("Udp Client Started...........");
//            message=params[0];
//
//
//            try{
//                DatagramSocket ds=new DatagramSocket();
//                byte[] b=message.getBytes();
//                InetAddress ia=InetAddress.getByName("191.101.189.83");
//                DatagramPacket dp=new DatagramPacket(b, b.length,ia,1111);
//                ds.send(dp);
//                System.err.println("send from client---> : "+message);
//
//                byte[] b1= new byte[2048];
//                DatagramPacket dp1=new DatagramPacket(b1, dp.getOffset(),dp.getLength());
//                ds.receive(dp1);
//
//                String received= new String(dp1.getData(),0,dp1.getLength());
//                System.err.println("Received at client:-->  "+ received);
//                receivedCount=receivedCount+received.length();
//
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//
//
//
//
//
//            return "Executed!";
//
//        }
//
//    }



}
