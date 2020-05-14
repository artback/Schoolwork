#include "SnakeHead.h"

SnakeHead::SnakeHead(int x, int y, Direction dir) 
	: SnakeEntity(x, y)
{
	this->dir = dir;
}

SnakeHead::~SnakeHead()
{
	// Intentionally left blank
}

void SnakeHead::setDirection(Direction dir)
{
	// This if condition ensures that the direction cannot be changed from UP (0) to DOWN (3)
	// or vice versa, or from LEFT (1) to RIGHT (2) or vice versa.
	if (this->dir + dir != UP + DOWN)
	{
		this->dir = dir;
	}
}

Direction SnakeHead::getDirection() const {
	return this->dir;
}

void SnakeHead::move()
{
	// Explained in Snake.h.
	this->x += DELTA_X_FOR_DIR[dir];
	this->y += DELTA_Y_FOR_DIR[dir];
}