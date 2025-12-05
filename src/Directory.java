import java.util.ArrayList;
import java.util.List;

public class Directory extends FSNode {
    private List<FSNode> children;

    public Directory(String name, Directory parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }

    public void addChild(FSNode node) { children.add(node); }
    public void removeChild(FSNode node) { children.remove(node); }
    public List<FSNode> getChildren() { return children; }

    public FSNode getChild(String name) {
        for (FSNode node : children) {
            if (node.getName().equals(name)) return node;
        }
        return null;
    }

    @Override
    public boolean isDirectory() { return true; }
}