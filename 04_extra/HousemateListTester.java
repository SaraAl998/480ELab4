public class HousemateListTester {
  
    public static void main(String[] args) {
		char choice;
		int size;
		HousemateList hm_lst; // declare HousemateList object to test
	
		// get size of list
		System.out.print("Size of list? ");
		size = EasyScanner.nextInt();
		hm_lst = new HousemateList(size); // create object to test
		// menu
		do {
            // display options
            System.out.println();
            System.out.println("[1] Add housemate.");
            System.out.println("[2] Remove housemate.");
            System.out.println("[3] Check if the house is full.");
            System.out.println("[4] Find a housemate.");
            System.out.println("[5] Find the total number of housemates.");
            System.out.println("[6] Add payment from a housemate.");
            System.out.println("[7] See the payments of a housemate.");
            System.out.println("Press \"q\" to Quit.");
            System.out.println();
            System.out.print("Enter a choice [1-7 or \"q\"]: ");
            // get choice
            choice = EasyScanner.nextChar();
            System.out.println();
            // process choice
            switch(choice) {
                case '1': addHousemate(hm_lst); break;
                case '2': removeHousemate(hm_lst); break;
                case '3': checkIfHouseFull(hm_lst); break;
                case '4': findHousemate(hm_lst); break;
                case '5': findNumHousemates(hm_lst); break;
                case '6': addPaymentFromHousemate(hm_lst); break;
                case '7': findPaymentsOfHousemate(hm_lst); break;
                case 'q': System.out.println("Testing complete"); break;
                default: System.out.print("1-6 or q only");
            }
		} while (choice != 'q');
    }
  
    // Add
    private static void addHousemate(HousemateList hm_lst_in) {  
        // prompt for housemate details
        System.out.print("Enter Name: ");
        String name = EasyScanner.nextString();
        System.out.print("Enter Room Number: ");
        int room_num = EasyScanner.nextInt();
        // create new Housemate object from input
        Housemate h = new Housemate(name, room_num);


        // attempt to add new housemate to list of housemate
        boolean ok = hm_lst_in.addHousemate(h); // value of false sent back if unable to add
        if (!ok) { // check if item was not added
            System.out.println("ERROR: house full");
        }
    }

    // Display
    private static void removeHousemate(HousemateList listIn) {
        System.out.print("Enter housemate room number to remove: ");
        int num = EasyScanner.nextInt();
        listIn.removeHousemate(num);
        System.out.println("Remaining Housemates: ");
        System.out.println(listIn); // calls toString method of HousemateList
    }

    // Is full
    private static void checkIfHouseFull(HousemateList listIn) {  
        if (listIn.isFull()) {
            System.out.println("house is full");
        }
        else {
            System.out.println("house is NOT full");
        }
    }  
  
    // Get payment
    private static void findHousemate(HousemateList listIn) {  
        // prompt for and receive payment number
        System.out.print("Enter room number to retrieve: ");
        int num = EasyScanner.nextInt();
        // retrieve Payment object form list
        Housemate p = listIn.getHousemate(num); // returns null if invalid position
        if (p != null) {//
            System.out.println(p); // calls toString method of Housemate
        }
        else {
            System.out.println("INVALID ROOM NUMBER"); // invalid position error
        }   
    }

    // Get total
    private static void findNumHousemates(HousemateList listIn) {  
        System.out.print("TOTAL NUMBER OF HOUSEMATES: ");
        System.out.println(listIn.getTotal()); 
    }

    private static void addPaymentFromHousemate(HousemateList listIn) {  
        System.out.print("Enter housemate room number of housemate making payment: ");
        int num = EasyScanner.nextInt();
        System.out.print("Enter month of payment: ");
        String month = EasyScanner.nextString();
        System.out.print("Enter amount paid: ");
        double amount = EasyScanner.nextDouble();
        Payment p = new Payment(month, amount);
        listIn.getHousemate(num).makePayment(p); 
        System.out.println("Payment add successfully.");
    }

    // Get total paid
    private static void findPaymentsOfHousemate(HousemateList listIn) {  
        System.out.print("Enter housemate room number: ");
        int num = EasyScanner.nextInt();
        System.out.println(listIn.getHousemate(num)); 
    }
}

