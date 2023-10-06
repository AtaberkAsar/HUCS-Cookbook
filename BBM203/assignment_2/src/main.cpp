#include "input_handler.h"

int main(int argc, char *argv[])
{
    Street *street = new Street();
    InputHandler::fileHandler(argv[1], argv[2], street);

    return 0;
}