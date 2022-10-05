public class Node <T>{
    private T data;
    private Node<T> nextNode;

    public Node(){
        this(null, null);
    }

    public Node(T data){
        this(data, null);
    }

    public Node(T data, Node<T> nextNode){
        this.data = data;
        this.nextNode = nextNode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNextNode() {
        return nextNode;
    }
}
