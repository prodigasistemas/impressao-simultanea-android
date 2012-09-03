package background;

import business.ControladorImoveis;
import ui.FileManager;
import android.content.Context;
import android.os.Handler;
    

// Class that performs progress calculations on a thread.  Implement
// the thread by subclassing Thread and overriding its run() method.  Also provide
// a setState(state) method to stop the thread gracefully.
        
public class CarregarRotaThread extends Thread {	
    
    // Class constants defining state of the thread
    public final static int DONE = 0;
    public final static int RUNNING = 1;
    private static Context context;

    
    Handler mHandler;
    int mState;
    int total;
    private String fileName;

    // Constructor with an argument that specifies Handler on main thread
    // to which messages will be sent by this thread.
    public CarregarRotaThread(Handler h, String fileName, Context context) {
    	this.mHandler = h;
        this.context = context;
        this.total = 0;
        this.fileName = fileName;
    }
    
    // Override the run() method that will be invoked automatically when 
    // the Thread starts.  Do the work required to update the progress bar on this
    // thread but send a message to the Handler on the main UI thread to actually
    // change the visual representation of the progress. 
    
    @Override
    public void run() {
    	
    	mState = RUNNING;
    	FileManager.getInstancia();
		
    	if ( fileName.endsWith(".txt") ){
			ControladorImoveis.getInstancia().carregarDadosParaRecordStore(FileManager.readFile(fileName), mHandler, context);

		} else {
			ControladorImoveis.getInstancia().carregarDadosParaRecordStore(FileManager.readCompressedFile(fileName), mHandler, context);
			
		}
    	mState = DONE;
    }
    
    // Set current state of thread
    public void setState(int state) {
        mState = state;
    }
    
    // get current state of thread
    public int getCustomizedState() {
        return mState;
    }
}