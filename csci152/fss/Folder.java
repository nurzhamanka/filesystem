package csci152.fss;

import csci152.adt.Queue;
import csci152.adt.SortedQueue;
import csci152.impl.LinkedListQueue;
import csci152.impl.LinkedListSortedQueue;

public class Folder extends FolderOrDocument {

    /* at first, I wanted to use a BSTSet for contents,
    but I had problems with removeAny(), because it
    kept failing test 14 by removing the same thing
    twice after re-adding in getAllPaths method, so
    I gave up on that idea. */
    
    private Queue<FolderOrDocument> contents;

    public Folder(String name, Folder parent) {
        super(name, parent);
        contents = new LinkedListQueue<>();
    }

    public Queue<FolderOrDocument> getContents() {
        return contents;
    }

    public void addToFolder(FolderOrDocument fod) {
        contents.enqueue(fod);
    }
    
    public FolderOrDocument removeAnyValue() {
        return contents.dequeue();
    }
    
    public void deleteFromFolder(FolderOrDocument fod) {
        
        Queue<FolderOrDocument> tempQueue = new LinkedListQueue();
        // empties the contents into a temp queue
        // except the one that needs to be deleted (it just gets thrown away)
        while (contents.getSize() > 0) {
            FolderOrDocument temp = contents.dequeue();
            if (!temp.equals(fod))
                tempQueue.enqueue(temp);
        }
        // returning back the contents
        while (tempQueue.getSize() > 0) {
            contents.enqueue(tempQueue.dequeue());
        }
    }

    /**
     * Use to check if there is an item in this folder with
     * the given name.
     * 
     * @param aName the name to check for
     * @return true if there is a document or folder with
     * aName in this folder
     */
    public boolean isNameInFolder(String aName) {

        FolderOrDocument check = new Folder(aName, null);
        Queue<FolderOrDocument> tempQueue = new LinkedListQueue();
        boolean present = false;
        // empties the contents into a temp queue
        // and checks for the needed 'check' folder
        while (contents.getSize() > 0) {
            FolderOrDocument temp = contents.dequeue();
            tempQueue.enqueue(temp);
            if (temp.equals(check))
                present = true;
        }
        
        while (tempQueue.getSize() > 0) {
            contents.enqueue(tempQueue.dequeue());
        }
        
        return present;
    }

    /**
     * Returns a listing of names (not paths) of all the items
     * in this folder.
     * 
     * @return the names of the documents and folders in this
     * folder, sorted by lexicographic order
     */
    public SortedQueue<String> getContentNames() {

        if (contents.getSize() > 0) {

            SortedQueue<String> contentNames = new LinkedListSortedQueue<>();
            Queue<FolderOrDocument> tempQueue = new LinkedListQueue<>();
            
            while (contents.getSize() > 0) {
                FolderOrDocument item = contents.dequeue();
                contentNames.insert(item.getName());
                tempQueue.enqueue(item);
            }
            
            while (tempQueue.getSize() > 0) {
                contents.enqueue(tempQueue.dequeue());
            }

            return contentNames;
            
        } else {
            // Empty Folder
            return new LinkedListSortedQueue<>();
        }
    }

    @Override
    public boolean isFolder() {
        return true;
    }


    // Don't change.... this is used for testing
    /**
     * @return queue of pathnames of all files in the system
     * rooted at the current folder
     */
    public Queue<String> getAllPaths() {
        Queue<String> results = new LinkedListQueue<>();
        results.enqueue(this.getName());
        getAllPaths(getName(), results);
        return results;
    }

    // Don't change.... this is used for testing
    protected void getAllPaths(String prePath, Queue<String> results) {

        SortedQueue<FolderOrDocument> sorted = new LinkedListSortedQueue<>();

        for (int i = 0; i < contents.getSize(); i++) {
            FolderOrDocument fod;
            try {
                fod = contents.dequeue();
                contents.enqueue(fod);

                sorted.insert(fod);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        while (sorted.getSize() > 0) {
            try {
                FolderOrDocument fod = sorted.dequeue();
                String fodPath = prePath + "/" + fod.getName();
                results.enqueue(fodPath);
                if (fod.isFolder()) {
                        Folder folder = (Folder)fod;
                        folder.getAllPaths(fodPath, results);
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
