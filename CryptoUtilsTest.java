import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
	public class CryptoUtilsTest {
	    private static final String JDBC_CONNECTION_URL = "jdbc:oracle:thin:malvika/malvika@localhost:1521:xe";
		public static void main(String[] args) throws IOException {
	        String key = "difficultpasswo1";
	        /////////////////////////////////////
	        InputStream inputStream = null;
	    	FileOutputStream outputStream = null;
	    	File targetFile = null;
	    		// read this file into InputStream
	    		inputStream = new FileInputStream("C:\\Users\\Administrator\\Documents\\employee.csv");
	    		 targetFile = new File("E:\\docs\\target_emp.csv");

	    		// write the inputStream to a FileOutputStream
	    		 outputStream = new FileOutputStream(targetFile);

	    		int read = 0;
	    		byte[] bytes = new byte[1024];

	    		while ((read = inputStream.read(bytes)) != -1) {
	    			outputStream.write(bytes, 0, read);
	    		}

	    		System.out.println("Done!");
	
	        /////////////////////////////////////
/*File file = new File("C:\\Users\\Administrator\\Downloads\\encrypted.dat");  
	      //Create the file
	      if (file.createNewFile()){
	      System.out.println("File is created!");
	      }else{
	      System.out.println("File already exists.");
	      }*/
	      //////////////
	      File encrypted = new File("E://docs//encrypt.csv");
	      
	    //Create the file
	    if (encrypted.createNewFile()){
	    System.out.println("Encrypted File is created!");
	    }else{
	    System.out.println("Encrypted File already exists.");
	    }
	     /////////
	    File decrypted = new File("E://docs//decrypt.csv");
	    
	  //Create the file
	  if (decrypted.createNewFile()){
	  System.out.println("Decrypted File is created!");
	  }else{
	  System.out.println(" Decrypted File already exists.");
	  }
	        try {
	            CryptoUtils.encrypt(key, targetFile, encrypted);
	            CryptoUtils.decrypt(key, encrypted, decrypted);
	        } catch (CryptoException ex) {
	            System.out.println(ex.getMessage());
	            ex.printStackTrace();
	        }
	   //}
//////////// Loading to DB starts ///////////////////////////////////////
	    try {

			CSVLoader loader = new CSVLoader(getCon());
			
			loader.loadCSV("E:\\docs\\decrypt.csv", "EXTRACTS", true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static Connection getCon() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	}
