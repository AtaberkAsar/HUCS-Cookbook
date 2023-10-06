#include <iostream>
#include "input_handler.h"

int main(int args, char *argv[])
{
    EventQueue *eventQueue;
    int cashierNumber;
    int customerNumber;
    InputHandler::fileHandler(argv[1], argv[2], eventQueue);
    return 0;
}