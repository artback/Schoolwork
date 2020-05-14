#include "SnakeEntity.h"

SnakeEntity::SnakeEntity(int x, int y)
{
	this->x = x;
	this->y = y;
}

SnakeEntity::~SnakeEntity()
{
	// Intentionally left blank
}

int SnakeEntity::getX() const
{
	return this->x;
}

int SnakeEntity::getY() const
{
	return this->y;
}

// Returns true if the two entities have collided, false otherwise
bool SnakeEntity::collidedWith(const SnakeEntity& other) const
{
	return this->x == other.x && this->y == other.y;
}