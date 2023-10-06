#include "primary_tree.h"

PrimaryNode::PrimaryNode()
{
    this->left = nullptr;
    this->right = nullptr;
    this->avl_tree = nullptr;
    this->llrb_tree = nullptr;
}

PrimaryTree::PrimaryTree()
{
    this->root = nullptr;
}

PrimaryNode *PrimaryTree::newNode(std::string category)
{
    PrimaryNode *newNode = new PrimaryNode();
    newNode->category = category;
    return newNode;
}

PrimaryNode *PrimaryTree::insert(PrimaryNode *node, std::string category, std::string name, int price)
{
    // category not exist
    if (node == nullptr)
    {
        PrimaryNode *tempNode = newNode(category);
        tempNode->avl_tree = new AVLTree(category);
        tempNode->llrb_tree = new LLRBTree(category);
        tempNode->avl_tree->insert(name, price);
        tempNode->llrb_tree->insert(name, price);
        return tempNode;
    }
    else if (node->category.compare(category) > 0)
        node->left = insert(node->left, category, name, price);
    else if (node->category.compare(category) < 0)
        node->right = insert(node->right, category, name, price);

    // category exists
    else
    {
        node->avl_tree->insert(name, price);
        node->llrb_tree->insert(name, price);
        return node;
    }

    return node;
}

PrimaryNode *PrimaryTree::findCategory(std::string category)
{
    PrimaryNode *tempNode = this->root;

    while (tempNode != nullptr)
    {
        if (tempNode->category.compare(category) > 0)
            tempNode = tempNode->left;
        else if (tempNode->category.compare(category) < 0)
            tempNode = tempNode->right;
        else
            break;
    }

    return tempNode;
}

void PrimaryTree::insert(std::string category, std::string name, int price)
{
    this->root = insert(this->root, category, name, price);
}

void PrimaryTree::remove(std::string category, std::string name)
{
    PrimaryNode *tempNode = findCategory(category);

    if (tempNode != nullptr)
    {
        tempNode->avl_tree->remove(name);
        tempNode->llrb_tree->remove(name);
    }
}

void PrimaryTree::printAllItems(std::ofstream &AVLstream, std::ofstream &LLRBstream)
{
    AVLstream << "command:printAllItems" << std::endl;
    LLRBstream << "command:printAllItems" << std::endl;

    PrimaryNode *tempNode = this->root;
    if (tempNode == nullptr)
    {
        AVLstream << "{}\n";
        LLRBstream << "{}\n";
        return;
    }

    AVLstream << "{" << std::endl;
    LLRBstream << '{' << std::endl;

    std::queue<PrimaryNode *> nodeQueue = std::queue<PrimaryNode *>();
    nodeQueue.push(tempNode);

    while (!nodeQueue.empty())
    {
        tempNode = nodeQueue.front();
        nodeQueue.pop();

        if (tempNode->left != nullptr)
            nodeQueue.push(tempNode->left);
        if (tempNode->right != nullptr)
            nodeQueue.push(tempNode->right);

        if (tempNode->avl_tree->root == nullptr)
        {
            AVLstream << '"' << tempNode->category << "\":{}\n";
            LLRBstream << '"' << tempNode->category << "\":{}\n";
        }
        else
        {
            tempNode->avl_tree->printAll(AVLstream);
            tempNode->llrb_tree->printAll(LLRBstream);
        }
    }

    AVLstream << '}' << std::endl;
    LLRBstream << '}' << std::endl;
}

void PrimaryTree::printAllItemsInCategory(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category)
{
    AVLstream << "command:printAllItemsInCategory\t" << category << std::endl;
    LLRBstream << "command:printAllItemsInCategory\t" << category << std::endl;

    PrimaryNode *tempNode = this->root;

    while (tempNode != nullptr)
    {
        if (tempNode->category.compare(category) > 0)
            tempNode = tempNode->left;
        else if (tempNode->category.compare(category) < 0)
            tempNode = tempNode->right;
        else
            break;
    }

    if (tempNode == nullptr)
    {
        AVLstream << "{}\n";
        LLRBstream << "{}\n";
        return;
    }

    AVLstream << '{' << std::endl;
    LLRBstream << '{' << std::endl;

    if (tempNode->avl_tree->root == nullptr)
    {
        AVLstream << '"' << tempNode->category << "\":{}\n";
        LLRBstream << '"' << tempNode->category << "\":{}\n";
    }
    else
    {
        tempNode->avl_tree->printAll(AVLstream);
        tempNode->llrb_tree->printAll(LLRBstream);
    }

    AVLstream << '}' << std::endl;
    LLRBstream << '}' << std::endl;
}

void PrimaryTree::printItem(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category, std::string name)
{
    AVLstream << "command:printItem\t" << category << '\t' << name << std::endl;
    LLRBstream << "command:printItem\t" << category << '\t' << name << std::endl;

    PrimaryNode *tempNode = this->root;

    while (tempNode != nullptr)
    {
        if (tempNode->category.compare(category) > 0)
            tempNode = tempNode->left;
        else if (tempNode->category.compare(category) < 0)
            tempNode = tempNode->right;
        else
            break;
    }

    if (tempNode == nullptr)
    {
        AVLstream << "{}\n";
        LLRBstream << "{}\n";
        return;
    }

    if (tempNode->avl_tree->root == nullptr)
    {
        AVLstream << '"' << tempNode->category << "\":{}\n";
        LLRBstream << '"' << tempNode->category << "\":{}\n";
    }
    else
    {
        tempNode->avl_tree->print(AVLstream, name);
        tempNode->llrb_tree->print(LLRBstream, name);
    }

    AVLstream << '}' << std::endl;
    LLRBstream << '}' << std::endl;
}

void PrimaryTree::find(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category, std::string name)
{
    AVLstream << "command:find\t" << category << '\t' << name << std::endl;
    LLRBstream << "command:find\t" << category << '\t' << name << std::endl;

    PrimaryNode *tempNode = this->root;

    while (tempNode != nullptr)
    {
        if (tempNode->category.compare(category) > 0)
            tempNode = tempNode->left;
        else if (tempNode->category.compare(category) < 0)
            tempNode = tempNode->right;
        else
            break;
    }

    if (tempNode == nullptr)
    {
        AVLstream << "{}\n";
        LLRBstream << "{}\n";
        return;
    }

    if (tempNode->avl_tree->root == nullptr)
    {
        AVLstream << '"' << tempNode->category << "\":{}\n";
        LLRBstream << '"' << tempNode->category << "\":{}\n";
    }
    else
    {
        tempNode->avl_tree->print(AVLstream, name);
        tempNode->llrb_tree->print(LLRBstream, name);
    }

    AVLstream << '}' << std::endl;
    LLRBstream << '}' << std::endl;
}

void PrimaryTree::updateData(std::string category, std::string name, int new_price)
{
    PrimaryNode *tempNode = findCategory(category);

    if (tempNode != nullptr)
    {
        tempNode->avl_tree->updateData(name, new_price);
        tempNode->llrb_tree->updateData(name, new_price);
    }
}
