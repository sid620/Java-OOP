import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

class Data {
	
	private int result;
	private boolean pChance;
	private boolean mChance;
	private Object lock;
	private int nop;
	
	public Data(){ // 8 Marks
		Scanner scan=null;
		try {
			scan= new Scanner(new FileInputStream("input.txt"));
			}
		catch(FileNotFoundException e) {
			System.out.println("File could not be opened.");
			System.exit(0);
		}
		finally {
			this.result=scan.nextInt();
			this.pChance=scan.nextBoolean();
			this.mChance=scan.nextBoolean();
			this.nop=scan.nextInt();
			this.lock=new Object();
			scan.close();
		}
		//Q.1 Write your code here 
	}

	public int getResult() {
		return result;
	}

	public void setResult(int value) {
		this.result = value;
	}

	public boolean isPChance() {
		return pChance;
	}

	public void setPChance(boolean pChance) {
		this.pChance = pChance;
	}

	public boolean isMChance() {
		return mChance;
	}

	public void setMChance(boolean mChance) {
		this.mChance = mChance;
	}

	public Object getLock() {
		return lock;
	}

	public int getNoP() {
		return nop;
	}	
}

class Monitor implements Runnable{

	private Data d;
	private int tails = 0;
	private int heads = 0;
	
	public Monitor(Data d){
		this.d = d;
	}
	
	public void run() {
		int i;
		PrintWriter outStream=null;
		Object obj = d.getLock();
		for(i=0;i<d.getNoP();i++)
		synchronized(obj) {
			while(d.isMChance()==false) {
				try {
					obj.wait();
				}
				catch(Exception e) {
					
				}
			}
			System.out.println("monitor is going to read value ...");
			if(d.getResult() == 0) tails++; else heads++;
			d.setMChance(false);
			//d.setPChance(true);
			//PrintWriter p=null;
		    try {
		    	outStream=new PrintWriter(new FileOutputStream("output.txt",true));
		    	outStream.println(this.heads+" "+this.tails);
		    }
		    catch(Exception e) {
		    	System.out.println("Error!");
		    	System.exit(0);
		    }
		    finally {
		    	
		    	System.out.println(heads+" "+tails);
		    }
		    obj.notifyAll();
		}
		outStream.close();
		//Q.2 (a) - 2 Marks
		
		//Q.2 (b) - 3 Marks
		
		//Q.2 (c) - 6 Marks	
		
		/*
		 * Uncomment the following two lines after writing
		 * solution for Q.2(a), Q.2(b)and Q.2(c)		 
		 */
		//System.out.println("monitor is going to read value ...");
		//if(d.getResult() == 0) tails++; else heads++;
		
		//Q.2 (d) - 6 Marks
		
		//Q.2 (e) - 3 Marks				
	}
}

class Player implements Runnable{
	private Data d;
	Random rand = new Random();
	
	public Player(Data d) {
		this.d = d;
	}
	
	public void run() {
		
		Object obj = d.getLock();
		synchronized(obj) {
			while(d.isMChance()==true ) {
				try {
					obj.wait();
				}
				catch(Exception e) {}
			}
			d.setResult(rand.nextInt(2));
			System.out.println("player is going to write value :" + d.getResult());
			d.setMChance(true);
			//d.setPChance(false);
			obj.notifyAll();
		}
		//Q.3(a) - 3 Marks
		
		//Q.3(b) - 3 Marks
		
		/*
		 * Uncomment the following two lines after writing
		 * solution for Q.3(a) and Q.3(b)		 
		 */
		//d.setResult(rand.nextInt(2));
		//System.out.println("player is going to write value :" + d.getResult());
		
		//Q.3(c) - 6 Marks			
	
	} //end of run method		
}

class Lab10{
	
	public static void main(String[] args){
		
		Data data = new Data();
		Thread[] players;
		Thread monitor;
		players=new Thread[data.getNoP()];
		monitor=new Thread(new Monitor(data));
		for(int i=0;i<data.getNoP();i++)
			players[i]=new Thread(new Player(data));
		for(int i=0;i<data.getNoP();i++)
			players[i].start();
		monitor.start();
		//Q.4(a) - 7 Marks
		
		//Q.4(b) - 3 Marks
	}
}
