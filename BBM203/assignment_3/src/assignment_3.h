#include <iostream>
#include <iomanip>

#if !defined(_ASSIGNMENT_3)
#define _ASSIGNMENT_3

template <typename T>
class Node
{
public:
    T data;
    Node<T> *next;
    Node(T data);
};

template <typename T>
class Queue
{
protected:
    const int _SIZE;
    int _currentSize;
    Node<T> *_front;
    Node<T> *_back;

public:
    Queue(int size);
    void enqueue(T data);
    T dequeue();
    T front();
    bool isEmpty();
    bool isFull();
};

class Order
{
public:
    int orderID;
    float startTime;
    float orderTime;
    float brewTime;
    float price;
    float endTime;
    Order();
    Order(float start, float orderTime, float brewTime, float price, int orderID);
};

class Employee
{
public:
    int employeeID;
    bool isIdle;
    Order *currentOrder;
    float busyTime;
    Employee();
    Employee(int ID);
};

class Event
{
public:
    std::string eventName;
    float eventTime;
    Order *order;
    Event();
    Event(std::string eventName, float eventTime, Order *order);
};

class EventQueue : public Queue<Event>
{
public:
    EventQueue(int eventNum);
    void enqueue(Event event);
    EventQueue *clone();
    // Event *dequeue();
};

class CashierQueue : public Queue<Order>
{
public:
    int maxQueueLength;
    CashierQueue(int maxCustomer);
    void enqueue(Order order);
};

class BaristaQueue : public Queue<Order>
{
public:
    int maxQueueLength;
    BaristaQueue(int maxCustomer);
    void enqueue(Order order);
};

class Barista : public Employee
{
public:
    BaristaQueue *baristaQueue;
    Barista();
    Barista(int ID);
};

class EventHandler
{
private:
    float currentTime;
    int cashierNum;
    int baristaNum;
    int customerNum;
    Employee **cashiers;
    Barista **baristas;
    EventQueue *eventQueue;
    EventQueue *eventQueueCopy;
    CashierQueue *cashierQueue;
    BaristaQueue *singularBaristaQueue;
    void model1(std::ostream &stream);
    void model2(std::ostream &stream);
    void restart();

public:
    Order **orders;
    EventHandler(EventQueue *eventQueue, int cashierNum, int customerNum);
    void handleEvents(std::ostream &outputs);
};

#endif
