#ifndef SNAKE_HEAD_H
#define SNAKE_HEAD_H

#include "SnakeEntity.h"

enum Direction
{
	UP,
	LEFT,
	RIGHT,
	DOWN
};

// These arrays are used to determine the X and Y movement based on the current direction.
// The current direction is between 0 (UP) and 3 (DOWN) and can be used as an array index.
const short DELTA_X_FOR_DIR[] = {0, -1, 1, 0};
const short DELTA_Y_FOR_DIR[] = {-1, 0, 0, 1};

class SnakeHead : public SnakeEntity
{
	private:
		Direction dir;
	public:
		SnakeHead(int x = 0, int y = 0, Direction dir = RIGHT);
		virtual ~SnakeHead();
		void setDirection(Direction dir);
		Direction getDirection() const;
		void move();
};

#endif