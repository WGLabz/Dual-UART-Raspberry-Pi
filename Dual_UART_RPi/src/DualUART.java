import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Serial;

public class DualUART {
	int fd=-1;
	GpioPinDigitalOutput selectorPin;
	public static void main(String[] args) {

		try {
			new DualUART().init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	void init() throws InterruptedException{
	    GpioController gpio = GpioFactory.getInstance();
	    selectorPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
	    
	    
		fd = Serial.serialOpen(Serial.DEFAULT_COM_PORT, 9600);
		
		if (fd == -1) {
	      System.out.println("unable to open serial port.");
	      return;
	    }
		while(true){
			System.out.println(sendData("Hello World0\r\n",0)); //send to device 1
			Thread.sleep(2000);
			System.out.println(sendData("Hello World1\r\n",1)); //send to device 2
			Thread.sleep(2000);
		}
//		sendData("Hello World",0);
//		sendData("Hello World",1);
//		
	}
	private String sendData(String data, int port) throws InterruptedException {
		char[] responseData= new char[200];
		// TODO Auto-generated method stub
		int i=0;
		Serial.serialFlush(fd); //Get rid of old data if any in the Tx or Rx Buffer
		Thread.sleep(200);
		
		if(port==1)
			selectorPin.high(); //Select device 1
		else
			selectorPin.low(); //Select device 2
		
		Serial.serialPuts(fd, data);
		Thread.sleep(1000);

		int dataavail = Serial.serialDataAvail(fd);
		
		while (dataavail > 0){
			responseData[i]=(char)Serial.serialGetByte(fd);
            dataavail = Serial.serialDataAvail(fd);
            i++;
		}
		
		System.out.println(i); //send to device 2
		return new String(responseData);

	}

}
