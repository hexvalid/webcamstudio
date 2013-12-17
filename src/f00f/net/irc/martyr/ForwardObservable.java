package f00f.net.irc.martyr;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Does notifications in the order they are added.
 * */
public class ForwardObservable extends Observable
{
	private boolean changed = true;
	private final List<Observer> obs = new LinkedList<>();
	private final Object localMonitor = new Object();


	public void setChanged()
	{
		synchronized(localMonitor)
		{
			changed = true;
		}
	}

	protected void clearChanged()
	{
		synchronized(localMonitor)
		{
			changed = false;
		}
	}
	
	public void addObserver( Observer o )
	{
		synchronized(localMonitor)
		{
			obs.add( o );
		}
	}

	public void deleteObserver( Observer o )
	{
		synchronized(localMonitor)
		{
			obs.remove( o );
		}
	}

	public void notifyObservers(Object arg) 
	{
		synchronized(localMonitor)
		{
			if (!changed)
				return;
			clearChanged();

            for (Observer ob : obs) {
                ob.update(this, arg);
            }
        }
	}


}

