package logger;

// Packages for Current User
import client.Student;
import client.student.FileUpload;

// Pakages For Screenshot
import java.awt.AWTException; 
import java.awt.Rectangle; 
import java.awt.Toolkit; 
import java.awt.Robot; 
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

// Packages for File Handling
import java.io.IOException; 
import java.io.File; 

// Current Time
import java.time.LocalDateTime;  // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;  // Import the DateTimeFormatter class


// Runnable Thread
public class Screenshot implements Runnable{
	private final Student user;

	public Screenshot(Student user){
		this.user = user;
	}

	// Get Cuurent Time for file name
	public  String getCurrentDateTime(){
		// Creating Date Object
		LocalDateTime myDateObj = LocalDateTime.now();  

		// Formatting Date String
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH.mm.ss");  
		String formattedDate = myDateObj.format(myFormatObj);  
		return formattedDate;
	}

	public  void screenshot_task(String current_date_time){
		try {  
			//  Dont remove this, it shows
			// error: exception InterruptedException is never thrown 
			// in body of corresponding try statement
			Thread.sleep(120);

			Robot r = new Robot(); 

			// It saves screenshot to desired path 
			String path = System.getProperty("user.dir") +  "/local/"+ user.rollNum;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = user.rollNum + "_"+current_date_time + ".jpg";

			String full_path = path +"/" + fileName;

			// Used to get ScreenSize and capture image 
			// Used to get ScreenSize and capture image 
			Rectangle capture = 
			new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 

			BufferedImage Image = r.createScreenCapture(capture); 
			ImageIO.write(Image, "jpg", new File(full_path)); 
			System.out.println("Screenshot saved"); 

			new FileUpload(this.user).uploadFile("",full_path);
			// new Thread(runns).start();

		} 
		catch (AWTException | IOException | InterruptedException ex) { 
			System.out.println(ex); 
		}
	}

	@Override
	public void run(){
		while(true) {		
			try {
				// Taking Screenshot every 8 seconds
				Thread.sleep(8000);
			} 
			catch (InterruptedException e) {
				System.err.format("IOException: %s%n", e);
			}

			// Getting Current Time
			String current_date_time = getCurrentDateTime();

			// Taking screenshots continously
			screenshot_task(current_date_time);
		} 
	}
} 

