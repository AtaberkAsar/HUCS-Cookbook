#include <fstream>
#include <sstream>
#include "avl_tree.h"
#include "llrb_tree.h"

#if !defined(_Primary_Tree)
#define _Primary_Tree

class PrimaryNode
{
public:
    std::string category;
    PrimaryNode *left;
    PrimaryNode *right;
    AVLTree *avl_tree;
    LLRBTree *llrb_tree;
    PrimaryNode();
};

class PrimaryTree
{
private:
    PrimaryNode *newNode(std::string category);
    PrimaryNode *insert(PrimaryNode *node, std::string category, std::string name, int price);
    PrimaryNode *findCategory(std::string category);

public:
    PrimaryNode *root;
    PrimaryTree();
    void insert(std::string category, std::string name, int price);
    void remove(std::string category, std::string name);
    void printAllItems(std::ofstream &AVLstream, std::ofstream &LLRBstream);
    void printAllItemsInCategory(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category);
    void printItem(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category, std::string name);
    void find(std::ofstream &AVLstream, std::ofstream &LLRBstream, std::string category, std::string name);
    void updateData(std::string category, std::string name, int new_price);
};

#endif // _Primary_Tree
