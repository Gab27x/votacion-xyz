import java.util.Scanner;

public class Cli {
	private Scanner scanner;
	private QueryDeviceController queryDeviceController;

	public Cli(QueryDeviceController queryDeviceController) {
		this.scanner = new Scanner(System.in);
		this.queryDeviceController = queryDeviceController;

	}
	
	public void startQueryProcess(){
		System.out.println("Welcome to query CLI");

		while (true) {
			System.out.println("Enter your id");
			String id = scanner.nextLine().trim();

			if (id.isEmpty())
				continue;
			if (id.equalsIgnoreCase("exit"))
				break;

			queryDeviceController.query(id);
		}
		scanner.close();
	}
	
}
