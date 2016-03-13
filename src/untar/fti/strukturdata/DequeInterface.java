package untar.fti.strukturdata;

/** 
 * An interface for the ADT Deque.
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public interface DequeInterface<T> {
	public void	addToFront(T newEntry);
	public void	addToBack(T newEntry);
	public T	removeFront();
	public T	removeBack();
	public T	getFront();
	public T	getBack();
	public boolean isEmpty();
	public void clear();
}
