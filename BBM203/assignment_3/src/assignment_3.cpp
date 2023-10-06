#include "assignment_3.h"

template <typename T>
Node<T>::Node(T data)
{
    this->data = data;
    this->next = nullptr;
}

template <typename T>
Queue<T>::Queue(int size) : _SIZE(size)
{
    this->_front = this->_back = nullptr;
    this->_currentSize = 0;
}

template <typename T>
void Queue<T>::enqueue(T data)
{
    if (this->_currentSize == 0)
    {
        this->_front = this->_back = new Node<T>(data);
        ++this->_currentSize;
    }
    else if (this->_currentSize < this->_SIZE)
    {
        Node<T> *newNode = new Node<T>(data);
        this->_back->next = newNode;
        this->_back = newNode;
        ++this->_currentSize;
    }
    else
        std::cout << "[ERROR] QUEUE OVERFLOW" << std::endl; // Recheck
}

template <typename T>
T Queue<T>::dequeue()
{
    // if (this->_currentSize == 0)
    //     return NULL; // RECHECK
    Node<T> *tempNode = this->_front;
    T temp = tempNode->data;
    this->_front = tempNode->next;
    this->_currentSize--;
    delete tempNode;
    return temp;
}

template <typename T>
T Queue<T>::front()
{
    return this->_front->data;
}

template <typename T>
bool Queue<T>::isEmpty()
{
    return this->_currentSize == 0;
}

template <typename T>
bool Queue<T>::isFull()
{
    return this->_currentSize == this->_SIZE;
}

Order::Order()
{
    this->orderID = -1;
    this->startTime = -1;
    this->orderTime = 0;
    this->brewTime = 0;
    this->price = 0;
    this->endTime = -1;
}

Order::Order(float start, float orderTime, float brewTime, float price, int orderID)
{
    this->orderID = orderID;
    this->startTime = start; // Enter Time
    this->orderTime = orderTime;
    this->brewTime = brewTime;
    this->price = price;
    this->endTime = -1;
}

Employee::Employee()
{
    this->employeeID = -1;
    this->isIdle = true;
    this->currentOrder = nullptr;
    this->busyTime = 0;
}

Employee::Employee(int ID)
{
    this->employeeID = ID;
    this->isIdle = true;
    this->currentOrder = nullptr;
    this->busyTime = 0;
}

Barista::Barista() : Employee()
{
    this->baristaQueue = nullptr;
}

Barista::Barista(int ID) : Employee(ID)
{
    this->baristaQueue = nullptr;
}

Event::Event()
{
    this->eventName = "";
    this->eventName = -1;
    this->order = nullptr;
}

Event::Event(std::string eventName, float eventTime, Order *order)
{
    this->eventName = eventName; // either enter, order, or brew
    this->eventTime = eventTime;
    this->order = order;
}

EventQueue::EventQueue(int eventNum) : Queue(eventNum)
{
}

void EventQueue::enqueue(Event event)
{
    if (this->_front == nullptr)
    {
        Queue::enqueue(event);
        return;
    }

    EventQueue *tempEventQueue = new EventQueue(this->_SIZE);
    while (this->_front != nullptr && this->_front->data.eventTime <= event.eventTime)
        tempEventQueue->Queue::enqueue(this->dequeue());
    tempEventQueue->Queue::enqueue(event);
    while (this->_front != nullptr)
        tempEventQueue->Queue::enqueue(this->dequeue());

    this->_front = tempEventQueue->_front;
    this->_back = tempEventQueue->_back;
    this->_currentSize = tempEventQueue->_currentSize;
}

EventQueue *EventQueue::clone()
{
    EventQueue *tempEventQueue = new EventQueue(this->_SIZE);
    Node<Event> *tempEventNode = this->_front;
    while (tempEventNode != nullptr)
    {
        tempEventQueue->enqueue(tempEventNode->data);
        tempEventNode = tempEventNode->next;
    }
    return tempEventQueue;
}

CashierQueue::CashierQueue(int maxCustomer) : Queue(maxCustomer)
{
    this->maxQueueLength = 0;
}

void CashierQueue::enqueue(Order order)
{
    Queue::enqueue(order);
    if (this->_currentSize > this->maxQueueLength)
        this->maxQueueLength = this->_currentSize;
}

BaristaQueue::BaristaQueue(int maxCustomer) : Queue(maxCustomer)
{
    this->maxQueueLength = 0;
}

void BaristaQueue::enqueue(Order order)
{
    if (this->_front == nullptr)
    {
        Queue::enqueue(order);
        return;
    }

    BaristaQueue *tempBaristaQueue = new BaristaQueue(this->_SIZE);
    while (this->_front != nullptr && this->_front->data.price >= order.price)
        tempBaristaQueue->Queue::enqueue(this->dequeue());
    tempBaristaQueue->Queue::enqueue(order);
    while (this->_front != nullptr)
        tempBaristaQueue->Queue::enqueue(this->dequeue());

    this->_front = tempBaristaQueue->_front;
    this->_back = tempBaristaQueue->_back;
    this->_currentSize = tempBaristaQueue->_currentSize;
    if (this->_currentSize > this->maxQueueLength)
        this->maxQueueLength = this->_currentSize;
}

EventHandler::EventHandler(EventQueue *eventQueue, int cashierNum, int customerNum)
{
    this->currentTime = 0;
    this->eventQueue = eventQueue;
    this->eventQueueCopy = eventQueue->clone();
    this->cashierNum = cashierNum;
    this->baristaNum = cashierNum / 3;
    this->customerNum = customerNum;

    this->cashiers = new Employee *[cashierNum];
    this->baristas = new Barista *[baristaNum];

    for (int i = 0; i < cashierNum; i++)
    {
        Employee *tempCashier = new Employee(i);
        this->cashiers[i] = tempCashier;
    }
    for (int i = 0; i < baristaNum; i++)
    {
        Barista *tempBarista = new Barista(i);
        tempBarista->baristaQueue = new BaristaQueue(customerNum);
        this->baristas[i] = tempBarista;
    }

    this->cashierQueue = new CashierQueue(customerNum);
    this->singularBaristaQueue = new BaristaQueue(customerNum);
}

void EventHandler::model1(std::ostream &stream)
{
    while (!this->eventQueue->isEmpty())
    {
        Event tempEvent = this->eventQueue->dequeue(); // Event queue used as a clock
        this->currentTime = tempEvent.eventTime;
        switch (tempEvent.eventName[0])
        {
        case 'e': // Customer enters
        {
            bool cashierFlag = true; // Flag used to find if any cashier is idle
            for (int i = 0; i < this->cashierNum; i++)
            {
                Employee *tempCashier = cashiers[i];
                if (tempCashier->isIdle)
                {
                    cashierFlag = false;
                    Event orderEvent = Event("order", this->currentTime + tempEvent.order->orderTime, tempEvent.order);
                    eventQueue->enqueue(orderEvent);
                    tempCashier->isIdle = false;
                    tempCashier->busyTime += tempEvent.order->orderTime;
                    tempCashier->currentOrder = tempEvent.order;
                    break; // Idle cashier found
                }
            }
            if (cashierFlag) // Cashiers are full
                this->cashierQueue->enqueue(*tempEvent.order);

            break; // End of enter event
        }

        case 'o': // Customer orders
        {
            for (int i = 0; i < this->cashierNum; i++)
            {
                Employee *tempCashier = cashiers[i]; // Searching for cashier who is busy with the event
                if (tempCashier->currentOrder->orderID == tempEvent.order->orderID)
                {
                    bool baristaFlag = true; // flag used to find if any barista is idle
                    Barista *tempBarista = this->baristas[0];
                    for (int i = 0; i < this->baristaNum; i++)
                    {
                        tempBarista = this->baristas[i];
                        if (tempBarista->isIdle)
                        {
                            baristaFlag = false;
                            break; // Idle barista found
                        }
                    }
                    if (!baristaFlag)
                    {
                        Event brewEvent = Event("brew", this->currentTime + tempEvent.order->brewTime, tempEvent.order);
                        eventQueue->enqueue(brewEvent);

                        tempBarista->isIdle = false;
                        tempBarista->busyTime += tempEvent.order->brewTime;
                        tempBarista->currentOrder = tempEvent.order;
                    }
                    else // Baristas are full
                        this->singularBaristaQueue->enqueue(*tempEvent.order);

                    tempCashier->isIdle = true;

                    if (!this->cashierQueue->isEmpty()) // Dequeue'ing cashier queue
                    {
                        Order tempOrder = this->cashierQueue->dequeue();
                        Order *order = orders[tempOrder.orderID];
                        Event orderEvent = Event("order", this->currentTime + tempOrder.orderTime, order);
                        eventQueue->enqueue(orderEvent);
                        tempCashier->isIdle = false;
                        tempCashier->busyTime += tempOrder.orderTime;
                        tempCashier->currentOrder = order;
                    }

                    break; // End of cashier search
                }
            }
            break; // End of order event
        }

        case 'b': // Coffee brewed
        {
            for (int i = 0; i < this->baristaNum; i++)
            {
                Barista *tempBarista = baristas[i]; // Searching for barista who is busy with the event
                if (tempBarista->currentOrder->orderID == tempEvent.order->orderID)
                {
                    tempBarista->isIdle = true;
                    tempEvent.order->endTime = this->currentTime;

                    if (!this->singularBaristaQueue->isEmpty()) // Dequeue'ing barista queue
                    {
                        Order tempOrder = this->singularBaristaQueue->dequeue();
                        Order *order = orders[tempOrder.orderID];
                        Event brewEvent = Event("brew", this->currentTime + tempOrder.brewTime, order);
                        eventQueue->enqueue(brewEvent);
                        tempBarista->isIdle = false;
                        tempBarista->busyTime += tempOrder.brewTime;
                        tempBarista->currentOrder = order;
                    }
                    break; // End of barista search
                }
            }
            break; // End of brew event
        }
        default: // Default should Not be used
            break;
        }
    } // End of First Model

    stream << std::fixed << std::setprecision(2);
    stream << this->currentTime << std::endl;
    stream << this->cashierQueue->maxQueueLength << std::endl;
    stream << this->singularBaristaQueue->maxQueueLength << std::endl;
    for (int i = 0; i < this->cashierNum; i++)
        stream << cashiers[i]->busyTime / this->currentTime << std::endl;
    for (int i = 0; i < this->baristaNum; i++)
        stream << baristas[i]->busyTime / this->currentTime << std::endl;
    for (int i = 0; i < this->customerNum; i++)
        stream << orders[i]->endTime - orders[i]->startTime << std::endl;
    stream << std::endl;
}

void EventHandler::model2(std::ostream &stream)
{
    // Second Model
    while (!this->eventQueueCopy->isEmpty())
    {
        Event tempEvent = this->eventQueueCopy->dequeue(); // Event queue used as a clock
        this->currentTime = tempEvent.eventTime;
        switch (tempEvent.eventName[0])
        {
        case 'e': // Customer enters
        {

            bool cashierFlag = true; // Flag used to find if any cashier is idle
            for (int i = 0; i < this->cashierNum; i++)
            {
                Employee *tempCashier = cashiers[i];
                if (tempCashier->isIdle)
                {
                    cashierFlag = false;
                    Event orderEvent = Event("order", this->currentTime + tempEvent.order->orderTime, tempEvent.order);
                    eventQueueCopy->enqueue(orderEvent);
                    tempCashier->isIdle = false;
                    tempCashier->busyTime += tempEvent.order->orderTime;
                    tempCashier->currentOrder = tempEvent.order;
                    break; // Idle cashier found
                }
            }
            if (cashierFlag) // Cashiers are full
                this->cashierQueue->enqueue(*tempEvent.order);

            break; // End of enter event
        }

        case 'o': // Customer orders
        {
            for (int i = 0; i < this->cashierNum; i++)
            {
                Employee *tempCashier = cashiers[i]; // Searching for cashier who is busy with the event
                if (tempCashier->currentOrder->orderID == tempEvent.order->orderID)
                {
                    Barista *tempBarista = this->baristas[(int)(tempCashier->employeeID / 3)];
                    if (tempBarista->isIdle)
                    {
                        Event brewEvent = Event("brew", this->currentTime + tempEvent.order->brewTime, tempEvent.order);
                        eventQueueCopy->enqueue(brewEvent);
                        tempBarista->isIdle = false;
                        tempBarista->busyTime += tempEvent.order->brewTime;
                        tempBarista->currentOrder = tempEvent.order;
                    }
                    else // Barista is full
                        tempBarista->baristaQueue->enqueue(*tempEvent.order);

                    tempCashier->isIdle = true;

                    if (!this->cashierQueue->isEmpty()) // Dequeue'ing cashier queue
                    {
                        Order tempOrder = this->cashierQueue->dequeue();
                        Order *order = orders[tempOrder.orderID];
                        Event orderEvent = Event("order", this->currentTime + tempOrder.orderTime, order);
                        eventQueueCopy->enqueue(orderEvent);
                        tempCashier->isIdle = false;
                        tempCashier->busyTime += tempOrder.orderTime;
                        tempCashier->currentOrder = order;
                    }

                    break; // End of cashier search
                }
            }
            break; // End of order event
        }

        case 'b': // Coffee brewed
        {
            for (int i = 0; i < this->baristaNum; i++)
            {
                Barista *tempBarista = baristas[i]; // Searching for barista who is busy with the event
                if (tempBarista->currentOrder->orderID == tempEvent.order->orderID)
                {
                    tempBarista->isIdle = true;
                    tempEvent.order->endTime = this->currentTime;

                    if (!tempBarista->baristaQueue->isEmpty()) // Dequeue'ing barista's queue
                    {
                        Order tempOrder = tempBarista->baristaQueue->dequeue();
                        Order *order = orders[tempOrder.orderID];
                        Event brewEvent = Event("brew", this->currentTime + tempOrder.brewTime, order);
                        eventQueueCopy->enqueue(brewEvent);
                        tempBarista->isIdle = false;
                        tempBarista->busyTime += tempOrder.brewTime;
                        tempBarista->currentOrder = order;
                    }
                    break; // End of barista search
                }
            }
            break; // End of brew event
        }
        default: // Default should Not be used
            break;
        }
    } // End of second model

    stream << this->currentTime << std::endl;
    stream << this->cashierQueue->maxQueueLength << std::endl;
    for (int i = 0; i < this->baristaNum; i++)
        stream << baristas[i]->baristaQueue->maxQueueLength << std::endl;
    for (int i = 0; i < this->cashierNum; i++)
        stream << cashiers[i]->busyTime / this->currentTime << std::endl;
    for (int i = 0; i < this->baristaNum; i++)
        stream << baristas[i]->busyTime / this->currentTime << std::endl;
    for (int i = 0; i < this->customerNum; i++)
        stream << orders[i]->endTime - orders[i]->startTime << std::endl;
}

void EventHandler::restart()
{
    // Restarting cashiers, baristas
    for (int i = 0; i < this->cashierNum; i++)
    {
        cashiers[i]->busyTime = 0;
        cashiers[i]->currentOrder = nullptr;
        cashiers[i]->isIdle = true;
    }
    for (int i = 0; i < this->baristaNum; i++)
    {
        baristas[i]->busyTime = 0;
        baristas[i]->currentOrder = nullptr;
        baristas[i]->isIdle = true;
    }
    for (int i = 0; i < this->customerNum; i++)
    {
        orders[i]->endTime = -1;
    }
}

void EventHandler::handleEvents(std::ostream &outputs)
{
    this->model1(outputs);
    this->restart();
    this->model2(outputs);
}

template class Queue<Event>;
template class Queue<Order>;