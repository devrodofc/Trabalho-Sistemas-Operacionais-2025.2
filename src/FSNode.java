import java.io.Serializable;

public abstract class FSNode implements Serializable {
    protected String name;
    protected Directory parent;

    public FSNode(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Directory getParent() { return parent; }
    public void setParent(Directory parent) { this.parent = parent; }
    
    public abstract boolean isDirectory();
}