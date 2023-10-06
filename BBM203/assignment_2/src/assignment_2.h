#include <iostream>
#include <fstream>
#include <sstream>

class Flat
{
public:
    Flat(int ID, int initial_bandwith);
    int ID;
    int initial_bandwidth;
    int is_empty;
    Flat *nextFlat;
    Flat *prevFlat;
};

class Apartment
{
public:
    Apartment(char name, int max_bandwidth);
    char name;
    int max_bandwidth;
    int remained_bandwidth;
    Flat *flatList;
    Apartment *nextApartment;
    void add_flat(int flat_ID, int initial_bandwidth, int index);
    void remove_flats(int flats_to_remove, bool delete_all_flag = false);
    void make_flat_empty(int flat_ID);
    Flat *search_flat(int flat_ID);
};

class Street
{
public:
    Street();
    Apartment *head;
    Apartment *tail;
    void add_apartment(char name, int max_bandwidth, std::string position);
    Street *remove_apartment(char name);
    Apartment *searchApartment(char name);
    int find_sum_of_max_bandwidths(std::ostream &stream);
    Street *merge_two_apartments(char apartment1, char apartment2);
    void relocate_flats_to_same_apartment(char apartment, int shift_ID, int flats[], int flat_num);
    void list_apartments(std::ostream &stream);
};