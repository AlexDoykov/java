package bg.uni.sofia.fmi.mjt.supermarket;

public class CashDeskImpl implements CashDesk {

    private static final int MAX_CASH = 100;
    private double currentCash;

    public CashDeskImpl() {
    	currentCash = 0;
    }

    @Override
    public synchronized void serveCustomer(Customer customer) {
    	double amountToAdd = customer.buyGoods();
     	currentCash += amountToAdd;
    	if(currentCash > MAX_CASH) {
    		System.out.println(currentCash);
         	this.notify();
        }
    }

    @Override
    public synchronized double getAmount() {
        return currentCash;
    }

    @Override
    public synchronized void setAmount(double amount) {
       this.currentCash = amount;
    }

}