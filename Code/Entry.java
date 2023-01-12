
public class Entry<K, V> {
	
	private K key;
	private SingleLinkedList value = new SingleLinkedList();
	private String word;
	
	public Entry(K key, V value) {
		this.key = key;
	}
		
	public void addEntry(String file, int frequency) {
		value.add(file, frequency);
	}
			
	public void EntryUpdate(String file) {
		value.frequencyUpdate(file);
	}
		
	public int valueSize() {
		return value.size();
	}
	
	public boolean Name(String name) {//Checking if the entry in hashtable's put function has the same filename value
		return value.nameCheck(name);
	}
	
	public void display() {
		if(value.size() == 1)
			System.out.println(value.size() + " document found");
		else
			System.out.println(value.size() + " documents found");		
		value.display();		
	}
	
	
	public K getKey() {return key;}
	public void setKey(K key) {this.key = key;}
	public String getWord() {return word;}
	public void setWord(String word) {this.word = word;}
	public SingleLinkedList getValue() {return value;}
	public void setValue(SingleLinkedList value) {this.value = value;}
	
	
	
}
