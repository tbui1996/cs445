package cs445.a5;
import java.util.Iterator;
import java.util.NoSuchElementException;
import StackAndQueuePackage.*;

public class TernaryTree<T> implements TernaryTreeInterface<T>
{
	private TernaryNode<T> root;
	
	//constructor
	public TernaryTree()
	{
		root = null;
	}
	//constructor
	public TernaryTree(T rootData)
	{
		root = new TernaryNode<>(rootData);
	}
	//constructor
	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree)
	{
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	}

	class TernaryNode<T>
	{
		private T data;
		private TernaryNode<T> leftChild;
		private TernaryNode<T> middleChild;
		private TernaryNode<T> rightChild;
		
		public TernaryNode()
		{
			this(null); //calls next constructor
		}
		
		public TernaryNode(T dataPortion)
		{
			this(dataPortion, null, null, null); //call next constructor
		}
		
		public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newMiddleChild, TernaryNode<T> newRightChild)
		{
			data = dataPortion;
			leftChild = newLeftChild;
			middleChild = newMiddleChild;
			rightChild = newMiddleChild;
		}
		
		/**
		 * retrieves the data portion of the node.
		 * @return the object in the data portion of the node.
		 */
		public T getData()
		{
			return data;
		}
		
		/**
		 * Sets the data portion of the node.
		 * @param newData the data object.
		 */
		public void setData(T newData)
		{
			data = newData;
		}
		
		/**
		 * Retrieves the left child of the node.
		 * @return the left child of the node.
		 */
		public TernaryNode<T> getLeftChild()
		{
			return leftChild;
		}
		
		/**
		 * Sets the node's left child to a given node.
		 * @param newLeftChild A node that will be the left child.
		 */
		public void setLeftChild(TernaryNode<T> newLeftChild)
		{
			leftChild = newLeftChild;
		}
		
		/**
		 * Retrieves the middle child of the node.
		 * @return the middle child of the node.
		 */
		public TernaryNode<T> getMiddleChild()
		{
			return middleChild;
		}
		
		/**
		 * Sets the node's middle child to a given node.
		 * @param newMiddleChild a node that will be the middle child.
		 */
		public void setMiddleChild(TernaryNode<T> newMiddleChild)
		{
			middleChild = newMiddleChild;
		}
		
		/**
		 * Retrieves the right child of the node.
		 * @return the right child of the node.
		 */
		public TernaryNode<T> getRightChild()
		{
			return rightChild;
		}
		
		/**
		 * Sets the node's right child to a given node.
		 * @param newRightChild a node that will be the right child.
		 */
		public void setRightChild(TernaryNode<T> newRightChild)
		{
			rightChild = newRightChild;
		}
		
		/**
		 * Detects whether the node has a left child.
		 * @return true if the node has a left child.
		 */
		public boolean hasLeftChild()
		{
			return leftChild != null;
		}
		
		/**
		 * detects whether the node has a middle child.
		 * @return true if the node has a middle child.
		 */
		public boolean hasMiddleChild()
		{
			return middleChild != null;
		}
		
		/**
		 * detects whether the node has a right child.
		 * @return true if the node has a right child.
		 */
		public boolean hasRightChild()
		{
			return rightChild != null;
		}
		
		/**
		 * Detects whether this node is a leaf.
		 * @return True if the node is a leaf
		 */
		public boolean isLeaf()
		{
			return(leftChild == null) && (middleChild == null) && (rightChild == null);
		}
		
		/**
		 * Counts the nodes in the subtree rooted at the node
		 * @return the number of nodes in the subtree rooted at the node.
		 */
		public int getNumberOfNodes()
		{
			int leftNumber = 0; 
			int middleNumber = 0;
			int rightNumber = 0;
			
			if(leftChild != null)
			{
				leftNumber = leftChild.getNumberOfNodes();
			}
			if(middleChild != null)
			{
				middleNumber = middleChild.getNumberOfNodes();
			}
			if(rightChild != null)
			{
				rightNumber = rightChild.getNumberOfNodes();
			}
			return 1 + leftNumber + middleNumber + rightNumber;
		}
		
		/**
		 * Computes the height of the subtree rooted at the node.
		 * @return the height of the subtree rooted at the node
		 */
		public int getHeight()
		{
			return getHeight(this); //calls private getHeight
		}
		private int getHeight(TernaryNode<T> node)
		{
			int height = 0;
			if(node != null)
			{
				height = 1 + Math.max(Math.max(getHeight(node.leftChild), getHeight(node.middleChild)), 
												getHeight(node.rightChild));
			}
			return height;
		}
		
		/**
		 * Copies the subtree rooted at the node.
		 * @return the root of a copy of the subtree rooted at the node.
		 */
		public TernaryNode<T> copy()
		{
			TernaryNode<T> newRoot = new TernaryNode<>(data);
			if(leftChild != null)
			{
				newRoot.setLeftChild(leftChild.copy());
			}
			if(middleChild != null)
			{
				newRoot.setMiddleChild(middleChild.copy());
			}
			if(rightChild != null)
			{
				newRoot.setRightChild(rightChild.copy());
			}
			return newRoot;
		}
	}
	
	@Override
	public void setTree(T rootData) 
	{
//		root = new TernaryNode<>(rootData);
		privateSetTree(rootData, null, null, null);
	}

	@Override
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree,
			TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree) 
	{
		privateSetTree(rootData,(TernaryTree<T>) leftTree, (TernaryTree<T>) middleTree, (TernaryTree<T>) rightTree);
	}
	
	public void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree)
	{
		root = new TernaryNode<>(rootData);
			
		if((leftTree != null) && !leftTree.isEmpty())
		{
			root.setLeftChild(leftTree.root);
		}
		if((middleTree != null) && !middleTree.isEmpty())
		{
			root.setMiddleChild(middleTree.root);
		}
		if((rightTree != null) && !rightTree.isEmpty())
		{
			root.setRightChild(rightTree.root);
		}
	}
	
	public TernaryNode<T> getLeftChild()
	{
		return root.getLeftChild();
	}
	public TernaryNode<T> getMiddleChild()
	{
		return root.getMiddleChild();
	}
	public TernaryNode<T> getRightChild()
	{
		return root.getRightChild();
	}
	
	public boolean hasLeftChild()
	{
		return root.hasLeftChild();
	}
	public boolean hasRightChild()
	{
		return root.hasRightChild();
	}
	public boolean hasMiddleChild()
	{
		return root.hasMiddleChild();
	}
	
	@Override
	public T getRootData()
	{
		if(isEmpty())
		{
			throw new EmptyTreeException();
		}
		else
		{
			return root.getData();
		}
	}

	@Override
	public int getHeight() 
	{
		return root.getHeight();
	}

	@Override
	public int getNumberOfNodes() 
	{
		return root.getNumberOfNodes();
	}

	@Override
	public boolean isEmpty() 
	{
		return root == null;
	}

	@Override
	public void clear() 
	{
		root = null;
	}

	@Override
	public Iterator<T> getPreorderIterator() 
	{
		return new PreorderIterator();
	}

	@Override
	public Iterator<T> getPostorderIterator() 
	{
		return new PostorderIterator();
	}

	/**
	 * This method is not supported because it is used to check for a left and right child,
	 * which in the case of a ternary tree, does not make sense. 
	 */
	@Override
	public Iterator<T> getInorderIterator() 
	{
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Iterator<T> getLevelOrderIterator() 
	{
		return new LevelOrderIterator();
	}
	
	class PreorderIterator implements Iterator<T>
	{
		private StackInterface<TernaryNode<T>> nodeStack;
		
		public PreorderIterator()
		{
			nodeStack = new LinkedStack<>();
			if(root != null)
			{
				nodeStack.push(root);
			}
		}
		
		@Override
		public boolean hasNext()
		{
			return !nodeStack.isEmpty();
		}

		@Override
		public T next() 
		{
			TernaryNode<T> nextNode;
			if(hasNext())
			{
				nextNode = nodeStack.pop();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				
				//push into stack in reverse order of recursive calls
				if(rightChild != null)
				{
					nodeStack.push(rightChild);
				}
				if(middleChild != null)
				{
					nodeStack.push(middleChild);
				}
				if(leftChild != null)
				{
					nodeStack.push(leftChild);
				}
			}
			else
			{
				throw new NoSuchElementException();
			}
			
			return nextNode.getData();
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
		public void iterativePreorderTraverse()
		{
			StackInterface<TernaryNode<T>> nodeStack = new LinkedStack<>();
			if(root != null)
			{
				nodeStack.push(root);
			}
			TernaryNode<T> nextNode;
			while(!nodeStack.isEmpty())
			{
				nextNode = nodeStack.pop();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				
				//pushing into stack in reverse order of recursive calls
				if(rightChild != null)
				{
					nodeStack.push(rightChild);
				}
				if(middleChild != null)
				{
					nodeStack.push(middleChild);
				}
				if(leftChild != null)
				{
					nodeStack.push(leftChild);
				}
				System.out.print(nextNode.getData() + " ");
			}
		}
		
	}
	
	class PostorderIterator implements Iterator<T>
	{
		private StackInterface<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;
		
		//constructor
		public PostorderIterator()
		{
			nodeStack = new LinkedStack <>();
			currentNode = root;
		}

		@Override
		public boolean hasNext() 
		{
			return !nodeStack.isEmpty() || (currentNode != null);
		}

		@Override
		public T next() 
		{
			//boolean foundNext = false;
			TernaryNode<T> leftChild, middleChild, nextNode = null;
			
			//find leftmost leaf
			while(currentNode != null)
			{
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				middleChild = currentNode.getMiddleChild();
				if(leftChild == null)
				{
					if(middleChild == null)
					{
						currentNode = currentNode.getRightChild();
					}
					else
					{
						currentNode = middleChild;
					}
				}
				else
				{
					currentNode = leftChild;
				}
			}
			
			//Stack is not empty either because we just pushed a node, or
			//it wasn't empty to begin with since hasNext is true.
			//But Iterator specifies an exception for next() in case
			//has Next() is false.
			
			if(!nodeStack.isEmpty())
			{
				nextNode = nodeStack.pop();
				//next Node != null since stack was not empty before pop
				
				TernaryNode<T> parent = null;
				if(!nodeStack.isEmpty())
				{
					parent = nodeStack.peek();
					if(nextNode == parent.getLeftChild())
					{
						currentNode = parent.getMiddleChild();
					}
					else if(nextNode == parent.getMiddleChild())
					{
						currentNode = parent.getRightChild();
					}
					else
					{
						currentNode = null;
					}
				}
				else
				{
					currentNode = null;
				}
			}
			else
			{
				throw new NoSuchElementException();
			}
			
			return nextNode.getData();
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
	}
	
	class LevelOrderIterator implements Iterator<T>
	{
		private QueueInterface<TernaryNode<T>> nodeQueue;
		
		public LevelOrderIterator()
		{
			nodeQueue = new LinkedQueue<>();
			if(root != null)
			{
				nodeQueue.enqueue(root);
			}
		}

		@Override
		public boolean hasNext() 
		{
			return !nodeQueue.isEmpty();
		}

		@Override
		public T next() 
		{
			TernaryNode<T> nextNode;
			
			if(hasNext())
			{
				nextNode = nodeQueue.dequeue();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				
				if(leftChild != null)
				{
					nodeQueue.enqueue(leftChild);
				}
				if(middleChild != null)
				{
					nodeQueue.enqueue(middleChild);
				}
				if(rightChild != null)
				{
					nodeQueue.enqueue(rightChild);
				}
			}
			else
			{
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
	}

}


