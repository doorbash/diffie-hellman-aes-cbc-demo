public class Main {
    
    public static void main(String argv[]) {
    	new Alice().start();
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				new Bob().start();
			}
		}).start();
    }


}