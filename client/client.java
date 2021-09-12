import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;


public class client implements KeyListener{

    private int count;
    private DatagramSocket sock = null;
    private DataOutputStream out = null;


    /* Constructor */
    public client(String ip,int port){
        try{
            sock = new DatagramSocket();
         //   out = new DataOutputStream(sock.getOutputStream());
            System.out.println("Connected!");
        }catch(Exception e){
            e.printStackTrace();
        }
        count = 0;
    }
    public client(){
        System.out.println("Created client");
    }
    /* key listener methods */
    public void keyPressed(KeyEvent k){
	String num = null;
        count++;
        if(k.getKeyCode() == KeyEvent.VK_UP && count == 1)
            num = "8";
        if(k.getKeyCode() == KeyEvent.VK_DOWN && count == 1)
            num = "2";
        if(k.getKeyCode() == KeyEvent.VK_LEFT && count == 1)
            num = "4";
        if(k.getKeyCode() == KeyEvent.VK_RIGHT && count == 1)
            num = "6";
	String addr = "192.168.1.16";
	try{
	    System.out.println("num is: "+num);
	    InetAddress address = InetAddress.getByName(addr);
	    DatagramPacket dir = new DatagramPacket(num.getBytes(),1,address,5002);
	    sock.send(dir);
	}catch(Exception e){
	    e.printStackTrace();
	}

    }
    public void keyReleased(KeyEvent k){
	String num = null;
        if(k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_DOWN){
            count = 0;
            System.out.println("released");
            num = "7";
        }
        if(k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_LEFT){
            count = 0;
            System.out.println("steer released");
            num = "3";
	}
	String addr = "192.168.43.150";
	try{
	    InetAddress address = InetAddress.getByName(addr);
	    DatagramPacket dir = new DatagramPacket(num.getBytes(),1,address,5002);
	    sock.send(dir);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    public void keyTyped(KeyEvent k){
        if(k.getKeyCode() == KeyEvent.VK_UP){
            System.out.println("OLA KALA typed");
        }
    }


    public static void main(String[] args){
        String ip = "192.168.43.150";
        int port = 5002;
        /* Frame settings */
        JFrame jf = new JFrame("key event");
        jf.setSize(400,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addKeyListener(new client(ip,port));
        jf.setVisible(true);
        /* Connection */
        /*try(Socket sock = new Socket(ip,port)){
            OutputStream out = sock.getOutputStream();
            PrintWriter writer = new PrintWriter(out,true);
            String num = args[0];
            writer.println(num);
            sock.close();
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }
}
