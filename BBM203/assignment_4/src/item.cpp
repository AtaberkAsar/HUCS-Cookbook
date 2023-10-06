#include "item.h"

Item::Item(std::string category, std::string name, int price)
{
    this->category = category;
    this->name = name;
    this->price = price;
}