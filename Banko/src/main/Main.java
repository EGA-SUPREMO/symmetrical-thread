package main;

class Main1 {
	
	public static void main(String[] args) {
		
		Banko1 b = new Banko1();
		/*while(b.geti()!=1900000)
			b.mon_transiro((byte) (Math.random()*127), (byte) (Math.random()*127));*/
		
		for(int i = 0; i<30; i++) {
			
			Thread h = new Thread(new mon_transiro11(b));
			
			h.start();
			System.out.println(b.geti());
			try {
				h.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		b.imprimikontn();
		
	}
	
}

class Banko1 {
	
	private final double kontj[] = new double[127];
	private int i = 0;
	
	public Banko1() {
		
		for(int i = 0; i<127;i++)
			kontj[i] = 3000;
		
	}
	
	void mon_transiro(byte devenaK, byte destinaK, mon_transiro11 h) {
		
		/*if(devenaK == destinaK)
			return;*/
		
		double kvanto = Math.round(Math.random() * (kontj[devenaK]));
		
		kontj[devenaK] -= kvanto;
		kontj[destinaK] += kvanto;
		i++;
		
		/*System.out.println("La konto " + (devenaK + 1) + " transdonis la monon al la konto " + (destinaK + 1) +
				", la nombro estas " + kvanto + " bolivaroj.\nLa tuto estas: " + getTut() +
				", \u011Di estas la " + (h.gethi() + 1) + "-a transdonado de " + Thread.currentThread().getName() +
				" kaj la " + i + "-a transdonado");*/
		
		/*if(kontj[devenaK]==0) {
			
			imprimikontn();
			System.out.println("Bonege! la " + (devenaK + 1) + "-a konto jam estas malplenri\u0109a, Feli\u0109on al vi kaj la viaj por ke \u0109io estu la\u016D via deziro!");
			System.exit(0);
			
		}*/
		
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
		
		byte g[] = new byte[64];
		byte ij = 0;
		
		byte gh[] = new byte[64];
		byte ih = 0;
		
		for(byte ii= 0;ii<127;ii++) {
			
			System.out.print("La " + (ii + 1) + "-a konto havas: " + getKontj(ii) + " bolivarojn. ");
			
			if(getKontj(ii)>=10000) {
				
				g[ij] = ii;
				ij++;
				
			} else if(getKontj(ii)<=10) {
				
				gh[ih] = ii;
				ih++;
				
			}
			
			if(ii%2!=0)
				System.out.println();
			
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
		
		System.out.println("La lasta transdonado estas la " + i + ". Farita de: " + Thread.currentThread().getName());
		
	}
	
}

class mon_transiro11 implements Runnable {
	
	private byte devenaK;
	private byte destinaK;
	private static Banko1 b;
	private int hi = 0;
	
	public mon_transiro11(Banko1 bb) {
		b = bb;
	}
	
	@Override
	public void run() {
		
		for(; hi<30000000 ; hi++) {
			
			devenaK = (byte) (Math.random()*127);
			
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
			
			b.mon_transiro(devenaK, destinaK, this);
			
		}
		
	}
	
	int gethi() {
		return hi;
	}
	
}