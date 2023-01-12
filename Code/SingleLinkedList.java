
public class SingleLinkedList {

	Node head;

	public void add(String name, int freq) {
		Node newNode = new Node(name, freq);
		
		if(head == null)
			head = newNode;
		else {
			Node temp = head;
			while(temp.getLink() != null) {
				temp = temp.getLink();
			}
			temp.setLink(newNode);
		}
	}

	//StopWords add
	public void add(String word) {
		Node newNode = new Node(word);
		if(head == null)
			head = newNode;
		else {
			Node temp = head;
			while(temp.getLink() != null)
				temp = temp.getLink();
			temp.setLink(newNode);
		}
	}

	//StopWrods search
	public boolean search(String word) {
		Node temp = head;
		boolean flag = true;
		while(temp != null) {
			if(temp.getFileName().equals(word)) {
				flag = false;
			}
				temp = temp.getLink();
		}
		return flag;
	}
	
	
	public int size() {		
		int counter = 0;
		Node temp = head;
		
		while(temp != null) {
			temp = temp.getLink();
			counter++;
		}
		return counter;
	}
	
	
	public void frequencyUpdate(String file) {
		Node temp = head;
		int count ;
		
		while(temp != null) {
			if(temp.getFileName().equals(file)) {
				count = temp.getFrequency();
				temp.setFrequency((count + 1));
			}
			temp = temp.getLink();
		}
	}
	
	
	
	public boolean nameCheck(String fileName) {
		Node temp = head;
		Boolean flag = false;
		
		while(temp != null) {
			if(temp.getFileName().equals(fileName)) {//zaten o dosya adý varmýþ kontrolü
				flag = true;
			}
			temp = temp.getLink();
		}
		return flag;
	}
	
	
	
	public void display() {
		Node temp = head;
		
		while(temp != null) {
			System.out.println(temp.getFrequency() + " - " + temp.getFileName());					
			temp = temp.getLink();
		}
		
	}
	
	
	
	
	
	
}
