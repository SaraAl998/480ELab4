
import java.io.*;


class HousemateFileHandler {
    
    public static void saveRecords(int noOfRoomsIn, HousemateList listIn) {
        
        try {
            FileOutputStream housemateFile = new FileOutputStream("housemates.dat");
            DataOutputStream housemateWriter = new DataOutputStream(housemateFile);
            housemateWriter.writeInt(listIn.getTotal());
            for (int i=1; i <= noOfRoomsIn; i++) {
                if (listIn.getHousemate(i) != null) {
                    housemateWriter.writeInt(listIn.getHousemate(i).getRoom());
                    housemateWriter.writeUTF(listIn.getHousemate(i).getName());
                    housemateWriter.writeInt(listIn.getHousemate(i).getPayments().getTotal());
                    for(int j = 1; j<= listIn.getHousemate(i).getPayments().getTotal(); j++) {
                        housemateWriter.writeUTF(listIn.getHousemate(i).getPayments().getPayment(j).getMonth());
                        housemateWriter.writeDouble(listIn.getHousemate(i).getPayments().getPayment(j).getAmount());
                    }
                }
            }
            housemateWriter.flush();
            housemateWriter.close();
        }
        catch(IOException ioe) {
            System.out.println("Error writing file");
        }
        
    }
    
    public static void readRecords(HousemateList listIn) {
        
        try {
            FileInputStream housemateFile = new FileInputStream("housemates.dat");
            DataInputStream housemateReader = new DataInputStream(housemateFile);
            
            int tempRoom = 0;
            String tempName = new String("");
            String tempMonth = new String("");
            double tempAmount = 0 ;
            Housemate temphousemate = null;
            Payment tempPayment =  null;
            int tot = 0;
            int totpay = 0;
            
            tot = housemateReader.readInt();
            for (int j = 1; j<=tot; j++) {
                tempRoom = housemateReader.readInt();
                tempName = housemateReader.readUTF();
                temphousemate = new Housemate(tempName,tempRoom);
                totpay = housemateReader.readInt();
                for(int k = 1; k <= totpay; k++) {
                    tempMonth = housemateReader.readUTF();;
                    tempAmount = housemateReader.readDouble();
                    tempPayment = new Payment(tempMonth, tempAmount);
                    temphousemate.makePayment(tempPayment);
                }
                listIn.addHousemate(temphousemate);
                
            }
            housemateReader.close();
        }
        
        catch(IOException ioe) {
            System.out.println("No records found");
        }
        
    }
}
