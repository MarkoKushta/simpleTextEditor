/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextEditorProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.SupportedSourceVersion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author oltasula
 */
/**
 * LinkedList class implements a doubly-linked list.
 */
public class LinkedList<AnyType> extends AbstractCollection<AnyType>
{

    /**
     * Construct an empty LinkedList.
     */
    public LinkedList()
    {
        clear();
    }

    /**
     * Construct a LinkedList with same items as another Collection.
     */
    public LinkedList(Collection<? extends AnyType> other)
    {
        clear();
        for (AnyType val : other)
        {
            add(val);
        }
    }

    /**
     * Change the size of this collection to zero.
     */
    public void clear()
    {
        beginMarker = new Node<AnyType>(null, null, null);
        endMarker = new Node<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * Returns the number of items in this collection.
     *
     * @return the number of items in this collection.
     */
    public int size()
    {
        return theSize;
    }

    /**
     * Tests if some item is in this collection.
     *
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    public boolean contains(Object x)
    {
        return findPos(x) != NOT_FOUND;
    }

    /**
     * Returns the position of first item matching x in this collection, or
     * NOT_FOUND if not found.
     *
     * @param x any object.
     * @return the position of first item matching x in this collection, or
     * NOT_FOUND if not found.
     */
    private Node<AnyType> findPos(Object x)
    {
        for (Node<AnyType> p = beginMarker.next; p != endMarker; p = p.next)
        {
            if (x == null)
            {
                if (p.data == null)
                {
                    return p;
                }
            } else if (x.equals(p.data))
            {
                return p;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Adds an item to this collection, at the end.
     *
     * @param string any object.
     * @return true.
     */
    public boolean add(AnyType string)
    {
        addLast(string);
        return true;
    }

    /**
     * Adds an item to this collection, at specified position. Items at or after
     * that position are slid one position higher.
     *
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(),
     * inclusive.
     */
    public void add(int idx, AnyType x)
    {
        Node<AnyType> p = getNode(idx, 0, size());
        Node<AnyType> newNode = new Node<AnyType>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    /**
     * Adds an item to this collection, at front. Other items are slid one
     * position higher.
     *
     * @param x any object.
     */
    public void addFirst(AnyType x)
    {
        add(0, x);
    }

    /**
     * Adds an item to this collection, at end.
     *
     * @param x any object.
     */
    public void addLast(AnyType x)
    {
        add(size(), x);
    }

    /**
     * Returns the front item in the list.
     *
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType element()
    {
        try
        {
            return getFirst();
        } catch (NoSuchElementException ex)
        {
            Logger.getLogger(LinkedList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns the first item in the list.
     *
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType getFirst() throws NoSuchElementException
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        return getNode(0).data;
    }

    /**
     * Returns the last item in the list.
     *
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType getLast() throws NoSuchElementException
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        return getNode(size() - 1).data;
    }

    /**
     * Returns the item at position idx.
     *
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get(int idx)
    {
        return getNode(idx).data;
    }

    /**
     * Changes the item at position idx.
     *
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set(int idx, AnyType newVal)
    {
        Node<AnyType> p = getNode(idx);
        AnyType oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }

    /// Lesson 5 Exercises
    /**
     * Substitute one element x of the list with another one y.
     *
     * @param x the item to substitute.
     * @param y the item which will replace x when found
     */
    //HW do it by using methods find and set
    public void substitute(AnyType x, AnyType y, Comparator<AnyType> cmp)
    {
        for (int i = 0; i < this.theSize; i++)
        {
            Node<AnyType> p = getNode(i);
            if (cmp.compare(p.data, x) == 0)
            {
                p.data = y;
            }
        }
    }
    
  
    /// Midterm Mock Exercises
    /**
     * At each occurrence of element x in the list, 
     * if y is the element before x and z is the one after x, substitute y with x and z with y.
     *
     * @param x the item to find and substitute yxz with xxy
     *
     */
    public void substitute(AnyType x, Comparator<AnyType> cmp)
    {
        for (int i = 0; i < this.theSize; i++)
        {
            Node<AnyType> p = getNode(i);
            if (cmp.compare(p.data, x) == 0)
            {
                p.next.data = p.prev.data;
                p.prev.data = x;
            }
        }
    }
    /**
     * At each occurrence of element x in the list, 
     * if y is the element before x and z is the one after x, substitute y with z and z with y.
     *
     * @param x the item to find and substitute yxz with zxy
     *
     */
    public void substituteYXZwithZXY(AnyType x, Comparator<AnyType> cmp)
    {
        for (int i = 0; i < this.theSize; i++)
        {
            Node<AnyType> p = getNode(i);
            if (cmp.compare(p.data, x) == 0)
            {
                AnyType temp = p.prev.data; //this is y
                p.prev.data = p.next.data;
                p.next.data = temp;
//                p.next.data = p.prev.data;
//                p.prev.data = x;
            }
        }
    }
    
    /**
     * Shows all the elements of the list.
     *
     * 
     */
    public void showList()
    {
        for (int i = 0; i < this.theSize; i++)
        {
            System.out.println(getNode(i).data.toString());
        }
    }
    /// Lesson 5 Exercises
    /**
     * Remove elements from index1 to index2 (including elements at both indexes).
     *
     * @param index1 the index where to start  removing elements
     * @param index2 the index where to end  removing elements
     */
    public void remove(int index1, int index2)
    {
        for (int i = index1; i <= index2; i++)
        {
            remove(index1);
        }
    }
    /// Lesson 5 Exercises
    /**
     * Exchange elements in index1 and index2.
     *
     * @param index1 the index of the element to swap with index2
     * @param index2 the index of the element to swap with index1
     */
    public void swap(int index1, int index2)
    {
//        AnyType data1 = getNode(index1).data;
//        Node<AnyType> p2 = getNode(index2);
//        this.set(index1, p2.data);
//        this.set(index2, data1);
        
        AnyType v1 = get(index1);
        AnyType v2 = get(index2);
        set(index1,v2);
        set(index2,v1);
    }
    
    /// Midterm Mock Exercises
    /**
     * Exchange elements in index1 and index2 with the following condition: 
     * if the element in index1 is larger than the one in index2 then swap, 
     * otherwise swap the elements in index1+1 and index2+1.
     *
     * @param index1 
     * @param index2
     */
    public void checkAndSwap(int index1, int index2, Comparator<AnyType> cmp)

    {
        

        if (index1 >= 0 && index1 < size() && index2 >= 0 && index2 < size())
        {
            AnyType data1 = getNode(index1).data;
            AnyType data2 = getNode(index2).data;

            if (cmp.compare(data1, data2) > 0)
            {
                
                //swap(index1, index2);
                this.set(index1, data2);
                this.set(index2, data1);
            } else if (index1 + 1 >= 0 && index1 + 1 < size() && index2 + 1 >= 0 && index2 + 1 < size())
            {
                data1 = getNode(index1+1).data;
                data2 = getNode(index2+1).data;
                this.set(index1+1, data2);
                this.set(index2+1, data1);
                //swap(index1 + 1, index2 + 1);
            } else
            {
                System.out.println("Index out of bounds error!");
            }
        } else
        {
            System.out.println("Index out of bounds error!");
        }

    }

    /// Lesson 5 Exercises
    /**
     * Copy elements from index1 to index2, and paste them after index3 preserving the order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index after which to paste the copied elements
     */
    public void copyAndPaste(int index1, int index2, int index3)
    {

        if (index3 > index2)
        {
            for (int i = index2; i >= index1; i--)
            {
                index3++;
                this.add(index3, getNode(i).data);
            }
        } else
        {
            for (int i = index1; i <= index2+2; i = i + 2)
            {
                index3++;
                this.add(index3, getNode(i).data);
            }
        }
    }
       public void copyAndPasteIntoList(int index1, int index2, int index3, LinkedList<AnyType> list2)
    {

//        if (index3 > index2)
//        {
            for (int i = index2; i >= index1; i--)
            {
                //index3++;
                list2.add(index3+1, getNode(i).data);
            }
//        } else
//        {
//            for (int i = index1; i <= index2+2; i = i + 2)
//            {
//                index3++;
//                list2.add(index3, getNode(i).data);
//            }
//        }
    }
    
    /// Midterm Mock Exercises
    /**
     * Copy elements from index1 to index2, and paste them before index3 preserving the order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index before which to paste the copied elements
     */
    public void copyAndPasteBefore(int index1, int index2, int index3)
    {

        if (index3 > index2)
        {
            for (int i = index2; i >= index1; i--)
            {
                //index3++;
                this.add(index3, getNode(i).data);
            }
        } else
        {
            for (int i = index2; i >= index1; i--)
            {
                //index3++;
                this.add(index3, getNode(i).data);
            }
        }
    }
    
    public void copyAndPasteBeforeReverse(int index1, int index2, int index3)
    {

        if (index3 > index2)
        {
            for (int i = index1; i >= index2; i++)
            {
                //index3++;
                this.add(index3, getNode(i).data);
            }
        } else
        {
            for (int i = index1; i >= index2; i++)
            {
                //index3++;
                this.add(index3, getNode(i).data);
            }
        }
    }
    /// Lesson 5 Exercises
    /**
     * Cut elements from index1 to index2, and paste them after index3 preserving the order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index after which to paste the cut elements
     */
    public void cutAndPaste(int index1, int index2, int index3)
    {
//        for(int i=index1;i<=index2;i++){
//            index3++;
//            this.add(index3, getNode(i).data);
//        }

        if (index3 > index2)
        {
            copyAndPaste(index1, index2, index3);
            remove(index1, index2);
        } else
        {
            //no of elements we have copied
            int count = index2 - index3 + 1;
            copyAndPaste(index1, index2, index3);
            remove(index1 + count, index2 + count);
        }

    }
    /// Lesson 5 Exercises
    /**
     * Cut elements from the list from index1 to index2, and paste them into List2 after index3 preserving the order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index after which to paste the cut elements
     * @param list2 the new list where to look for index3
     */
    public void cutAndPasteIntoList(int index1, int index2, int index3, LinkedList<AnyType> list2)
    {
        for (int i = index1; i <= index2; i++)
        {
            index3++;
            list2.add(index3, getNode(i).data);
        }
        remove(index1, index2);
    }
    /**
     * Copy elements from the list from index1 to index2, and paste them into List2 before index3 preserving the order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index after which to paste the copied elements
     * @param list2 the new list where to look for index3
     */
    public void copyAndPasteIntoListBefore(int index1, int index2, int index3, LinkedList<AnyType> list2)
    {
        for (int i = index2; i >= index1; i--)
        {
            //index3++;
            list2.add(index3, getNode(i).data);
        }
        //remove(index1, index2);
    }

    /// Lesson 5 Exercises
    /**
     * Cut the elements from the list from index1 to index2, and paste them into List2 after index3 in reverse order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index after which to paste the cut elements in reverse order
     * @param list2 the new list where to look for index3
     */
    public void cutAndPasteIntoListReverse(int index1, int index2, int index3, LinkedList<AnyType> list2)
    {
        index3++;
        for (int i = index1; i <= index2; i++)
        {
            list2.add(index3, getNode(i).data);
        }
        remove(index1, index2);
    }

    //adds into list2 elements from index1 to index2 in the same order as the original list
    //elements are added before index3 of list2
    /**
     * Cut the elements from the list from index1 to index2, and paste them into List2 before index3 in the original order.
     *
     * @param index1 where to start copying
     * @param index2 where to stop copying
     * @param index3 the index before which to paste the cut elements in the original order
     * @param list2 the new list where to look for index3
     */
    public void cutAndPasteIntoListBefore(int index1, int index2, int index3, LinkedList<AnyType> list2)
    {
        //index3++;
        for (int i = index1; i <= index2; i++)
        {
            list2.add(index3, getNode(i).data);
        }
        remove(index1, index2);
    }

    /// Lesson 5 Exercises
    /**
     * Move all the occurrences of element x at the end of the list.
     *
     * @param x the element to find and move to the end of the list
     */
    public void moveAtTheEnd(AnyType x, Comparator<AnyType> cmp)
    {
        int initialSize = this.theSize;
        int index = 0;
        for (int i = 0; i < initialSize - 1; i = index)
        {
            if (cmp.compare(getNode(i).data, x) == 0)
            {
                this.cutAndPaste(i, i, this.theSize - 1);
                initialSize--;
                index = i;
                continue;
            }
            index++;
        }
    }
    /// Lesson 5 Exercises
    /**
     * In a sorted list, move all elements larger than x at the beginning of the list.
     *
     * @param x the value to find elements larger than, and move them to the beginning of the list
     */
    //HW more efficient way for sorted linked list
    //check the second way problem
    public void moveLargerAtTheFront(AnyType x, Comparator<AnyType> cmp)
    {
        for (int i = 0; i < this.theSize; i++)
        {
            if (cmp.compare(getNode(i).data, x) > 0)
            {
                //AnyType temp = getNode(i).data;
                cutAndPaste(i,i,0);
//                remove(i);
//                addFirst(temp);
            }
        }
    }

    /**
     * Move the element x at the beginning of the list.
     *
     * @param x the value to find and move to the beginning of the list
     */
    public void moveAtTheFront(AnyType x, Comparator<AnyType> cmp)
    {
        for (int i = 0; i < this.theSize; i++)
        {
            if (cmp.compare(getNode(i).data, x) == 0)
            {
                remove(i);
                addFirst(x);
            }
        }
    }

    public int getIndex(AnyType x)
    {
        int index = -1; //not found case
        for (int i = 0; i < this.theSize; i++)
        {
            if (getNode(i).data.equals(x))
            {
                return i;
            }
        }
        return index;
    }

    //Lesson 5 Exercises
    /**
     * Replace all the occurrences of element x with the pattern y, x, z.
     *
     * @param x the value to find and replace with y, x, z
     * @param y
     * @param z
     */
    public void surround(AnyType x, AnyType y, AnyType z, Comparator<AnyType> cmp)
    {
        int index = 0;
        for (int i = 0; i < this.theSize; i = index)
        {
            if (cmp.compare(getNode(i).data, x) == 0)
            {
                add(i, y);
                add(i + 2, z);
                index = i + 3; /// jump one element
                continue;
            }
            //to check
            index++;
        }
    }
    /**
     * Clone a doubly linkedList.
     *
     * 
     */
    public LinkedList<AnyType> clone( )
    {
        LinkedList<AnyType> clone = new LinkedList<AnyType>();
        LinkedListIterator itr = (LinkedListIterator) this.iterator();
        
        while (itr.hasNext())
        {
            clone.add(itr.current.data);
            itr.next();
        }
        return clone;
    }
    
    //Midterm Mock Exercises
    /**
     * Clone a linked list by removing the duplicates.
     * @param cmp
     * @return a new LinkedList without duplicates
     */
    public LinkedList cloneWithoutDuplicates(Comparator<AnyType> cmp )
    {
        LinkedList<AnyType> clone = new LinkedList<AnyType>();
        LinkedListIterator itr = (LinkedListIterator) this.iterator();
        LinkedListIterator cloneItr = (LinkedListIterator) clone.iterator();
        
        while (itr.hasNext())
        {
            boolean found = false;
            if (cloneItr == null)
            {
                continue;
            }
            else
            {
                cloneItr = (LinkedListIterator) clone.iterator();
                while(cloneItr.hasNext())
                {
                    if(cmp.compare(cloneItr.current.data, itr.current.data) == 0)
                    {
                        found = true;
                        break;
                    }
                    cloneItr.next();
                }
            }
            
            if(found == false)
                clone.add(itr.current.data);
            
            itr.next();
        }
        
        return clone;
    }

    /**
     * Removes the front item in the queue.
     *
     * @return the front item.
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType remove()
    {
        try
        {
            return removeFirst();
        } catch (NoSuchElementException ex)
        {
            Logger.getLogger(LinkedList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Removes the first item in the list.
     *
     * @return the item was removed from the collection.
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType removeFirst() throws NoSuchElementException
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        return remove(getNode(0));
    }

    /**
     * Removes the last item in the list.
     *
     * @return the item was removed from the collection.
     * @throws NoSuchElementException if the list is empty.
     */
    public AnyType removeLast() throws NoSuchElementException
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        return remove(getNode(size() - 1));
    }

    /**
     * Removes an item from this collection.
     *
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    public boolean remove(Object x)
    {
        Node<AnyType> pos = findPos(x);

        if (pos == NOT_FOUND)
        {
            return false;
        } else
        {
            remove(pos);
            return true;
        }
    }

    //Lab Exercises 
    //Lesson4
    /**
     * Print the LinkedList in reverse order
     */
    public void printInverse()
    {
        Node<AnyType> currentPosition = endMarker.prev;
        for (int i = 0; i < size(); i++)
        {
            AnyType element = currentPosition.data;
            System.out.println(element);
            currentPosition = currentPosition.prev;
        }
    }
     public void printInverseV2()
    {
        Node<AnyType> currentPosition = endMarker.prev;
        while (currentPosition != beginMarker)
        //for (int i = 0; i < size(); i++)
        {
            AnyType element = currentPosition.data;
            System.out.println(element);
            currentPosition = currentPosition.prev;
        }
    }

    // Lab Exercises
    /**
     * find nr of occurrences of an element
     */
    
    public int findOccurrences(AnyType x, Comparator<AnyType> cmp)
    {
        Node<AnyType> currentPosition = beginMarker.next;
        int count = 0;
        for (int i = 0; i < size(); i++)
        {
            if (cmp.compare(currentPosition.data, x) == 0)
            {
                count++;
            }
            currentPosition = currentPosition.next;
        }
        return count;
    }
    
    // Lab Exercises
    /**
     * remove duplicate elements
     */
    //HW remove all duplicates
    public void removeDublicates(Comparator<AnyType> cmp)
    {
        Node<AnyType> currentPosition = beginMarker.next;
        Node<AnyType> nextPosition;

        while (currentPosition != null)
        {
            nextPosition = currentPosition;
            while (nextPosition != null)
            {
                if (nextPosition.next != null && nextPosition.next.data != null
                        && cmp.compare(nextPosition.next.data, currentPosition.data) == 0)
                {
                    nextPosition.next = nextPosition.next.next;
                    nextPosition.next.prev = nextPosition;
                    theSize--;
                } else
                {
                    nextPosition = nextPosition.next;
                }
            }
            currentPosition = currentPosition.next;
        }
    }

    public boolean simpleSearch(AnyType x, Comparator<AnyType> cmp)
    {
        if (cmp.compare(x)<0)
        {
            for (Node<AnyType> p = endMarker.prev; p != beginMarker; p = p.prev)
            {
                if (x.equals(p.data))
                {
                    return true;
                }
                else 
                    return false;
            }
        }
        else 
        {
            for (Node<AnyType> p = beginMarker.next; p != endMarker; p = p.next)
            {
                if (x.equals(p.data))
                {
                    return true;
                }
                else 
                    return false;
            }
        }
        return false;
    }
    public boolean binarySearch(AnyType x, Comparator<AnyType> cmp)
    {
        int low = 0;
        int mid = 0;
        int high = theSize - 1;

        while (low < high)
        {
            mid = (low + high) / 2;
            if (cmp.compare(x, getNode(mid).data) > 0)
            {
                low = mid + 1;
            } else
            {
                high = mid;
            }
        }

        if (low == theSize - 1 || cmp.compare(x, getNode(low).data) != 0)
        {
            return false;
        }
        return true;
    }

    public void insertSorted(AnyType x, Comparator<AnyType> cmp)
    {
        Node<AnyType> currentPosition = beginMarker.next;
        int count = 0;
        for (int i = 0; i < size(); i++)
        {
            if (cmp.compare(x, currentPosition.data) < 0)
            {
                break;
            }
            count++;
            currentPosition = currentPosition.next;
        }
        //int pos = getIndex(currentPosition.data);
        add(count, x);
    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( )-1.
     *
     * @param idx index to search at.
     * @return internal node corrsponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size()-1,
     * inclusive.
     */
    private Node<AnyType> getNode(int idx)
    {
        return getNode(idx, 0, size() - 1);
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     *
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corrsponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper,
     * inclusive.
     */
    private Node<AnyType> getNode(int idx, int lower, int upper)
    {
        Node<AnyType> p;

        if (idx < lower || idx > upper)
        {
            throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());
        }

        if (idx < size() / 2)
        {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++)
            {
                p = p.next;
            }
        } else
        {
            p = endMarker;
            for (int i = size(); i > idx; i--)
            {
                p = p.prev;
            }
        }

        return p;
    }

    /**
     * Removes an item from this collection.
     *
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove(int idx)
    {
        return remove(getNode(idx));
    }

    /**
     * Removes the object contained in Node p.
     *
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove(Node<AnyType> p)
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     *
     * @return an iterator positioned prior to the first element.
     */
    public Iterator<AnyType> iterator()
    {
        return (Iterator<AnyType>) new LinkedListIterator(0);
    }

    /**
     * Obtains a ListIterator object used to traverse the collection
     * bidirectionally.
     *
     * @return an iterator positioned prior to the requested element.
     * @param idx the index to start the iterator. Use size() to do complete
     * reverse traversal. Use 0 to do complete forward traversal.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(),
     * inclusive.
     */
    public ListIterator<AnyType> listIterator(int idx)
    {
        return  (ListIterator<AnyType>) new LinkedListIterator(idx);
    }

    /**
     * This is the implementation of the LinkedListIterator. It maintains a
     * notion of a current position and of course the implicit reference to the
     * LinkedList.
     */
    class LinkedListIterator implements Iterator
    {

        private Node<AnyType> current;
        private Node<AnyType> lastVisited = null;
        private boolean lastMoveWasPrev = false;
        private int expectedModCount = modCount;
        

        public int getIndexOfCurrent(){

           return getIndex((AnyType) current);

        }

        public  LinkedListIterator(int idx)
        {
            current = getNode(idx, 0, size());
          
        }

        public boolean hasNext()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            return current != endMarker;
        }

        public AnyType next()
        {
            if (!hasNext())
                try
            {
                throw new NoSuchElementException();
            } catch (NoSuchElementException ex)
            {
                Logger.getLogger(LinkedList.class.getName()).log(Level.SEVERE, null, ex);
            }

            current = current.next;
            lastVisited = current;
            lastMoveWasPrev = false;
            return current.data;
        }


        public void remove()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            if (lastVisited == null)
            {
                throw new IllegalStateException();
            }

            LinkedList.this.remove(lastVisited);
            lastVisited = null;
            if (lastMoveWasPrev)
            {
                current = current.next;
            }
            expectedModCount++;
        }

        public boolean hasPrevious()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            return current != beginMarker.next;
        }

        public AnyType previous()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            if (!hasPrevious())
                try
            {
                throw new NoSuchElementException();
            } catch (NoSuchElementException ex)
            {
                Logger.getLogger(LinkedList.class.getName()).log(Level.SEVERE, null, ex);
            }

            current = current.prev;
            lastVisited = current;
            lastMoveWasPrev = true;
            return current.data;
        }

    }

    static class NoSuchElementException extends Exception
    {

        public NoSuchElementException()
        {
            super();
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    class Node<AnyType>
    {

        public Node(AnyType d, Node<AnyType> p, Node<AnyType> n)
        {
            data = d;
            prev = p;
            next = n;
        }

        public AnyType data;
        public Node<AnyType> prev;
        public Node<AnyType> next;
    }

    private final Node<AnyType> NOT_FOUND = null;

    public int theSize;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
    private int modCount = 0;

   //My Methods
    Scanner TextSC = new Scanner(System.in);

    public static void addFileToList(LinkedList<String> x) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("texteditorproject/src/File.text"));
        while(scanner.hasNextLine()){
            x.add(scanner.nextLine());
        }
    }

    public void copyPasteAndCreate(int index1, int index2, int index3)
    {
/* 
        if (index3 > index2)
        {
            for (int i = index2; i >= index1; i--)
            {
                index3++;
                theSize++;
                this.add(index3 + 1, getNode(i).data);
           
            }
             
        } else
        {
            */
            for (int i = index1; i < index2 + 1; i++)
            {
                index3++;
                this.add(index3, getNode(i).data);
             
            }
        
        }
    //}

    public void cutPasteAndCreate(int index1, int index2, int index3)
    {
//        for(int i=index1;i<=index2;i++){
//            index3++;
//            this.add(index3, getNode(i).data);
//        }

        if (index3 > index2)
        {
            copyAndPaste(index1, index2, index3);
            remove(index1, index2);
        } else
        {
            //no of elements we have copied
            int count = index2 - index3 + 1;
            copyAndPaste(index1, index2, index3);
            remove(index1 + count, index2 + count);
        }

    }

    public Iterator<AnyType> iteratorToLast(){
        return (Iterator<AnyType>) new LinkedListIterator(theSize - 2);
    }


    public void addTextFromLL() throws IOException{
        FileWriter myWriter = new FileWriter("texteditorproject/src/File.text");

        for(int i = 0; i <= theSize - 1; i++){

            myWriter.write((String) getNode(i).data);
            myWriter.write(System.lineSeparator());
        }
        myWriter.close();
    }

    public void cutPasteAndWrite() throws IOException{
        Scanner AttributeSC = new Scanner(System.in);
        System.out.println("Start: ");
        int First = AttributeSC.nextInt();
        System.out.println("End: ");
        int Last = AttributeSC.nextInt();
        System.out.println("Move At:");
        int At = AttributeSC.nextInt();
        cutPasteAndCreate(First, Last, At);
   
    }
    
    public void substituteAndWrite() throws IOException{
        Scanner AttributeSC = new Scanner(System.in);
        System.out.println("Enter the text that you want to substitute: ");
        String Text1 = AttributeSC.nextLine();
        System.out.println("Enter what you want to substitute it with: ");
        String Text2 = AttributeSC.nextLine();
        substitute((AnyType) Text1, (AnyType)Text2, new TextComparator());
        

   }

    public void copyPasteAndWrite() throws IOException{

        Scanner AttributeSC = new Scanner(System.in);
        System.out.println("Start: ");
        int First = AttributeSC.nextInt();
        System.out.println("End: ");
        int Last = AttributeSC.nextInt();
        System.out.println("Move At:");
        int At = AttributeSC.nextInt();
        copyPasteAndCreate(First, Last, At);
       
    }

    public void toLast(){

        iteratorToLast().next();
        System.out.println(iteratorToLast().next());

    }

    public void toNext(){

        iterator().next();
        System.out.println(iterator().next());

    }

    public void currentLine(){

        System.out.println(((LinkedList<AnyType>.LinkedListIterator) iterator()).getIndexOfCurrent());
    
    }

    public void findOccurrancesBackwards(AnyType x)
    {
        int count = 0;
        for (Node<AnyType> p = endMarker.prev; p != beginMarker; p = p.prev)
        {
            if (x.equals(p.data))
            {
                count++;
            }
        }
        if(count >= 1){
            System.out.println(count + " " + "Pattern Found");
        }
    }

    public void SearchBackwards(){

        Scanner AttributeSC = new Scanner(System.in);
        System.out.println("Enter the line you're searching for: ");
        String Text = AttributeSC.nextLine();
        findOccurrancesBackwards((AnyType) Text);

    }

    
}

class TextComparator<AnyType> implements Comparator<AnyType>{

    @Override
    public int compare(AnyType Text1, AnyType Text2) {

        return (Text1.toString().compareTo(Text2.toString()));

    }
        
}
