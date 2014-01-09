import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*; 

import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.IChimpDevice;
import com.android.chimpchat.core.IChimpImage;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

import org.sikuli.script.Finder;

public class JFrameDemo{ 
	
	private JButton mbc;
    public static void main(String args[]){ 
    	JFrameDemo demo = new JFrameDemo();
    	demo.go1();
    } 

    public void frameEasy(){
        JFrame frame = new JFrame("JFrameDemo"); 
        JButton button = new JButton("Press Me"); 
  
        //first way to do that 
        // frame.getContentPane().add(button,BorderLayout.CENTER); 
  
  
        //another way to set the Pane    
        JPanel contentPane = new JPanel(); 
        contentPane.setLayout(new BorderLayout()); 
        contentPane.add(button,BorderLayout.CENTER); 
        frame.setContentPane(contentPane); 
 
        frame.setSize(200, 300);
        //frame.pack(); 
        frame.setVisible(true); 
    }
    public static void go(){ 
        JFrame frame = new JFrame("Flow Layout"); 
        Container contentPane = frame.getContentPane(); 
      
        contentPane.setLayout(new FlowLayout()); 
      
        JButton btn1 = new JButton("OK"); 
        JButton btn2 = new JButton("Open"); 
        JButton btn3 = new JButton("Close"); 
      
        contentPane.add(btn1); 
        contentPane.add(btn2); 
        contentPane.add(btn3); 
      
        frame.setSize(300,200); 
        frame.setVisible(true); 
    }
    
    public void go1(){
 	 	JFrame frame = new JFrame("Border Layout");
 	 	JButton be = new JButton("East");
 	 	JButton bw = new JButton("West");
 	 	JButton bn = new JButton("North");
 	 	JButton bs = new JButton("South");
 	 	 mbc = new JButton("Center");
 	
 	 	mbc.addActionListener(mActionListener);
 	 	frame.getContentPane().add(be,"East");
 	 	frame.getContentPane().add(bw,BorderLayout.WEST);
 	 	frame.getContentPane().add(bn,BorderLayout.NORTH);
 	 	frame.getContentPane().add(bs,BorderLayout.SOUTH);
 	 	frame.getContentPane().add(mbc,BorderLayout.CENTER);
 	 
 	 	frame.setSize(350,200);
 	 	frame.setVisible(true);
    }
    
    private ActionListener mActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == mbc){
				try {
					monkeyRunnerDemo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//getDevice();
			}
		}
	};
    
    private void monkeyRunnerDemo() throws IOException{
    	//AndroidDebugBridge.init(false);
    	AdbBackend adb = new AdbBackend(); 
    	IChimpDevice device = adb.waitForConnection(); 

        // Actions should go here 
        //device.touch(125, 1000,TouchPressType.DOWN_AND_UP); 
    	Process pcProcess =Runtime.getRuntime().exec("adb shell am start -a android.settings.LOCALE_SETTINGS");
        try {
			pcProcess.waitFor();
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //device.press("KEYCODE_HOME",TouchPressType.DOWN_AND_UP) ;
        IChimpImage iImage = device.takeSnapshot();
        iImage.writeToFile("D:\\1.png", "png");
//        byte[] b=iImage.convertToBytes("png");
//        BufferedImage image = device.takeSnapshot().createBufferedImage();
//        ImageIO.write(image, "png", new File("C:\\tmp.png")); 
        device.dispose(); 
        
        Finder finder = new Finder("D:\\1.png");
        finder.find("D:\\test.png");
        if(finder.hasNext()){
        	System.out.println("Find it");
        }else{
        	System.out.println("not Find it");
        }
    }
    
    private void getDevice(){
        IDevice device = null;
        AndroidDebugBridge bridge = AndroidDebugBridge
                      .createBridge("adb", true);// 如果代码有问题请查看API，修改此处的参数值试一下
        waitDevicesList(bridge);
        IDevice devices[] = bridge.getDevices();
         
        for (int i = 0; i < devices.length; i++) {
               System.out.println(devices[i].toString());
        }

    }
    
    private void waitDevicesList(AndroidDebugBridge bridge) {
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
               try {
                      Thread.sleep(500);
                      count++;
               } catch (InterruptedException e) {
               }
               if (count > 60) {
                      System.err.print("等待获取设备超时");
                      break;
               }
        }
 }
} 