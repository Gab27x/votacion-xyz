

public class QueryDeviceController implements Runnable {

	private Cli cli;
	// objeto prx

	public QueryDeviceController() {
		this.cli = new Cli(this);
		//objeto prx
	}
	
	public String query(String id) {
		return "hola";
		
	}

	@Override
	public void run() {
		cli.startQueryProcess();
	}


	
}
