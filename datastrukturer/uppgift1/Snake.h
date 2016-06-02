#ifndef SNAKE_H
#define SNAKE_H

#include <iostream>
#include <vector>
#include "SnakeHead.h"
using namespace std;

class Snake
{
	private:
		class Node
		{
			public:
				SnakeEntity* entity;
				Node* prev;
				Node* next;
				// Creates a SnakeHead node
				Node(int x, int y, Direction direction);
				// Creates a SnakeEntity node
				Node(int x, int y, Node* prev = NULL, Node* next = NULL);
				virtual ~Node();
		};
		// Points to the first node
		Node* head;
		// Points to the last node
		Node* tail;
		int length;
	public:
		Snake(int headX = 1, int headY = 0, Direction direction = RIGHT, int length = 2);
		virtual ~Snake();

		SnakeHead getHead() const;
		vector<SnakeEntity> getBody() const;
		SnakeEntity getTail() const;
		int getLength() const;
		void setDirection(Direction direction);
		void moveHead();
		void insertNodeAfterHead();
		void removeTail();
		bool collidedWithSelf() const;
};

#endif