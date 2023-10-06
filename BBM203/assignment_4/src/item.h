#include <iostream>
#include <fstream>
#include <sstream>

#if !defined(_CUSTOM_ITEM)
#define _CUSTOM_ITEM

class Item
{
public:
    std::string category;
    std::string name;
    int price;
    Item(std::string category, std::string name, int price);
};

#endif // _CUSTOM_ITEM
