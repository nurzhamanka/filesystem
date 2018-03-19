package csci152.fss;


public abstract class FolderOrDocument implements Comparable<FolderOrDocument> {

    private String name;
    private Folder parent;

    protected FolderOrDocument(String name, Folder parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Folder getParent() {
        return parent;
    }

    @Override
    public int compareTo(FolderOrDocument other) {
        return this.name.compareTo(other.getName());
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (o instanceof FolderOrDocument) {
            FolderOrDocument s = (FolderOrDocument) o;
            return name.equals(s.getName());
        } else return false;
    }

    public abstract boolean isFolder();
    
    @Override
    public String toString() {
        return name;
    }
	
}
