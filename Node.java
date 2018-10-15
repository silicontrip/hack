public class Node {
	NodeType type;

	public Node (NodeType n) { type = n; }

	public NodeType getType() { return type; }
	public Boolean match (Node n) {
		return (getType() == n.getType()) || (isHash() && n.isNotNil()) || (n.isHash() && isNotNil());
	}
	public Boolean isZero() { return type == NodeType.zero; }
	public Boolean isOne() { return type == NodeType.one; }
	public Boolean isHash() { return type == NodeType.hash; }
	public Boolean isNil() { return type == NodeType.nil; }
	public Boolean isNotNil() { return type != NodeType.nil; }
	public String toString() 
	{
		if (isZero()) return new String ("0");
		if (isOne()) return new String ("1");
		if (isHash()) return new String ("#");
		return new String ("_");  // or space 
	}
}

	

