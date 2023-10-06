#include "assignment_2.h"
#include <cmath>

Flat::Flat(int ID, int initial_bandwidth)
{
    this->ID = ID;
    this->initial_bandwidth = initial_bandwidth;
    this->is_empty = initial_bandwidth == 0 ? 1 : 0;
    this->nextFlat = nullptr;
    this->prevFlat = nullptr;
}

Apartment::Apartment(char name, int max_bandwidth)
{
    this->name = name;
    this->max_bandwidth = max_bandwidth;
    this->remained_bandwidth = max_bandwidth;
    this->flatList = nullptr;
    this->nextApartment = nullptr;
}

void Apartment::add_flat(int flatID, int initial_bandwidth, int index)
{
    initial_bandwidth = initial_bandwidth <= this->remained_bandwidth ? initial_bandwidth : this->remained_bandwidth;
    this->remained_bandwidth -= initial_bandwidth;
    Flat *newFlat = new Flat(flatID, initial_bandwidth);
    Flat *tempFlat = this->flatList;
    switch (index)
    {
    case 0:
        newFlat->nextFlat = tempFlat;
        if (tempFlat != nullptr) // flat_list empty
            tempFlat->prevFlat = newFlat;
        this->flatList = newFlat;
        break;
    default:
        for (int i = 0; i < index - 1; i++)
        {
            tempFlat = tempFlat->nextFlat;
        }
        newFlat->nextFlat = tempFlat->nextFlat;
        if (tempFlat->nextFlat != nullptr) // Insertion to end
            tempFlat->nextFlat->prevFlat = newFlat;
        tempFlat->nextFlat = newFlat;
        newFlat->prevFlat = tempFlat;
        break;
    }
}

void Apartment::remove_flats(int flat_to_remove, bool delete_all_flag)
{
    if (delete_all_flag)
    {
        while (this->flatList != nullptr)
        {
            Flat *flatToDel = this->flatList;
            this->remained_bandwidth += flatToDel->initial_bandwidth;

            this->flatList = flatToDel->nextFlat;
            if (flatToDel->nextFlat != nullptr)
            {
                flatToDel->nextFlat->prevFlat = flatToDel->prevFlat;
                flatToDel->nextFlat = nullptr;
            }
            delete flatToDel;
        }
    }
    else
    {
        Flat *flatToDel = this->search_flat(flat_to_remove);
        if (flatToDel == nullptr || this->flatList == nullptr)
            return;
        if (flatToDel == this->flatList)
            flatList = flatList->nextFlat;
        if (flatToDel->nextFlat != nullptr)
            flatToDel->nextFlat->prevFlat = flatToDel->prevFlat;
        if (flatToDel->prevFlat != nullptr)
            flatToDel->prevFlat->nextFlat = flatToDel->nextFlat;
        delete flatToDel;
    }
}

void Apartment::make_flat_empty(int flat_ID)
{
    Flat *tempFlat = this->flatList;
    while (tempFlat->ID != flat_ID)
        tempFlat = tempFlat->nextFlat;
    this->remained_bandwidth += tempFlat->initial_bandwidth;
    tempFlat->initial_bandwidth = 0;
    tempFlat->is_empty = 1;
}

Flat *Apartment::search_flat(int flat_ID)
{
    Flat *tempFlat = this->flatList;
    while (tempFlat != nullptr && tempFlat->ID != flat_ID)
        tempFlat = tempFlat->nextFlat;
    return tempFlat;
}

Street::Street()
{
    this->head = nullptr;
    this->tail = nullptr;
}

void Street::add_apartment(char name, int max_bandwidth, std::string position)
{
    Apartment *newApartment = new Apartment(name, max_bandwidth);
    Apartment *tempApartment = this->head;
    if (this->head == nullptr) // Street is empty
    {
        this->head = this->tail = newApartment;
        newApartment->nextApartment = newApartment;
    }
    else if (this->head == this->tail) // Street has 1 Apartment
    {
        switch (position[0])
        {
        case 'a': // after
            tempApartment->nextApartment = newApartment;
            this->tail = newApartment;
            newApartment->nextApartment = tempApartment;
            break;
        case 'b': // before
            newApartment->nextApartment = tempApartment;
            this->head = newApartment;
            tempApartment->nextApartment = newApartment;
            break;
        default: // head
            newApartment->nextApartment = tempApartment;
            this->head = newApartment;
            tempApartment->nextApartment = newApartment;
            break;
        }
    }
    else // Street has more than 1 Apartment
    {
        switch (position[0])
        {
        case 'h': // head
            newApartment->nextApartment = tempApartment;
            while (tempApartment->nextApartment != this->head)
                tempApartment = tempApartment->nextApartment;
            tempApartment->nextApartment = newApartment;
            this->head = newApartment;
            break;

        case 'a': // after
            while (tempApartment->name != position[6])
            {
                tempApartment = tempApartment->nextApartment;
            }
            newApartment->nextApartment = tempApartment->nextApartment;
            tempApartment->nextApartment = newApartment;
            if (this->tail == tempApartment)
                this->tail = newApartment;
            break;

        default:                                    // before
            if (tempApartment->name == position[7]) // Insert before head
            {
                newApartment->nextApartment = tempApartment;

                while (tempApartment->nextApartment != this->head)
                    tempApartment = tempApartment->nextApartment;
                tempApartment->nextApartment = newApartment;
                this->head = newApartment;
            }
            else
            {
                while (tempApartment->nextApartment->name != position[7])
                {
                    tempApartment = tempApartment->nextApartment;
                }
                newApartment->nextApartment = tempApartment->nextApartment;
                tempApartment->nextApartment = newApartment;
            }
            break;
        }
    }
}

Street *Street::remove_apartment(char s)
{
    Apartment *tempApartment = this->head;
    if (tempApartment == nullptr) // if Apartment is empty
        return NULL;
    while (tempApartment->nextApartment != this->head && tempApartment->nextApartment->name != s)
        tempApartment = tempApartment->nextApartment;
    Apartment *apartmentToDel = tempApartment->nextApartment;
    tempApartment->nextApartment = apartmentToDel->nextApartment;

    apartmentToDel->remove_flats(-1, true);
    apartmentToDel->nextApartment = nullptr;
    if (this->head == apartmentToDel) // check if first apartment deleted
        this->head = this->tail;
    if (this->tail == apartmentToDel)
        this->tail = tempApartment;

    delete apartmentToDel;
    return this;
}

Apartment *Street::searchApartment(char name)
{
    Apartment *tempApartment = this->head;
    while (tempApartment->name != name)
        tempApartment = tempApartment->nextApartment;
    return tempApartment;
}

int Street::find_sum_of_max_bandwidths(std::ostream &stream)
{
    Apartment *tempApartment = this->head;
    int sumOfBandwiths = 0;
    while (tempApartment->nextApartment != this->head)
    {
        sumOfBandwiths += tempApartment->max_bandwidth;
        tempApartment = tempApartment->nextApartment;
    }
    sumOfBandwiths += tempApartment->max_bandwidth;
    stream << "sum of bandwidth: " << sumOfBandwiths << std::endl
           << std::endl;
    return sumOfBandwiths;
}

Street *Street::merge_two_apartments(char apartment1, char apartment2)
{
    Apartment *apartmentMergeTo = this->searchApartment(apartment1);
    Apartment *apartmentMergeFrom = this->searchApartment(apartment2);

    if (apartmentMergeTo->flatList == nullptr) // if apartment1 is empty
    {
        apartmentMergeTo->max_bandwidth += apartmentMergeFrom->max_bandwidth;
        apartmentMergeTo->remained_bandwidth += apartmentMergeFrom->remained_bandwidth;
        apartmentMergeTo->flatList = apartmentMergeFrom->flatList;
        apartmentMergeFrom->flatList = nullptr;
        return this->remove_apartment(apartment2);
    }

    Flat *tempFlat = apartmentMergeTo->flatList;
    while (tempFlat->nextFlat != nullptr)
        tempFlat = tempFlat->nextFlat;

    apartmentMergeTo->max_bandwidth += apartmentMergeFrom->max_bandwidth;
    apartmentMergeTo->remained_bandwidth += apartmentMergeFrom->remained_bandwidth;

    tempFlat->nextFlat = apartmentMergeFrom->flatList;
    if (apartmentMergeFrom->flatList != nullptr)
        apartmentMergeFrom->flatList->prevFlat = tempFlat;
    apartmentMergeFrom->flatList = nullptr;
    return this->remove_apartment(apartment2);
}

void Street::relocate_flats_to_same_apartment(char apartment, int shift_ID, int flats[], int flat_num)
{
    Apartment *tempApartment = this->head;
    Apartment *apartmentToAdd = this->searchApartment(apartment);

    int found = 0;

    Flat **flat_addresses = new Flat *[flat_num];
    do
    {
        if (found == flat_num)
            break;

        for (int i = 0; i < flat_num; i++)
        {
            Flat *flat = tempApartment->search_flat(flats[i]);
            if (flat != nullptr)
            {
                flat_addresses[i] = flat;
                found++;
                if (tempApartment->flatList == flat)
                    tempApartment->flatList = flat->nextFlat;
                if (flat->nextFlat != nullptr)
                    flat->nextFlat->prevFlat = flat->prevFlat;
                if (flat->prevFlat != nullptr)
                    flat->prevFlat->nextFlat = flat->nextFlat;
                flat->prevFlat = nullptr;
                flat->nextFlat = nullptr;
                tempApartment->max_bandwidth -= flat->initial_bandwidth;
                apartmentToAdd->max_bandwidth += flat->initial_bandwidth;
            }
        }
        tempApartment = tempApartment->nextApartment;
    } while (tempApartment != this->head);

    Flat *shiftFlat = apartmentToAdd->search_flat(shift_ID);
    for (int i = 0; i < flat_num; i++)
    {
        Flat *flat = flat_addresses[i];
        if (shiftFlat->prevFlat != nullptr)
            shiftFlat->prevFlat->nextFlat = flat;
        else
            apartmentToAdd->flatList = flat;
        flat->prevFlat = shiftFlat->prevFlat;
        flat->nextFlat = shiftFlat;
        shiftFlat->prevFlat = flat;
    }
}

void Street::list_apartments(std::ostream &stream)
{
    Apartment *tempApartment = this->head;
    while (tempApartment->nextApartment != this->head)
    {
        stream << tempApartment->name << '(' << tempApartment->max_bandwidth << ')';
        Flat *tempFlat = tempApartment->flatList;
        while (tempFlat != nullptr)
        {
            stream << '\t' << "Flat" << tempFlat->ID << '(' << tempFlat->initial_bandwidth << ')';
            tempFlat = tempFlat->nextFlat;
        }
        tempApartment = tempApartment->nextApartment;
        stream << std::endl;
    }
    stream << tempApartment->name << '(' << tempApartment->max_bandwidth << ')';
    Flat *tempFlat = tempApartment->flatList;
    while (tempFlat != nullptr)
    {
        stream << '\t' << "Flat" << tempFlat->ID << '(' << tempFlat->initial_bandwidth << ')';
        tempFlat = tempFlat->nextFlat;
    }
    stream << std::endl
           << std::endl;
}