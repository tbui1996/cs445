package cs445.a1;

public class Set<E> implements SetInterface<E> 
{
	private E[] set; //contains all entries in the set
	private int size; //current number of entries in the set
	private int capacity; //initial capacity of set
	
	@SuppressWarnings("unchecked")
	//constructor
	public Set(int capacity)
	{
		this.capacity = capacity;
		set = (E[]) new Object[capacity];
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	//another constructor
	public Set()
	{
		this.capacity = 10;
		this.set = (E[]) new Object[10];
		this.size = 0;
	}
	
	public int getCurrentSize() 
	{
		return this.size;
	}

	public boolean isEmpty()
	{

		return (this.size == 0);
	}

	public boolean add(E newEntry) throws SetFullException, IllegalArgumentException 
	{
		boolean result;
		
		if(newEntry == null)
		{	
			throw new java.lang.IllegalArgumentException("null entry");
		}	
		if(contains(newEntry))
		{	
			result = false;
		}
		if(size == set.length)
		{
			throw new SetFullException();
		}
		
		set[size] = newEntry;
		size++;
		result = true;
		return result;
	}

	public boolean remove(E entry) throws IllegalArgumentException 
	{
		boolean result = false;
		if (!contains(entry))
		{
			result = false;
		}
		if(entry == null)
		{
			throw new java.lang.IllegalArgumentException("entry is null");
		}
		for(int k = 0; k < size; k++)
		{
			if(entry == set[k])
			{
				set[k] = set[size];
				set[size] = null;
				size--;
				result = true;
			}
		}
		return result;
	}

	public E remove() 
	{
		if(isEmpty())
		{
			return null;
		}
		
		E result = set[size - 1];
		set[size-1] = null;
		size--;
		return result;
	}

	public void clear() 
	{
		if(!isEmpty())
		{
			for(int k = 0; k < size; k++)
			{
				set[k] = null;
			}
		}
	}

	public boolean contains(E entry) throws IllegalArgumentException 
	{
		boolean result = false;
		
		if(isEmpty())
		{
			result = false;
		}
		
		if(entry == null)
		{
			throw new IllegalArgumentException("null entry");
		}
		
		for(E k: set)
		{
			if(entry == k)
			{
				result = true;
			}
		}
		return result;
		
	}

	public E[] toArray() 
	{
		@SuppressWarnings("unchecked")
		E[] referenceSet = (E[]) new Object[size];
		for(int k = 0; k < size; k++)
		{
			referenceSet[k] = (E) set[k];
		}
		return referenceSet;
		
		
	}

}
