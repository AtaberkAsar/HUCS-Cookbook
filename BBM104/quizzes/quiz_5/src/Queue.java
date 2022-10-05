public class Queue <T>{
    private Node<T> firstNode,
                    lastNode;
    private int nodeCounter = 0;

    public Queue(){
        firstNode = lastNode = null;
    }

    public void enqueueFront(T data){
        Node<T> tempNode = new Node<>(data);
        if(firstNode == null) // Queue is empty
            firstNode = lastNode = tempNode;
        else {
            tempNode.setNextNode(firstNode);
            firstNode = tempNode;
        }
        nodeCounter++;
    }

    public void enqueueMiddle(T data){
        int midNodeIndex = nodeCounter/2;
        Node<T> tempNode = new Node<>(data);

        if(firstNode == null) // Queue is empty
            firstNode = lastNode = tempNode;
        else if (nodeCounter == 1) {
            tempNode.setNextNode(firstNode);
            firstNode = tempNode;
        }
        else {
            Node<T> currentNode = firstNode;
            for(int i = 0; i < midNodeIndex - 1; i++){
                currentNode = currentNode.getNextNode();
            }
            tempNode.setNextNode(currentNode.getNextNode());
            currentNode.setNextNode(tempNode);
        }
        nodeCounter++;
    }

    public void enqueueBack(T data){
        Node<T> tempNode = new Node<>(data);
        if(firstNode == null) // Queue is empty
            firstNode = lastNode = tempNode;
        else {
            lastNode.setNextNode(tempNode);
            lastNode = tempNode;
        }
        nodeCounter++;
    }

    public Object dequeueFront(){
        if(firstNode == null)
            return -1;
        T dequeuedData = firstNode.getData();
        if(nodeCounter == 1)
            firstNode = lastNode = null;
        else
            firstNode = firstNode.getNextNode();
        nodeCounter--;
        return dequeuedData;
    }

    public Object dequeueMiddle(){
        int midNodeIndex = nodeCounter/2;
        if (firstNode == null)
            return -1;
        if (nodeCounter == 1) {
            nodeCounter--;
            T dequeuedData = firstNode.getData();
            firstNode = lastNode = null;
            return dequeuedData;
        }
        else if (nodeCounter == 2) {
            nodeCounter--;
            T dequeuedData = firstNode.getData();
            firstNode = lastNode;
            return dequeuedData;
        }
        else if (nodeCounter % 2 == 1) {
            nodeCounter--;
            Node<T> currentNode = firstNode;
            for (int i = 0; i < midNodeIndex - 1; i++)
                currentNode = currentNode.getNextNode();
            T dequeuedData = currentNode.getNextNode().getData();
            currentNode.setNextNode(currentNode.getNextNode().getNextNode());
            return dequeuedData;
        }
        else{
            nodeCounter--;
            Node<T> currentNode = firstNode;
            for (int i = 0; i < midNodeIndex - 2; i++)
                currentNode = currentNode.getNextNode();
            T dequeuedData = currentNode.getNextNode().getData();
            currentNode.setNextNode(currentNode.getNextNode().getNextNode());
            return dequeuedData;
        }
    }

    public Object dequeueBack(){
        if (firstNode == null)
            return -1;
        T dequeuedData = lastNode.getData();
        if(nodeCounter == 1)
            firstNode = lastNode = null;
        else{
            Node<T> currentNode = firstNode;
            while(currentNode.getNextNode() != lastNode)
                currentNode = currentNode.getNextNode();
            currentNode.setNextNode(null);
            lastNode = currentNode;
        }
        nodeCounter--;
        return dequeuedData;
    }

    @Override
    public String toString() {
        StringBuilder queueString = new StringBuilder("[");
        Node<T> currentNode = firstNode;

        if(firstNode == null)
            return queueString.append("]").toString();

        while(true) {
            queueString.append(currentNode.getData());
            if(firstNode == lastNode)
                return queueString.append(']').toString();
            if(currentNode == lastNode)
                return queueString.append(']').toString();
            currentNode = currentNode.getNextNode();
            queueString.append(",");
        }
    }
}
