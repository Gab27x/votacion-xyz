import Query.QueryProxyIPrx;


public class QueryDeviceController implements Runnable {

	private Cli cli;
	// objeto prx
	private QueryProxyIPrx queryProxyIPrx;

	public QueryDeviceController(QueryProxyIPrx queryProxyIPrx) {
		this.cli = new Cli(this);
		this.queryProxyIPrx = queryProxyIPrx;
	}
	
	public String query(String id) {
		String result = queryProxyIPrx.getVotingTableById(id);
		System.out.println(result);
		return result;
	}

	@Override
	public void run() {
		cli.startQueryProcess();
	}


	
}
