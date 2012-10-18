package background;

import ui.ArquivoRetorno;
import ui.FileManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
    

// Class that performs progress calculations on a thread.  Implement
// the thread by subclassing Thread and overriding its run() method.  Also provide
// a setState(state) method to stop the thread gracefully.
        
public class GerarArquivoCompletoThread extends Thread {	
    
    // Class constants defining state of the thread
    public final static int DONE = 2;
    public final static int RUNNING = 1;
    private static Context context;

    
    Handler mHandler;
    int mState;
    int total;
    int increment;

    // Constructor with an argument that specifies Handler on main thread
    // to which messages will be sent by this thread.
    public GerarArquivoCompletoThread(Handler h, Context context, int increment) {
    	this.mHandler = h;
        GerarArquivoCompletoThread.context = context;
        this.total = 0;
        this.increment = increment;
    }
    
    // Override the run() method that will be invoked automatically when 
    // the Thread starts.  Do the work required to update the progress bar on this
    // thread but send a message to the Handler on the main UI thread to actually
    // change the visual representation of the progress. In this example we count
    // the index total down to zero, so the horizontal progress bar will start full and
    // count down.
    
    @Override
    public void run() {
    	
    	mState = RUNNING;
    	FileManager.getInstancia();
    		
    	ArquivoRetorno.getInstancia().gerarArquivoCompleto(mHandler, context, increment);

    	mState = DONE;
    	
    	Bundle b = new Bundle();
        // Send message (with current value of total as data) to Handler on UI thread
        Message msg = mHandler.obtainMessage();
        b.putInt("arquivoCompleto" + String.valueOf(increment), 100);
        msg.setData(b);
        mHandler.sendMessage(msg);
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