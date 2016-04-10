package untar.fti.strukturdata;

public interface TreeInterface<T> {
	public T getRootData();
	public int getHeight();
	public int getNumberOfNodes();
	public boolean isEmpty();
	public void clear();
}
