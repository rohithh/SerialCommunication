package com.rohith.sd.dataanalysis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import jssc.*;

class DataAnalysis{


    public static void main(String args[]) throws SerialPortException, IOException{
  
         /*       
            Pattern pattern = Pattern.compile("tty.");
            String[] portNames = SerialPortList.getPortNames("/dev", pattern);
            if (portNames.length == 0) {
                System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
                System.out.println("Press Enter to exit...");
                try {
                    System.in.read();
                } catch (IOException e) {
                     // TODO Auto-generated catch block
                      e.printStackTrace();
                }
             } 
            for(String str : portNames){
                System.out.println(str);
            }
        */
         
        File file = new File("/Users/Rohith/Desktop/data.txt");
        File file2 = new File("/Users/Rohith/Desktop/dataNew.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);        
        SerialPort serialPort = new SerialPort("/dev/tty.SLAB_USBtoUART");
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600,
                         SerialPort.DATABITS_8,
                         SerialPort.STOPBITS_1,
                         SerialPort.PARITY_NONE);
        long current = System.currentTimeMillis();
        String str;
        int count = 0;
        while(count != 100){
            while((str = serialPort.readString()) != null && !str.equals(" ")){

                System.out.print(str);
                bw.write(str);
                count++;
            }
            if(str != null)
                System.out.print(" ");
        }                      
        serialPort.closePort();

        FileReader fr = new FileReader("/Users/Rohith/Desktop/data.txt");
        BufferedReader br = new BufferedReader(fr);
        bw.close();
        fw.close();
        fw = new FileWriter(file2);
        bw = new BufferedWriter(fw);
        while((str = br.readLine()) != null){
            str = str.replaceAll("Vout=", "");
            str = str.replaceAll("DUST_CONC=", "");
            str = str.replaceAll("		", "\r\n");
            bw.write(str);
        }
    
        bw.close();
        fw.close();
        br.close();
        fr.close();
    }
}
