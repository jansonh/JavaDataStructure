package untar.fti.strukturdata;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V>, Serializable {
	
	private TableEntry<K, V>[] hashTable; // dictionary entries
	private int numberOfEntries;
	private int locationsUsed; // number of table locations not null
	
	private static final int DEFAULT_SIZE = 101; // must be prime
	private static final double MAX_LOAD_FACTOR = 0.5; // fraction of hash table that can be filled
	
	private class TableEntry<S, T> implements Serializable {
		private S key;
		private T value;
		private boolean inTable;
		
		private TableEntry(S searchKey, T dataValue) {
			key = searchKey;
			value = dataValue;
			inTable = true;
		}
		
		private S getKey() {
			return key;
		}
		
		private T getValue() {
			return value;
		}
		
		private void setValue(T newValue) {
			value = newValue;
		}
		
		private void setToIn() {
			inTable = true;
		}
		
		private void setToRemoved() {
			inTable = false;
		}
		
		private boolean isIn() {
			return inTable;
		}
		
		private boolean isRemoved() {
			return !inTable;
		}
	}
	
	public HashedDictionary() {
		this(DEFAULT_SIZE);
	}
	
	public HashedDictionary(int tableSize) {
		int primeSize = getNextPrime(tableSize);
		
		hashTable = new TableEntry[primeSize];
		numberOfEntries = 0;
		locationsUsed = 0;
	}

	@Override
	public V add(K key, V value) {
		V oldValue; // value to return
		
		if (isHashTableTooFull())
			rehash();
		
		int index = getHashIndex(key);
		index = probe(index, key); // check for and resolve collision
		
		// Assertion: index is within legal range for hashTable
		assert (index >= 0) && (index < hashTable.length);
		
		if ((hashTable[index] == null) || hashTable[index].isRemoved()) {
			// key not found, so insert new entry
			hashTable[index] = new TableEntry<K, V>(key, value);
			numberOfEntries++;
			locationsUsed++;
			oldValue = null;
		}
		else {
			// key found; get old value for return and then replace it
			oldValue = hashTable[index].getValue();
			hashTable[index].setValue(value);
		}
		
		return oldValue;
	}

	@Override
	public V remove(K key) {
		V removedValue = null;
		
		int index = getHashIndex(key);
		index = locate(index, key);
		
		if (index != -1) {
			// key found; flag entry as removed an return its value
			removedValue = hashTable[index].getValue();
			hashTable[index].setToRemoved();
			numberOfEntries--;
		}
		
		return removedValue;
	}

	@Override
	public V getValue(K key) {
		V result = null;
		
		int index = getHashIndex(key);
		index = locate(index, key);
		
		if (index != -1)
			result = hashTable[index].getValue(); // key found; get value
		
		// else key not found; return null
		return result;
	}

	@Override
	public boolean contains(K key) {
		int index = getHashIndex(key);
		index = probe(index, key);
		
		return !((hashTable[index] == null) || hashTable[index].isRemoved());
	}

	@Override
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	@Override
	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public boolean isFull() {
		return locationsUsed == hashTable.length;
	}

	@Override
	public int getSize() {
		return numberOfEntries;
	}

	@Override
	public void clear() {
		numberOfEntries = 0;
		locationsUsed = 0;
	}
	
	private int getHashIndex(K key) {
		int hashIndex = key.hashCode() % hashTable.length;
		if (hashIndex < 0) 
			hashIndex = hashIndex + hashTable.length;
		return hashIndex;
	}
	
	private int getNextPrime(int anInteger) {
		while (!isPrime(anInteger))
			anInteger++;
		return anInteger;
	}
	
	private boolean isPrime(int anInteger) {
		for (int i = 2; i * i < anInteger; i++)
			if (anInteger % i == 0)
				return false;
		return true;
	}
	
	private boolean isHashTableTooFull() {
		double loadFactor = 1.0 * locationsUsed / hashTable.length;
		return loadFactor >= MAX_LOAD_FACTOR;
	}
	
	private int locate(int index, K key) {
		boolean found = false;
		
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn() && key.equals(hashTable[index].getKey()))
				found = true; // key found
			else // follow probe sequence
				index = (index + 1) % hashTable.length; // linear probing
		}
		
		int result = -1;
		if (found)
			result = index;
		
		return result;
	}
	
	private int probe(int index, K key) {
		boolean found = false;
		int removedStateIndex = -1; // index of first location in removed state
		
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey()))
					found = true; // key found
				else // follow probe sequence
					index = (index + 1) % hashTable.length; // linear probing
			}
			else { // skip entries that were removed
				// save index of first location in removed state
				if (removedStateIndex == -1) 
					removedStateIndex = index;
				
				index = (index + 1) % hashTable.length; // linear probing
			}
 		}
		
		if (found || (removedStateIndex == -1))
			return index; // index of either key or null
		else
			return removedStateIndex; // index of an available location
	}
	
	private void rehash() {
		TableEntry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(oldSize + oldSize);
		hashTable = new TableEntry[newSize]; // increase size array
		
		numberOfEntries = 0; // reset number of dictionary entries, since it will be incremented by add during rehash
		locationsUsed = 0;
		
		// rehash dictionary entries from old array to the new and bigger array;
		// skip both null locations and removed entries
		for (int index = 0; index < oldSize; index++) {
			if ((oldTable[index] != null) && oldTable[index].isIn())
				add(oldTable[index].getKey(), oldTable[index].getValue());
		}
	}
	
	private class KeyIterator implements Iterator<K> {
		private int currentIndex; // current position in hashTable
		private int numberLeft;   // number of entries left in iteration
		
		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		}
		
		public boolean hasNext() {
			return numberLeft > 0;
		}
		
		public K next() {
			K result = null;
			
			if (hasNext()) {
				// find index of next entry
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				}
				
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			}
			else
				throw new NoSuchElementException();
			
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex; // current position in hashTable
		private int numberLeft;   // number of entries left in iteration
		
		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		}
		
		public boolean hasNext() {
			return numberLeft > 0;
		}
		
		public V next() {
			V result = null;
			
			if (hasNext()) {
				// find index of next entry
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				}
				
				result = hashTable[currentIndex].getValue();
				numberLeft--;
				currentIndex++;
			}
			else
				throw new NoSuchElementException();
			
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
