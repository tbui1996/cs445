package cs445.a5;
import java.util.Iterator;


public class Test {

	public static void main(String[] args) 
	{
//		TernaryTree<String> tree = new TernaryTree<String>("A");
//		TernaryTree<String> b = new TernaryTree<String>("B");
//		TernaryTree<String> c = new TernaryTree<String>("C");
//		TernaryTree<String> d = new TernaryTree<String>("D");
//		TernaryTree<String> e = new TernaryTree<String>("E");
//		TernaryTree<String> f = new TernaryTree<String>("F");
//		TernaryTree<String> g = new TernaryTree<String>("G");
//		TernaryTree<String> h = new TernaryTree<String>("H");
//		System.out.println(tree.getRootData());
//		b.setTree(b.getRootData(),d,e,f);
//		g.setTree(g.getRootData(),null,null,h);
//		c.setTree(c.getRootData(),null,g,null);
//		tree.setTree(tree.getRootData(),b,null,c);
//		System.out.println(tree.getRootData());
//		Iterator<String> it = tree.getLevelOrderIterator();
//		System.out.println("Tree1: ");
//		while(it.hasNext())
//		{
//			System.out.print(it.next()+", ");
//		}
//
//		System.out.println();
//
//		System.out.println("Depth: "+tree.getHeight());
//		System.out.println("Num Nodes: "+tree.getNumberOfNodes());
//
//		tree.setTree(tree.getRootData());
//		it = tree.getLevelOrderIterator();
//		System.out.println("Tree1: ");
//		while(it.hasNext())
//		{
//			System.out.print(it.next()+", ");
//		}
//
//		System.out.println();
//
//		System.out.println("Depth: "+tree.getHeight());
//		System.out.println("Num Nodes: "+tree.getNumberOfNodes());
		
		TernaryTree<String> tree = new TernaryTree<String>("Root");
		TernaryTree<String> leftChild = new TernaryTree<String>("LeftChild");
		TernaryTree<String> middleChild = new TernaryTree<String>("MiddleChild");
		TernaryTree<String> rightChild = new TernaryTree<String>("RightChild");
		tree.setTree(tree.getRootData(), leftChild, middleChild, rightChild);
		System.out.println(tree.getRootData());
		System.out.println(tree.getLeftChild().getData());
		System.out.println(tree.getMiddleChild().getData());
		System.out.println(tree.getRightChild().getData());
		System.out.println();
		
		TernaryTree<String> leftLeft = new TernaryTree<String>("LeftLeft");
		TernaryTree<String> leftMiddle = new TernaryTree<String>("LeftMiddle");
		TernaryTree<String> leftRight = new TernaryTree<String> ("LeftRight");
		leftChild.setTree(leftChild.getRootData(), leftLeft, leftMiddle, leftRight);
		System.out.println(leftChild.getRootData());
		System.out.println(leftChild.getLeftChild().getData());
		System.out.println(leftChild.getMiddleChild().getData());
		System.out.println(leftChild.getRightChild().getData());
		System.out.println();
		
		TernaryTree<String> leftLeftLeft = new TernaryTree<String>("LeftLeftLeft");
		TernaryTree<String> leftLeftMiddle = new TernaryTree<String>("LeftLeftMiddle");
		TernaryTree<String> leftLeftRight = new TernaryTree<String>("LeftLeftRight");
		leftLeft.setTree(leftLeft.getRootData(), leftLeftLeft, leftLeftMiddle, null);
		System.out.println(leftLeft.getRootData());
		System.out.println(leftLeft.getLeftChild().getData());
		System.out.println(leftLeft.getMiddleChild().getData());
		System.out.println();
		
		Iterator<String> iterator = tree.getLevelOrderIterator();
		Iterator<String> iterator2 = leftChild.getLevelOrderIterator();
		System.out.print("Tree 1 (level order): ");
		while(iterator.hasNext())
		{
			System.out.print(iterator.next() + ", ");
		}
		System.out.println();
		System.out.println("Height: " + tree.getHeight());
		System.out.println("Number of Nodes: " + tree.getNumberOfNodes());
		System.out.println();
		System.out.print("Tree 2 (level order): ");
		while(iterator2.hasNext())
		{
			System.out.print(iterator2.next() + ", ");
		}
		System.out.println();
		System.out.println("Height: " + leftChild.getHeight());
		System.out.println("Number of Nodes: " + leftChild.getNumberOfNodes());
		System.out.println();
		
		Iterator<String> iterator3 = tree.getPreorderIterator();
		Iterator<String> iterator4 = leftChild.getPreorderIterator();
		System.out.print("Tree 1 (preorder): ");
		while(iterator3.hasNext())
		{
			if(tree.hasLeftChild())
			{
				if(tree.getLeftChild().isLeaf())
				{
					while(iterator4.hasNext())
					{
						System.out.print(iterator4.next() + ", ");
					}
				}
			}
			System.out.print(iterator3.next() + ", ");
			
		}
	}

}
