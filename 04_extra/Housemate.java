/** Class used to record the details of a housemate
 */
public class Housemate {
	private String name;
	private int room;
	private PaymentList payments;
      /** Constant defines the maximum number of housemates */
	public static final int MAX = 12;
	
      /** Constructor initialises the name and room number of the housemate
	*  and sets the payments made to the empty list
  	*  @param  nameIn: name of housemate
  	*  @param  roomIn: room number of housemate
	*/
	public Housemate(String nameIn, int roomIn) {
            name = nameIn;
            room = roomIn;
            payments = new PaymentList(MAX);
	}
	
      /** Records a payment for the housemate 
       *  @param paymentIn: payment made by housemate
       */
	public void makePayment(Payment paymentIn) {
            payments.addPayment(paymentIn); // call PaymentList method
	}
        
      /** Reads the name of the housemate
       *  @return Returns the name of the housemate
       */
      public String getName() {
            return name;
      }
      
      /** Reads the room of the housemate 
       *  @return Returns the room of the housemate
       */
      public int getRoom() {
            return room;
      }
      
      /** Reads the payments of the housemate 
       *  @return Returns the payments made by the housemate
       */
      public PaymentList getPayments() {
            return payments;
      }
      
      @Override
      public String toString() {
            return name + ", " + room + ", " + payments;
      }
}
