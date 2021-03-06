#include "Snake.h"
#include <algorithm>


// Inner class (Snake::Node)

Snake::Node::Node(int x, int y, Direction direction)
{
	this->entity = new SnakeHead(x, y, direction);
	this->prev = this->next = NULL;
}

Snake::Node::Node(int x, int y, Node* prev, Node* next)
{
	this->entity = new SnakeEntity(x, y);
	this->prev = prev;
	this->next = next;
}

Snake::Node::~Node()
{
	delete this->entity;
}

// Snake class

Snake::Snake(int headX, int headY, Direction direction, int length)
{
	this->head = new Node(headX, headY, direction);
	this->length = max(length,2);
	
	Node* walker = this->head;
	int deltaX = -DELTA_X_FOR_DIR[direction];
	int deltaY = -DELTA_Y_FOR_DIR[direction];
	for (int i = 1; i < this->length; i++)
	{
		int nextX = walker->entity->getX() + deltaX;
		int nextY = walker->entity->getY() + deltaY;
		walker->next = new Node(nextX, nextY, walker);
		walker = walker->next;
	}
	this->tail = walker;
}

Snake::~Snake()
{
	// Must free up dynamically allocated memory for every single node.
	Node* prev=NULL;
	Node* current=tail;
	for (int i = 0; i < length; ++i) {
       prev=current->prev;
		delete current;
		current=prev;
	}
}

SnakeHead Snake::getHead() const
{
	// Returns a copy of the SnakeHead object contained in the head node.
	// Don't be afraid to use typecasting and the dereference operator!
	// Also, note that this function returns a COPY of the head. 
	// In other words, don't rely on this function if you intend to make changes to the
	// original SnakeHead object.
	SnakeHead* sHead = dynamic_cast<SnakeHead*>(head->entity);
	return *sHead;
}

vector<SnakeEntity> Snake::getBody() const
{
	// TODO: Implement!
	// Creates and returns a vector that contains all of the snake's body segments (not the head).
    vector<SnakeEntity>body;
	Node* next = head->next;
	for (int i = 0; i < length-1 ;++i) {
		body[i] = *next->entity;
		next = next->next;
	}
	return body;
}

SnakeEntity Snake::getTail() const
{
	// TODO: Implement!
	// Returns the SnakeEntity object from the last node (the "tail").
	SnakeEntity* sTail = dynamic_cast<SnakeEntity*>(tail->entity);
	return *sTail;
}

int Snake::getLength() const
{
	// Returns the current length of the snake.
	return length;
}

void Snake::setDirection(Direction direction)
{
	// TODO: Implement!
	// Changes the direction of the snake's head.
	SnakeHead* sHead = dynamic_cast<SnakeHead*>(head->entity);
	sHead->setDirection(direction);
	head->entity = sHead;
}

void Snake::moveHead()
{
	// Moves the snake's head. Nothing else should be done in this function.
	SnakeHead* sHead = dynamic_cast<SnakeHead*>(head->entity);
    sHead->move();
	head->entity = sHead;
}

void Snake::insertNodeAfterHead()
{
	// Creates a new SnakeEntity node and inserts it immediately after the first node (head).
	// This new SnakeEntity should have the same X and Y position as the head node.
	// Make sure that every pointer is updated accordingly.
	// Also, since you're adding a new node, do not forget to change the value of "length".
	Node* nowNode = head;
	Node* nextNode = new Node(head->entity->getX(), head->entity->getY(),head,nowNode->next);
	head->next = nextNode;
	for (int i = 0; i < length-1; ++i) {
		nextNode->next = nowNode->next;
		nextNode->prev = nowNode;
		nextNode = nextNode->next;
	}
	length++;
}

void Snake::removeTail()
{
	// Frees up the dynamically allocated memory for the last node and removes it.
	// Don't forget to update your "tail" pointer and, since you're removing a node,
	// to change the value of "length".
    Node* tTail = tail->prev;
    delete tail;
    tail  = tTail;
}

bool Snake::collidedWithSelf() const {
	// TODO: Implement!
	// This function returns true if the head has collided with any of the other body
	// segments, false otherwise. Don't forget that there is a handy function called
	// "collidedWith" in the SnakeEntity class!
	Node *nNode = head;
	bool collided = false;
	for (int i = 0; i < length - 1 && !collided; ++i) {
		collided= nNode->entity->collidedWith((*nNode->next->entity));
		nNode = nNode->next;
	}
	return collided;
}