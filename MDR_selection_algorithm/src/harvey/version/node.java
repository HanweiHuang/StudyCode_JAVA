package harvey.version;

import java.util.ArrayList;

public class node {
	private String range;
	
	private String node_x;
	
	private String node_y;

	private String id;
	
	node parent;
	public node getParent() {
		return parent;
	}

	public void setParent(node parent) {
		this.parent = parent;
	}

	//String neighbour;
	String state;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	ArrayList<node> dependent_neighbors = new ArrayList<node>();
	
	public ArrayList<node> getDependent_neighbors() {
		return dependent_neighbors;
	}

	public void setDependent_neighbors(ArrayList<node> dependent_neighbors) {
		this.dependent_neighbors = dependent_neighbors;
	}

	ArrayList<node> neighbours = new ArrayList<node>();
	
	public ArrayList<node> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayList<node> neighbours) {
		this.neighbours = neighbours;
	}

	int []a = new int[31];
	
	public int[] getA() {
		return a;
	}

	public void setA(int[] a) {
		this.a = a;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getNode_x() {
		return node_x;
	}

	public void setNode_x(String node_x) {
		this.node_x = node_x;
	}

	public String getNode_y() {
		return node_y;
	}

	public void setNode_y(String node_y) {
		this.node_y = node_y;
	}
}
