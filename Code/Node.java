
public class Node {

	private Node link;
	private String fileName;
	private int frequency;
	
	
	public Node(String name, int freq) {
		this.fileName = name;
		this.frequency = freq;
		link = null;
	}

	public Node(String name) {//for stopwords sll 
		fileName = name;
	}
	
	
	public Node getLink() {return link;}
	public void setLink(Node link) {this.link = link;}

	public String getFileName() {return fileName; }
	public void setFileName(String fileName) {this.fileName = fileName;}

	public int getFrequency() {return frequency;}
	public void setFrequency(int frequency) {this.frequency = frequency;}
	
	

}
