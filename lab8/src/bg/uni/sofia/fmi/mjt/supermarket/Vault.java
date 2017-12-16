package bg.uni.sofia.fmi.mjt.supermarket;

public class Vault extends Thread {

	private CashDesk cashDesk;
	private double vaultAmount;
	
    public Vault(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
        vaultAmount = 0;
        this.setDaemon(true);
    }

    @Override
    public void run() {
		synchronized (cashDesk) {
			while(true) {
	    		try {
	    			cashDesk.wait();
	    			System.out.println("=============");
	    			System.out.println(cashDesk.getAmount());
	    			System.out.println("=============");
	    			vaultAmount += cashDesk.getAmount();
	    	    	cashDesk.setAmount(0);
	    		} catch (InterruptedException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
    		}
		}
    	
    }
    
    public  double getAmount() {
    	return vaultAmount;
    }

}
