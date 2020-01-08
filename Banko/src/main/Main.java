package main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	
	public static void main(String[] args) {
		
		Banko b = new Banko();
		/*while(b.geti()!=1900000)
			b.mon_transiro((byte) (Math.random()*127), (byte) (Math.random()*127));*/
		
		for(byte i = 0; i<127; i++) {
			
			Thread h = new Thread(new mon_transiro1(b, i));
			
			h.start();
			//System.out.println(b.geti());
			try {
				
				if(i==126)
					h.join();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		b.imprimikontn();
		
	}
	
}

class Banko {
	
	private final double kontj[];
	private int i;
	//private byte iii;
	private Lock l;
	private Condition suficxmon;
	
	public Banko() {
		
		l = new ReentrantLock();
		suficxmon = l.newCondition();
		kontj = new double[127];
		i = 0;
		//iii = 0;
		
		for(int i = 0; i<127;i++)
			kontj[i] = 3000;
		
	}
	//Ankaŭ vi povas uzi la vorton "synchronized"
	void mon_transiro(byte devenaK, byte destinaK, int h) {
		
		/*if(devenaK == destinaK)
			return;*/
		
		l.lock();
		
		try {
			
			//double kvanto = Math.round(Math.random() * 3000);
			double kvanto = (int) (Math.random() * 1000);
			
			while(kontj[devenaK]<kvanto)
				suficxmon.await();
			
			kontj[devenaK] -= kvanto;
			kontj[destinaK] += kvanto;
			i++;
			//iii++;
			suficxmon.signalAll();
			
			System.out.println("La konto " + (devenaK + 1) + " transdonis la monon al la konto " + (destinaK + 1) +
					", la nombro estas " + kvanto + " bolivaroj.\nLa tuto estas: " + getTut() +
					", \u011Di estas la " + (h + 1) + "-a transdonado de " + Thread.currentThread().getName() +
					" kaj la " + i + "-a transdonado");
			if(isCXiuThreadsWaiting()) {
				
				imprimikontn();
				
			}
			/*if(iii==50) {
				
				iii=0;
				Thread.dumpStack();Thread.currentThread().getThreadGroup().list();
				for(byte b = 0; b<100;b++) {
					
					//System.out.println();
					System.out.println(Thread.currentThread().getThreadGroup());
					
					
				}
			}*/
			/*if(kontj[devenaK]==0) {
				
				imprimikontn();
				System.out.println("Bonege! la " + (devenaK + 1) +
				"-a konto jam estas malplenri\u0109a, Feli\u0109on al vi kaj la viaj por ke \u0109io estu la\u016D via deziro!");
				System.exit(0);
				
			}*/
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			
			l.unlock();
			
		}
		
	}
	
	boolean isCXiuThreadsWaiting() {
		
		Thread j[] = new Thread[127];
		Thread.currentThread().getThreadGroup().enumerate(j);
		byte b = 0;
		byte c = 0;
		
		for(Thread g:j) {
			try {
				if(g.getState().toString().equals("WAITING"))
					b++;
			}catch(NullPointerException e) {
				c--;
			}
		}
		return b == (c - 1);
	}
	
	double getTut() {
		
		double h = 0;
		
		for(byte i = 0; i<127;i++)
			h += getKontj(i);
		
		return h;
		
	}
	
	int geti() {
		return i;
	}
	
	void seti(int ii) {
		i = ii;
	}
	
	public double getKontj(byte i) {
		return kontj[i];
	}
	
	public void setKontj(byte i, double h) {
		kontj[i] = h;
	}
	
	void imprimikontn() {
		
		byte g[] = new byte[26];
		byte ij = 0;
		
		byte gh[] = new byte[127];
		byte ih = 0;
		
		for(byte ii = 0;ii<127;ii++) {
			
			System.out.print("La " + (ii + 1) + "-a konto havas: " + getKontj(ii) + " bolivarojn. ");
			
			if(getKontj(ii)>=15000) {
				
				g[ij] = ii;
				ij++;
				
			} else if(getKontj(ii)<=10) {
				
				gh[ih] = ii;
				ih++;
				
			}
			
			if(ii%2!=0)
				System.out.println();
			
		}
		
		if(ij==0) {
			
			g = new byte[48];
			
			for(byte ii = 0;ii<127;ii++)
				if(getKontj(ii)>=8000) {
					
					g[ij] = ii;
					ij++;
					
				}
		
		}
		
		System.out.println("\n");
		System.out.println("La plej ri\u0109aj kontoj estas:");
		
		for(byte k:g) {
			
			if(k==0)
				break;
			
			System.out.println("La " + (k + 1) + "-a konto havas: " + getKontj(k) + " bolivarojn. ");
			
		}
		
		
		System.out.println("\n");
		System.out.println("La malplej ri\u0109aj kontoj estas:");
		
		for(byte k:gh) {
			
			if(k==0)
				break;
			
			System.out.println("La " + (k + 1) + "-a konto havas: " + getKontj(k) + " bolivarojn. ");
			
		}
		
		System.out.println("La lasta transdonado estas la " + i + "-a, kaj la tuto estas: " + getTut() + ". Farita de: " +
				Thread.currentThread().getName());
		
	}
	
}

class mon_transiro1 implements Runnable {
	
	private byte devenaK;
	private byte destinaK;
	private static Banko b;
	private int hi = 0;
	
	public mon_transiro1(Banko bb, byte dk) {
		
		b = bb;
		devenaK = dk;
		
	}
	
	@Override
	public void run() {
		while(true) {
		//for(; hi<200; hi++) {
			
			//devenaK = (byte) (Math.random()*127);
			
			do
				destinaK = (byte) (Math.random()*127);
			while(devenaK == destinaK);
			
			/*double kvanto = (int) (Math.random() * b.getKontj(devenaK));
			
			b.setKontj(devenaK, b.getKontj(devenaK) - kvanto);
			b.setKontj(destinaK, b.getKontj(destinaK) + kvanto);
			b.seti(b.geti() + 1);
			
			System.out.println("La konto " + (devenaK + 1) + " transdonis la monon al la konto " + (destinaK + 1) +
					", la nombro estas " + kvanto + " bolivaroj.\nLa tuto estas: " + b.getTut() + ", \u011Di estas la " + b.geti() +
					"-a transdonado.\nFarita de: " + Thread.currentThread().getName());*/
			
			//b.mon_transiro(devenaK, destinaK, this);
			b.mon_transiro(devenaK, destinaK, hi);
			
		}
		
	}
	
}