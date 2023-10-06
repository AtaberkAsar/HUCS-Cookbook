#include "avl_tree.h"

AVLNode::AVLNode()
{
    this->key = nullptr;
    this->left = nullptr;
    this->right = nullptr;
    this->height = 0;
    this->shorter = true;
}

AVLTree::AVLTree(std::string category)
{
    this->category = category;
    this->root = nullptr;
}

AVLNode *AVLTree::newNode(std::string name, int price)
{
    AVLNode *newNode = new AVLNode();
    newNode->key = new Item(this->category, name, price);
    newNode->height = 1;
    return newNode;
}

int AVLTree::height(AVLNode *node)
{
    if (node == nullptr)
        return 0;
    return node->height;
}

void AVLTree::updateBalance(AVLNode *node)
{
    node->balanceFactor = height(node->left) - height(node->right);
}

AVLNode *AVLTree::findInorderSuccessor(AVLNode *node)
{
    AVLNode *tempNode = node->right;
    while (tempNode->left != nullptr)
        tempNode = tempNode->left;
    return tempNode;
}

void AVLTree::swap(AVLNode *nodeZero, AVLNode *nodeOne)
{
    Item *tempKey = nodeZero->key;
    nodeZero->key = nodeOne->key;
    nodeOne->key = tempKey;
}

AVLNode *AVLTree::rotateCCW(AVLNode *node)
{
    AVLNode *tempNode = node->right;
    node->right = tempNode->left;
    tempNode->left = node;

    // update height
    node->height = std::max(height(node->left), height(node->right)) + 1;
    tempNode->height = std::max(height(tempNode->left), height(tempNode->right)) + 1;

    updateBalance(node);
    updateBalance(tempNode);

    return tempNode;
}

AVLNode *AVLTree::rotateCW(AVLNode *node)
{
    AVLNode *tempNode = node->left;
    node->left = tempNode->right;
    tempNode->right = node;

    // update height
    node->height = std::max(height(node->left), height(node->right)) + 1;
    tempNode->height = std::max(height(tempNode->left), height(tempNode->right)) + 1;

    updateBalance(node);
    updateBalance(tempNode);

    return tempNode;
}

AVLNode *AVLTree::insert(AVLNode *node, std::string name, int price)
{
    // Empty Node found
    if (node == nullptr)
    {
        return newNode(name, price);
    }
    else if (node->key->name.compare(name) > 0)
        node->left = insert(node->left, name, price);
    else
        node->right = insert(node->right, name, price);

    // Update height
    node->height = std::max(height(node->left), height(node->right)) + 1;

    updateBalance(node);

    int balance = height(node->right) - height(node->left);

    // balance to the left
    if (balance > 1 && node->right->key->name.compare(name) < 0)
        return rotateCCW(node);
    if (balance > 1 && node->right->key->name.compare(name) > 0)
    {
        node->right = rotateCW(node->right);
        return rotateCCW(node);
    }

    // balance to the right
    if (balance < -1 && node->left->key->name.compare(name) > 0)
        return rotateCW(node);
    if (balance < -1 && node->left->key->name.compare(name) < 0)
    {
        node->left = rotateCCW(node->left);
        return rotateCW(node);
    }

    return node;
}

AVLNode *AVLTree::remove(AVLNode *node, std::string name)
{
    if (node == nullptr)
        return nullptr; // Name not found
    else if (node->key->name.compare(name) > 0)
        node->left = remove(node->left, name);
    else if (node->key->name.compare(name) < 0)
        node->right = remove(node->right, name);
    else
    {
        // name found

        // leaf node
        if (node->left == nullptr && node->right == nullptr)
        {
            delete node;
            node = nullptr;
            return nullptr;
        }

        // node has one child
        else if (node->left == nullptr)
        {
            AVLNode *tempNode = node->right;
            delete node;
            node = nullptr;
            return tempNode;
        }
        else if (node->right == nullptr)
        {
            AVLNode *tempNode = node->left;
            delete node;
            node = nullptr;
            return tempNode;
        }

        // node has two child
        AVLNode *inorderSuccessor = findInorderSuccessor(node);

        swap(node, inorderSuccessor);

        node->right = remove(node->right, inorderSuccessor->key->name);
    }

    // update height
    node->height = std::max(height(node->left), height(node->right)) + 1;

    updateBalance(node);

    int balance = height(node->right) - height(node->left);

    // balance to the left
    if (balance > 1 && node->right->key->name.compare(name) < 0)
        return rotateCCW(node);
    if (balance > 1 && node->right->key->name.compare(name) > 0)
    {
        node->right = rotateCW(node->right);
        return rotateCCW(node);
    }

    // balance to the right
    if (balance < -1 && node->left->key->name.compare(name) > 0)
        return rotateCW(node);
    if (balance < -1 && node->left->key->name.compare(name) < 0)
    {
        node->left = rotateCCW(node->left);
        return rotateCW(node);
    }

    return node;
}

void AVLTree::insert(std::string name, int price)
{
    this->root = insert(this->root, name, price);
}

void AVLTree::remove(std::string name)
{
    this->root = remove(this->root, name);
}

void AVLTree::printAll(std::ofstream &stream)
{
    stream << '"' << this->category << "\":\n";
    if (this->root == nullptr)
        return;

    std::queue<AVLNode *> printQueue = std::queue<AVLNode *>();
    printQueue.push(this->root);
    while (!printQueue.empty())
    {
        stream << '\t';
        int currentSize = printQueue.size();
        for (int i = 0; i < currentSize; ++i)
        {
            AVLNode *tempNode = printQueue.front();
            printQueue.pop();

            if (tempNode->left != nullptr)
                printQueue.push(tempNode->left);
            if (tempNode->right != nullptr)
                printQueue.push(tempNode->right);

            stream << '"' << tempNode->key->name << "\":\"" << tempNode->key->price << '"';
            if (currentSize > 1 && i < currentSize - 1)
                stream << ',';
        }
        stream << '\n';
    }
}

void AVLTree::print(std::ofstream &stream, std::string name)
{
    AVLNode *tempNode = this->root;
    while (tempNode != nullptr)
    {
        if (tempNode->key->name.compare(name) > 0)
            tempNode = tempNode->left;
        else if (tempNode->key->name.compare(name) < 0)
            tempNode = tempNode->right;
        else
            break;
    }
    if (tempNode != nullptr)
    {
        stream << "{\n";
        stream << '"' << this->category << "\":";
        stream << "\n\t\"" << tempNode->key->name << "\":\"" << tempNode->key->price << "\"\n";
    }
    else
        stream << "{";
}

void AVLTree::updateData(std::string name, int new_price)
{
    AVLNode *tempNode = this->root;
    while (tempNode != nullptr)
    {
        if (tempNode->key->name.compare(name) > 0)
            tempNode = tempNode->left;
        else if (tempNode->key->name.compare(name) < 0)
            tempNode = tempNode->right;
        else
            break;
    }
    if (tempNode != nullptr)
        tempNode->key->price = new_price;
}
