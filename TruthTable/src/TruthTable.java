
public class TruthTable {
	public static void main(String args[]) {
		//Wrong
		boolean p, q;
		System.out.println("P\tQ\tAND\tOR\tXOR\tNOT");
		p = true; q = true;
		System.out.print(p + "\t" + q +"\t");
		System.out.print((p&q) + "\t" + (p|q) + "\t");
		System.out.println((p^q) + "\t" + (!p));
		p = true; q = false;
		System.out.print(p + "\t" + q +"\t");
		System.out.print((p&q) + "\t" + (p|q) + "\t");
		System.out.println((p^q) + "\t" + (!p));
		p = false; q = true;
		System.out.print(p + "\t" + q +"\t");
		System.out.print((p&q) + "\t" + (p|q) + "\t");
		System.out.println((p^q) + "\t" + (!p));
		p = false; q = false;
		System.out.print(p + "\t" + q +"\t");
		System.out.print((p&q) + "\t" + (p|q) + "\t");
		System.out.println((p^q) + "\t" + (!p) + "\r");
		
		//Right
	    System.out.println("P\tQ\tAND\tOR\tXOR\tNOT");
	    System.out.println(row(true, true));
	    System.out.println(row(true, false));
	    System.out.println(row(false, true));
	    System.out.println(row(false, false));
	}

	private static String row(final boolean p, final boolean q) {
	    return tabDelimited(p, q) + tabDelimited(p & q, p | q) + tabDelimited(p ^ q, !p);
	}

	private static String tabDelimited(final boolean a, final boolean b) {
	    return (a ? 1 : 0) + "\t" + (b ? 1 : 0) + "\t";
	}
}
