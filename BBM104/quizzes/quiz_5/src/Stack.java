public class Stack <T>{
    private Node<T> firstNode,
                    lastNode;
    private int nodeCounter = 0;

    public Stack(){
        firstNode = lastNode = null;
    }

    public void push (T data) throws StackIsFullException{
        Node<T> tempNode = new Node<>(data);

        if(isFull())
            throw new StackIsFullException();

        if(isEmpty())
            firstNode = lastNode = tempNode;
        else {
            lastNode.setNextNode(tempNode);
            lastNode = tempNode;
        }
        nodeCounter++;
    }

    public T pop() throws StackIsEmptyException{
        if(isEmpty())
            throw new StackIsEmptyException();
        T poppedItem = lastNode.getData();
        nodeCounter--;
        if(isEmpty())
            firstNode = lastNode = null;
        else{
            Node<T> tempNode = firstNode;
            while(tempNode.getNextNode() != lastNode)
                tempNode = tempNode.getNextNode();
            tempNode.setNextNode(null);
            lastNode = tempNode;
        }
        return poppedItem;
    }

    public Node<T> Top(){
        return lastNode;
    }

    public boolean isFull(){
        return nodeCounter == 50;
    }

    public boolean isEmpty(){
        return nodeCounter == 0;
    }

    public int Size(){
        return  nodeCounter;
    }
}

class StackIsFullException extends Exception{}

class StackIsEmptyException extends Exception{}