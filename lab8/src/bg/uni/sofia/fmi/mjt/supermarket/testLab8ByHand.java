package bg.uni.sofia.fmi.mjt.supermarket;

public class testLab8ByHand {
	public static void main(String[] args) throws InterruptedException {
		CashDesk cashDesk = new CashDeskImpl();
		Vault vault = new Vault(cashDesk);
		vault.start();
		Thread[] threads = new Thread[100];
		for(int i = 0; i < 100; i++) {
			Customer customer = new Customer(cashDesk, 10, 2);
			threads[i] = new Thread(customer);
			threads[i].start();
		}
		
		for(int i = 0; i < 100; i++) {
			threads[i].join();
		}

		System.out.println(cashDesk.getAmount());
		System.out.println(vault.getAmount());
		
	}
}


