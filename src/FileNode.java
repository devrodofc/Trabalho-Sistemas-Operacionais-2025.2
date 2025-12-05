public class FileNode extends FSNode {
    private String content;

    public FileNode(String name, Directory parent, String content) {
        super(name, parent);
        this.content = content;
    }

    @Override
    public boolean isDirectory() { return false; }
    
    public String getContent() { return content; }
}