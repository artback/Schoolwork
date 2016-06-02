#ifndef SNAKE_ENTITY_H
#define SNAKE_ENTITY_H

class SnakeEntity
{
	protected:
		int x;
		int y;
	public:
		SnakeEntity(int x = 0, int y = 0);
		virtual ~SnakeEntity();
		int getX() const;
		int getY() const;
		bool collidedWith(const SnakeEntity& other) const;
};

#endif