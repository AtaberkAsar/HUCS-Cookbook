#include <fstream>
#include <sstream>
#include "item.h"
#include "queue"

#if !defined(_CUSTOM_LLRB_TREE)
#define _CUSTOM_LLRB_TREE

#define RED true
#define BLACK false

class LLRBNode
{
public:
    Item *key;
    LLRBNode *left;
    LLRBNode *right;
    bool color;
    LLRBNode();
};

class LLRBTree
{
private:
    LLRBNode *newNode(std::string name, int price);
    bool color(LLRBNode *node);
    LLRBNode *findInorderSuccessor(LLRBNode *node);
    void swap(LLRBNode *nodeZero, LLRBNode *nodeOne);
    LLRBNode *swapColor(LLRBNode *node);
    LLRBNode *rotateCW(LLRBNode *node);
    LLRBNode *rotateCCW(LLRBNode *node);
    LLRBNode *insert(LLRBNode *node, std::string name, int price);
    LLRBNode *remove(LLRBNode *node, std::string name);

public:
    LLRBNode *root;
    std::string category;
    LLRBTree(std::string category);

    void insert(std::string name, int price);
    void remove(std::string name);
    void printAll(std::ofstream &stream);
    void print(std::ofstream &stream, std::string name);
    void updateData(std::string name, int new_price);
};

#endif // _CUSTOM_LLRB_TREE
