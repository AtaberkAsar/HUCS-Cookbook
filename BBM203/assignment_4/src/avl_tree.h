#include <fstream>
#include <sstream>
#include "item.h"
#include "queue"

#if !defined(_CUSTOM_AVL_TREE)
#define _CUSTOM_AVL_TREE

class AVLNode
{
public:
    Item *key;
    AVLNode *left;
    AVLNode *right;
    int height;
    int balanceFactor;
    bool shorter;
    AVLNode();
};

class AVLTree
{
private:
    AVLNode *newNode(std::string name, int price);
    int height(AVLNode *node);
    void updateBalance(AVLNode *node);
    AVLNode *findInorderSuccessor(AVLNode *node);
    void swap(AVLNode *nodeZero, AVLNode *nodeOne);
    AVLNode *rotateCW(AVLNode *node);
    AVLNode *rotateCCW(AVLNode *node);
    AVLNode *insert(AVLNode *node, std::string name, int price);
    AVLNode *remove(AVLNode *node, std::string name);

public:
    AVLNode *root;
    std::string category;
    AVLTree(std::string category);

    void insert(std::string name, int price);
    void remove(std::string name);
    void printAll(std::ofstream &stream);
    void print(std::ofstream &stream, std::string name);
    void updateData(std::string name, int new_price);
};

#endif // _CUSTOM_AVL_TREE
