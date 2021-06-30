import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TimerGUI implements ActionListener
{
    boolean end=false;
    boolean isPressed=true;
    JFrame jf;
    JLabel jl;
    JTextField jMinutes;
    JTextField jSeconds;
    JButton start;
    JButton reset;
    private int mins;
    private int secs;

    TimerGUI(){
        jf=new JFrame();
        jl=new JLabel();
        jMinutes=new JTextField("Enter Minutes");
        jSeconds=new JTextField("Enter Seconds");
         start=new JButton("Start");
         reset=new JButton("Reset");


         start.setBounds(50,80,80,50);
         start.setVisible(true);
         start.addActionListener(this);

         reset.setBounds(150,80,80,50);
         reset.setFocusable(false);
         reset.setVisible(true);
         reset.addActionListener(this);

         jl.setBounds(50,100,80,100);
         jl.setText("0:0:0");
         jl.setVisible(true);

         jMinutes.setBounds(25,10,80,50);
         jMinutes.addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent k) {
                     if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || k.getKeyChar() == 8) {
                         jMinutes.setEditable(true);
                     } else {
                         jMinutes.setEditable(false);
                         jl.setText("Invalid Input Please Enter only Integer Numbers");
                 }
             }

         });

         jSeconds.setBounds(125,10,80,50);
         jSeconds.setVisible(true);
        jSeconds.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9' )|| k.getKeyChar() == 8) {
                    jSeconds.setEditable(true);
                } else {
                    jSeconds.setEditable(false);
                    jl.setText("Invalid Input Please Enter only Integer Numbers");
                }
            }
        });

         jf.add(jMinutes);
         jf.add(jSeconds);
         jf.add(start);
         jf.add(reset);
         jf.add(jl);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(300, 300);
        jf.setLayout(null);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start)
        {
                end=false;
            if(isPressed) {
                isPressed=false;
                end = false;
                mins = Integer.parseInt(jMinutes.getText());
                secs = Integer.parseInt(jSeconds.getText());
                countdown(mins, secs);
            }
        }
        if(e.getSource()==reset){
                mins = 0;
                secs = 0;
                jl.setText("0:0:0");
                end = true;

        }
    }

    private void countdown(int mins,int secs)
    {

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                int minutes=mins;
                int seconds=secs;
                int hour = 0;
                if(minutes>60) {
                    hour+=minutes/60;
                    minutes=minutes%60;
                }
                if(seconds>=60) {
                    minutes+=seconds/60;
                    seconds=seconds%60;
                }

                while(seconds >=0 && minutes>=0 && hour>=0 && !end)
                {
                    if(seconds>60) {
                        minutes+=seconds/60;
                        seconds=seconds%60;
                    }

                    if(minutes>60) {
                        hour+=minutes/60;
                        minutes=minutes%60;
                    }

                    if(minutes==0 && hour>0 && seconds==0)
                    {
                        minutes+=59;
                        seconds+=59;
                        hour--;
                    }



                    if(seconds==0 && minutes>0)
                    {
                        minutes--;
                        seconds+=59;
                    }

                    jl.setText(hour+":"+minutes+":"+seconds);
                    --seconds;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(end)
                {
                    isPressed=true;
                    return;
                }
                for (int i = 0; i < 10000; ++i) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toolkit.getDefaultToolkit().beep();
                }

                isPressed=true;
            }
        });
        t.start();
    }
}