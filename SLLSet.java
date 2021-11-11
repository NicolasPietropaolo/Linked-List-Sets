

public class SLLSet {
    private int setSize;
    private SLLNode listHead;

 // Empty set constructor
    public SLLSet() {
        setSize = 0;
        listHead = null;
    }

    //set constructor
    public SLLSet(int[] sortedArray) {
        setSize = sortedArray.length;

        // Sets the listHead as first element in the sortedArray
        listHead = new SLLNode(sortedArray[0], null);
        SLLNode cElement= listHead;
        for (int i = 1; i < sortedArray.length; i++) {
            cElement.next = new SLLNode(sortedArray[i], null); //links node to previous ones
            cElement = cElement.next; // Updates cElement node
        }
    }

    public int getSize() {// Getter implemented for private setSize field
        return setSize;
    }

    public SLLSet copy() {// obtains deep copy
        int[] dataSet = new int[setSize]; // Allocates space for copied dataSet
        SLLNode cElement = listHead;

        int indx = 0;
        while (cElement != null) { // goes through linked list
            dataSet[indx] = cElement.value; // copies value from the cElement node to new list
            cElement = cElement.next;
            indx++;
        }
        return new SLLSet(dataSet); // Initializes and returns the new linkedList which is based on the copied list
    }

    public boolean isIn(int v) {// verifies if a certain value is in linkedList
        SLLNode cElement = listHead;
        while (cElement != null) { //goes through linkedList
            if (cElement.value == v)
                return true; // return true if the cElement node value matches the query
            cElement = cElement.next;
        }
        return false;
    }

    public void add(int v) {// adds additional value into its correct spot within sorted linkedList
        if (!isIn(v)) {
            if (listHead == null) { listHead = new SLLNode(v, null); }      // empty condition
            else if (listHead.value > v) { listHead = new SLLNode(v, listHead); }  //if the value is smaller than the listHead
            else {      // all other cases
                SLLNode cElement = listHead;
                while (cElement.next != null) {  // goes through linkedList relative to next value
                    if (cElement.next.value > v) {  // verifies if  next value is larger than queried value
                        cElement.next = new SLLNode(v, cElement.next); // Inserts value into linkedList
                        setSize++;
                        return;
                    }
                    cElement = cElement.next;
                }
                cElement.next = new SLLNode(v, null);       // Case for inserting as last value
            }
            setSize++;
        }
        else { System.out.println("Value: " + "\'" + v + "\' " + "is already in the list." ); }
    }

    public void remove(int v) {     //function removes value from linkedList

        if (isIn(v)) {  
            if (listHead.value == v) { listHead = listHead.next; }   // Removing the listHead value of the list
            else {                                       // All other cases
                SLLNode cElement = listHead;
                while (cElement != null) {
                    if (cElement.next.value == v) {
                        cElement.next = cElement.next.next; // Break the link with the value to be removed and link cElement node to the next node
                        break;
                    }
                    cElement = cElement.next; //increment set
                }
            }
            setSize--;		//decrement size
        }
        else { System.out.println("Value: " + "\'" + v + "\' " + "is not in the list."); }
    }

    
    public SLLSet union(SLLSet s) {// Finds union between 2 ordered sets
        SLLSet setsUnion = new SLLSet(); // initialize set
        SLLNode cElement1 = listHead;
        SLLNode cElement2 = s.listHead;
        SLLNode unioncElement = setsUnion.listHead;

        while (cElement1 != null && cElement2 != null) {
            if (cElement1.value < cElement2.value) {  // First less than
               
               if (unioncElement == null) { setsUnion.listHead = new SLLNode(cElement1.value, null); }
               		else { unioncElement.next = new SLLNode(cElement1.value, null); }
                		unioncElement = unioncElement.next;

                setsUnion.add(cElement1.value);  // Add first to new set
                cElement1 = cElement1.next;  // Increment only first set
            }
            else if (cElement2.value < cElement1.value)  { // Second less than first
            	if (unioncElement == null) { setsUnion.listHead = new SLLNode(cElement2.value, null); }
            		else { unioncElement.next = new SLLNode(cElement2.value, null); }
            			unioncElement = unioncElement.next;
            	
                setsUnion.add(cElement2.value);  // Add second to new set
                cElement2 = cElement2.next;  // Increment only second set
            }
            else {  // First equal to second
            	if (unioncElement == null) { setsUnion.listHead = new SLLNode(cElement1.value, null); }
            		else { unioncElement.next = new SLLNode(cElement1.value, null); }
            			unioncElement = unioncElement.next;

                setsUnion.add(cElement1.value);  // Add either first or second to new set (first in this case)
                cElement1 = cElement1.next;  // Increment first set
                cElement2 = cElement2.next;  // Increment second set
            }
        }
//look for set that isn't equal to null, which is the larger list as values still remain in it which haven't been added to the new set 
											
        while (cElement1 != null){ 
            setsUnion.add(cElement1.value);
            cElement1 = cElement1.next;
        }
        while (cElement2 != null) {			
            setsUnion.add(cElement2.value); // all values are added from previous set to new set
            cElement2 = cElement2.next;
        }
        return setsUnion;
    }

    public SLLSet intersection(SLLSet s) {
        SLLSet intersectSet = new SLLSet(); // Function finds intersection between two sets
        SLLNode cElement1 = listHead; 
        SLLNode cElement2 = s.listHead;

        // Same idea as union except only add values to  new set when both sets contain same value
        while (cElement1 != null && cElement2 != null) {
            if (cElement1.value < cElement2.value) {
                cElement1 = cElement1.next;
            }
            else if (cElement2.value < cElement1.value) {
                cElement2 = cElement2.next;
            }
            else {
                intersectSet.add(cElement1.value); // Only add to new set when both values are equal
                cElement1 = cElement1.next;
                cElement2 = cElement2.next;
            }
        }
        
        return intersectSet;
    }

    public SLLSet difference(SLLSet s) {
        SLLSet diffSet = new SLLSet(); // function finds  difference between two ordered sets
        SLLNode cElement = listHead;

        while (cElement != null) {
            if (!s.isIn(cElement.value)) {  // verifies if  cElement node value(x) is not present s(y)
                diffSet.add(cElement.value);  // values added to new set
            }
            cElement = cElement.next; //go through values
        }
        return diffSet;
    }

    public static SLLSet union(SLLSet[] sArray) { // function finds union between every set in array
        SLLSet output = sArray[0]; // Set initial set as first set in sArray
        for (int i = 1; i < sArray.length; i++) { // goes through sArray, discovering the  between each set and then setting output as the new set
            output = output.union(sArray[i]);
        }
        return output;
    }

    public String toString() { 
        String result = new String(); //create new string
        result += "{ ";
        SLLNode cElement = listHead; 
        while (cElement != null) { //goes through all cases in cElement
            result += cElement.value;
            result += (cElement.next == null) ? " " : ", ";
            cElement = cElement.next;
        }
        result += "}\n";//new character
        
        return result;
    
    }

}