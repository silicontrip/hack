import java.util.ArrayList;
public class DiamondMatrix<T> {
	private ArrayList<T> al;

	DiamondMatrix() { al = new ArrayList<T>(); }

	private int toIndex (int x, int y)
	{
		int offset;
		int ring = Math.abs(x) + Math.abs(y);
		if (ring==0) return 0;
		
		int base = 2 * ring * (ring - 1) + 1;
		if (x>=0) 
			offset = ring - y;
		else
			offset = (3 * ring) + y;

		return -(base + offset);
		
	}

	public Boolean isEmpty(int x, int y) { return get(x,y)==null; }

	public T get(int x, int y) {
		int index = toIndex(x,y);
		if (index >= al.size())
			return null;
		return al.get(index);
	}

	public void set(int x, int y, T t) {
		int index = toIndex(x,y);
		while (index >= al.size())
			al.add(null);
			//al.ensureCapacity(index+1);
		al.set(index,t);
	}

	public int size() { return al.size(); }

	public String toString() {
		StringBuilder sb=new StringBuilder("[");  
		Boolean first = true;
		for (T c : al) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			sb.append(c);
		}
		sb.append("]");
		return sb.toString();
	}

}
