import java.util.Arrays;


public class HashTable<K extends Comparable<? super K>, V> implements HashTableInterface<K, V> {
	private Entry<K, V>[] hashTable;
	private int numOfEntries;
	private static int DEFAULT_CAPACITY = 9;
	public static int collision = 0;
	private int hashOption;
	private int collisionOption;
	
	public HashTable() {
		this(DEFAULT_CAPACITY);
	}
	
	
	public HashTable(int initialCapacity) {
		Entry<K, V>[] tempHashTable = (Entry<K, V>[])new Entry[initialCapacity];
		hashTable = tempHashTable;
		numOfEntries = 0;
	}
	

	public void put(K key, V value) {
		int index = 0;
		
		if(hashOption == 1)//SSF
			index = (int)SSF((String)key);
		else//PAF
			index = (int)PAF((String)key);
		
		int temp;
		String fileName = value.toString();

		
		if(hashTable[index] == null) {//if the word is added for the first time				
			Entry e = new Entry(index, fileName);
			hashTable[index] = e;
			e.addEntry(fileName, 1);//add value in sll
			numOfEntries++;
			e.setWord(key.toString());			
		}		
		else if(hashTable[index] != null){//The place to add the key is not empty		
			temp = search(key);			
			if(temp == -1) {//key is not found but index is not empty			
				
				if(hashOption == 1)//LP
					index = LP(index);
				else//DH
					index = DH(index);
				
				Entry e = new Entry(index, fileName);
				hashTable[index] = e;
				e.addEntry(fileName, 1);//add value in sll
				numOfEntries++;
				e.setWord(key.toString());
				collision++;
			}			
			else {//this key is already added		
				Entry e = hashTable[temp];
				boolean flag = e.Name(fileName);//Is there element in sll with the same file name?					
				if(flag == true) {//increase frequency with the same filename
					e.EntryUpdate(fileName);
				}
				else {//if different filename new element is added to value in sll		
					e.addEntry(fileName, 1);
				}				
			}	
		}
		resize();
	}

		
	public double loadFactor() {
		double load = ((double)numOfEntries /(double) DEFAULT_CAPACITY);
		return load;
	}
	
	
	
	public void resize() {
		
	    double load = loadFactor();
	    Entry[] temp = new Entry[DEFAULT_CAPACITY];
	    int index = 0;
	    Entry entry = null;
	    
		if(load >= 0.5) {//0.8
			DEFAULT_CAPACITY = DEFAULT_CAPACITY * 2;	
			hashTable = Arrays.copyOf(hashTable, (2 * hashTable.length) + 1);
			
			for(int i = 0; i < DEFAULT_CAPACITY; i++) {
				if(hashTable[i] != null) {
					temp[i] = hashTable[i];
				}
			}
			removeAll();//to empty the table
			
			for(int i = 0; i < temp.length; i++) {//new size
				if(temp[i] != null) {
					entry = temp[i];
					index = SSF(entry.getWord());
					
					if(hashTable[index] == null) {
						hashTable[index] = entry;
					}
					else {//if there is a collision
						index = LP(index);
						hashTable[index] = entry;
					}
				}
			}
			
		}
	}

	
	public void removeAll() {//delete all key in hash table
		for(int i = 0; i < DEFAULT_CAPACITY; i++) {
			if(hashTable[i] != null) {
				hashTable[i] = null;
			}
		}
	}
	
	
	public void remove(K key) {		
		int index = search(key);
		
		if(index==-1)
			System.out.println("The word to be removed is not added.");
		else {
			Entry e = hashTable[index];
			if(e.getWord().equals(key)) {
				hashTable[index] = null;
				numOfEntries--;
			}
		}				
	}
	
		
	public int search(K key) {		
		int index = 0;
		
		if(hashOption == 1)//SSF
			index = (int)SSF((String)key);
		else//PAF
			index = (int)PAF((String)key);
		
		String k = key.toString();
		if(hashTable[index] != null && hashTable[index].getWord().equals(k)) {
			return index;
		}
		else {//collision varsaaaaa
			for(int i = 0; i < DEFAULT_CAPACITY; i++) {
				if(hashTable[i] != null && hashTable[i].getWord().equals(k)) {
					index = i;
					break;
				}
				else index = -1;//this key is not added
			}			
			return index;
		}
	}
	
	
	public void ValueGet(K key) {//according to the key received from the user
		int temp = search(key);//word being searched	
		if(temp == -1)
			System.out.println("Not found!");
		else
			hashTable[temp].display();		
	}

		
	//HASH FUNCTION
	public int SSF(String word) {
		
		char[] charray = word.toCharArray();
		int temp = 0;
		int ssf = 0;
		for(int i = 0; i < charray.length; i++) {
			temp = (int)charray[i] - 96;
			ssf += temp;
			temp = 0;
		}
		return ssf % DEFAULT_CAPACITY;
	}
	
		
	public int PAF(String word) {
		
		char[] charray = word.toCharArray();
		int temp = 0;
		int paf = 0;
		int z = 31;
		
		for(int i = 0; i < charray.length; i++) {
			temp = (int)charray[i] - 96;
			paf += temp * Math.pow(z, (charray.length-(i+1)));
			temp = 0;
		}		
		return paf % DEFAULT_CAPACITY;
	}

	
	//COLLISION HANDLING
	public int LP(int index) {//linear probing
		//int LP = 0;
		
		for(int i = index; i < DEFAULT_CAPACITY; i++) {
			if(hashTable[i] == null) {
				index = i;
				break;
			}			
		}
		for(int j = 0; j < index; j++) {//in order to cycle
			if(hashTable[j] == null) {
				index = j;
				break;
			}
		}
		return index;			
	}
	
	
	public int DH(int index) {//double hashing
		
		int DH = 0;
		int k = index;
		int N = DEFAULT_CAPACITY;
		int q = 7;//prime number
		int h = k % N;//h(k)
		int d = q - (k % q);//d(k)
				
		for(int i = 0; i < (N - 1); i++) {
			DH = h + (i * d);
			DH = DH % N;
			
			if(hashTable[DH] == null) {
				index = DH;
				break;
			}
		}
		return index;
	}

	
	

	public int getHashOption() {return hashOption;}
	public void setHashOption(int hashOption) {this.hashOption = hashOption;}
	public int getCollisionOption() {return collisionOption;}
	public void setCollisionOption(int collisionOption) {this.collisionOption = collisionOption;}
	public static int getCollision() {return collision;}
	public static void setCollision(int collision) {HashTable.collision = collision;}

	

}
