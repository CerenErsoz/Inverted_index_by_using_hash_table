
public interface HashTableInterface<K, V> {

	public void put(K key, V value);
	
	public void remove(K key);
	
	public void ValueGet(K key);
	
	public void resize();
	

}
