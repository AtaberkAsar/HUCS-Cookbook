#include "llrb_tree.h"

LLRBNode::LLRBNode()
{
    this->key = nullptr;
    this->left = nullptr;
    this->right = nullptr;
    this->color = RED;
}

LLRBTree::LLRBTree(std::string category)
{
    this->category = category;
    this->root = nullptr;
}

LLRBNode *LLRBTree::newNode(std::string name, int price)
{
    LLRBNode *newNode = new LLRBNode();
    newNode->key = new Item(this->category, name, price);
    return newNode;
}

bool LLRBTree::color(LLRBNode *node)
{
    if (node == nullptr)
        return BLACK;
    return node->color;
}

LLRBNode *LLRBTree::findInorderSuccessor(LLRBNode *node)
{
    LLRBNode *tempNode = node->right;
    while (tempNode->left != nullptr)
        tempNode = tempNode->left;
    return tempNode;
}

void LLRBTree::swap(LLRBNode *nodeZero, LLRBNode *nodeOne)
{
    Item *tempKey = nodeZero->key;
    nodeZero->key = nodeOne->key;
    nodeOne->key = tempKey;
}

LLRBNode *LLRBTree::swapColor(LLRBNode *node)
{
    node->left->color = BLACK;
    node->right->color = BLACK;
    node->color = !(node->color);

    return node;
}

LLRBNode *LLRBTree::rotateCCW(LLRBNode *node)
{
    LLRBNode *tempNode = node->right;
    node->right = tempNode->left;
    tempNode->left = node;

    bool color = node->color;
    node->color = tempNode->color;
    tempNode->color = color;

    return tempNode;
}

LLRBNode *LLRBTree::rotateCW(LLRBNode *node)
{
    LLRBNode *tempNode = node->left;
    node->left = tempNode->right;
    tempNode->right = node;

    bool color = node->color;
    node->color = tempNode->color;
    tempNode->color = color;

    return tempNode;
}

LLRBNode *LLRBTree::insert(LLRBNode *node, std::string name, int price)
{
    // Empty node found
    if (node == nullptr)
        return newNode(name, price);
    if (node->key->name.compare(name) > 0)
        node->left = insert(node->left, name, price);
    else
        node->right = insert(node->right, name, price);

    // left and right red child
    if (color(node->left) && color(node->right))
        return swapColor(node);

    // right red child
    if (color(node->right))
        return rotateCCW(node);

    // left and next left red child
    if (color(node->left) && color(node->left->left))
    {
        node = rotateCW(node);
        return swapColor(node);
    }

    return node;
}

LLRBNode *LLRBTree::remove(LLRBNode *node, std::string name)
{
    // node not found
    if (node == nullptr)
        return nullptr;
    if (node->key->name.compare(name) > 0)
        node->left = remove(node->left, name);
    else if (node->key->name.compare(name) < 0)
        node->right = remove(node->right, name);
    else
    {
        // node found

        // no child
        if (node->left == nullptr && node->right == nullptr)
        {
            delete node;
            node = nullptr;
            return nullptr;
        }

        // only one child
        else if (node->left == nullptr)
        {
            LLRBNode *tempNode = node->right;
            tempNode->color = node->color;
            delete node;
            node = nullptr;
            return tempNode;
        }
        else if (node->right == nullptr)
        {
            LLRBNode *tempNode = node->left;
            tempNode->color = node->color;
            delete node;
            node = nullptr;
            return tempNode;
        }

        // has two child
        LLRBNode *inorderSuccessor = findInorderSuccessor(node);

        swap(node, inorderSuccessor);

        node->right = remove(node->right, inorderSuccessor->key->name);
    }

    // left and right red child
    if (color(node->left) && color(node->right))
        return swapColor(node);

    // right red child
    if (color(node->right))
        return rotateCCW(node);

    // left and next left red child
    if (color(node->left) && color(node->left->left))
    {
        node = rotateCW(node);
        return swapColor(node);
    }

    return node;
}

void LLRBTree::insert(std::string name, int price)
{
    this->root = insert(this->root, name, price);
}

void LLRBTree::remove(std::string name)
{
    this->root = remove(this->root, name);
}

void LLRBTree::printAll(std::ofstream &stream)
{
    stream << '"' << this->category << "\":\n";
    if (this->root == nullptr)
        return;

    std::queue<LLRBNode *> printQueue = std::queue<LLRBNode *>();
    printQueue.push(this->root);
    while (!printQueue.empty())
    {
        stream << '\t';
        int currentSize = printQueue.size();
        for (int i = 0; i < currentSize; ++i)
        {
            LLRBNode *tempNode = printQueue.front();
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

void LLRBTree::print(std::ofstream &stream, std::string name)
{
    LLRBNode *tempNode = this->root;
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

void LLRBTree::updateData(std::string name, int new_price)
{
    LLRBNode *tempNode = this->root;
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
