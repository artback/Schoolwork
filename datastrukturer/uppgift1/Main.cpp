#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
#include <windows.h>
#include "Snake.h"

#define ARRAY_SIZE(arr) (sizeof(arr) / sizeof(arr[0]))

using namespace std;

// Size of the game area
const int GRID_HEIGHT = 25;
const int GRID_WIDTH = 40;
// Frames per second
const int FPS = 10;
const int SLEEP_TIME = 1000 / FPS;
const int POINTS_PER_APPLE = 100;

// Visual representations of the different entities
const char HEAD_CHAR  = 'O';
const char BODY_CHAR  = '\xFE';
const char APPLE_CHAR = '@';

// Used for positioning of the cursor in the console
const HANDLE STD_OUT_HANDLE = (HANDLE) GetStdHandle(STD_OUTPUT_HANDLE);

// High score
int readHighScoreFromFile();
void writeHighScoreToFile(int highScore);
// Apple randomization
SnakeEntity getNewRandomizedApple();
// User input
void handleUserInput(Snake& snake);
void clearAsyncKeyStates();
// Screen output
void setCursorPosition(SnakeEntity entity);
void setCursorPosition(int x, int y);
void drawBorders();
void drawEntireSnake(const Snake& snake);
void drawUpdatedGrid(const Snake& snake, const SnakeEntity& apple);
void clearTail(const SnakeEntity& tail);
void drawScore(int score);
void drawHighScore(int highScore);
// Game over
bool handleGameOver(int score, int& highScore);
bool userWantsToTryAgain();

int main()
{
	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
	system("color 0A");

	int highScore = readHighScoreFromFile();

	bool gameRunning = true;
	while (gameRunning)
	{
		// Setup
		Snake snake;
		SnakeEntity apple = getNewRandomizedApple();
		int score = 0;

		// Screen output
		drawBorders();
		drawEntireSnake(snake);
		drawScore(score);
		drawHighScore(highScore);
		clearAsyncKeyStates();

		bool snakeAlive = true;
		while (snakeAlive)
		{	
			Sleep(SLEEP_TIME);

			handleUserInput(snake);

			// Snake movement
			snake.insertNodeAfterHead();
			snake.moveHead();
			SnakeHead head = snake.getHead();
			SnakeEntity tail = snake.getTail();
			if (!head.collidedWith(apple))
			{
				snake.removeTail();
			}
			else
			{
				score += POINTS_PER_APPLE;
				drawScore(score);
				apple = getNewRandomizedApple();
			}
			// Collision detection
			snakeAlive = !snake.collidedWithSelf() && head.getX() >= 0 && head.getY() >= 0 && head.getX() < GRID_WIDTH && head.getY() < GRID_HEIGHT;
			if (snakeAlive)
			{
				clearTail(tail);
				drawUpdatedGrid(snake, apple);
			}
		}
		gameRunning = handleGameOver(score, highScore);
	}

	cout << endl << endl;	
	return EXIT_SUCCESS;
}

int readHighScoreFromFile()
{
	int highScore = 0;
	fstream in("highscore.dat", ios::binary | ios::in);
	if (in.good())
	{
		in.read((char*) &highScore, sizeof(highScore));
	}
	else
	{
		// Creates file if it didn't already exist (high score is set to 0)
		ofstream out("highscore.dat", ios::binary | ios::out);
		out.write((char*) &highScore, sizeof(highScore));
		out.close();
	}
	in.close();
	return highScore;
}

void writeHighScoreToFile(int highScore)
{
	ofstream out("highscore.dat", ios::binary | ios::out);
	out.write((char*) &highScore, sizeof(highScore));
	out.close();
}

SnakeEntity getNewRandomizedApple()
{
	return SnakeEntity(rand() % GRID_WIDTH, rand() % GRID_HEIGHT);
}

// Changes the direction of the snake depending on the user input
void handleUserInput(Snake& snake)
{
	const static char KEYS[] = {VK_UP, VK_LEFT, VK_RIGHT, VK_DOWN};
	const static Direction DIRECTIONS[] = {UP, LEFT, RIGHT, DOWN};
	int i = 0;
	bool actionTaken = false;
	while (!actionTaken && i < ARRAY_SIZE(KEYS))
	{
		if (GetAsyncKeyState(KEYS[i]))
		{
			snake.setDirection(DIRECTIONS[i]);
			actionTaken = true;
		}
		i++;
	}
}

void clearAsyncKeyStates()
{
	const static char KEYS_TO_CLEAR[] = {VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, 'Y', 'N'};
	for (int i = 0; i < ARRAY_SIZE(KEYS_TO_CLEAR); i++)
	{
		GetAsyncKeyState(KEYS_TO_CLEAR[i]);
	}
}

// Moves the console cursor to the position of a certain entity
void setCursorPosition(SnakeEntity entity)
{
	setCursorPosition(entity.getX() + 1, entity.getY() + 1);
}

void setCursorPosition(int x, int y)
{
	COORD coord = {x, y};
	SetConsoleCursorPosition(STD_OUT_HANDLE, coord);
}

void drawBorders()
{
	system("cls");
	ostringstream stream;
	stream << "\xC9" << setw(GRID_WIDTH) << setfill('\xCD') << "" << "\xBB\n";
	for (int i = 0; i < GRID_HEIGHT; i++)
	{
		stream << "\xBA" << setw(GRID_WIDTH) << setfill(' ') << "" << "\xBA\n";
	}
	stream << "\xC8" << setw(GRID_WIDTH) << setfill('\xCD') << "" << "\xBC\n";
	cout << stream.str();
}

void drawEntireSnake(const Snake& snake)
{
	SnakeHead head = snake.getHead();
	setCursorPosition(head);
	cout << HEAD_CHAR;
	vector<SnakeEntity> bodyParts = snake.getBody();
	for (unsigned i = 0; i < bodyParts.size(); i++)
	{
		setCursorPosition(bodyParts[i]);
		cout << BODY_CHAR;
	}
}

void drawUpdatedGrid(const Snake& snake, const SnakeEntity& apple)
{
	SnakeHead head = snake.getHead();
	setCursorPosition(head);
	cout << HEAD_CHAR;

	// Calculates the previous position of the head
	int deltaX = DELTA_X_FOR_DIR[head.getDirection()];
	int deltaY = DELTA_Y_FOR_DIR[head.getDirection()];
	setCursorPosition(head.getX() - deltaX + 1, head.getY() - deltaY + 1);
	cout << BODY_CHAR;

	setCursorPosition(apple);
	cout << APPLE_CHAR;
}

void clearTail(const SnakeEntity& tail)
{
	setCursorPosition(tail);
	cout << ' ';
}

void drawScore(int score)
{
	setCursorPosition(0, 3 + GRID_HEIGHT);
	printf("Your score: %d", score);
}

void drawHighScore(int highScore)
{
	setCursorPosition(0, 5 + GRID_HEIGHT);
	printf("High score: %d", highScore);
}

// Returns true if the player wants to try again
bool handleGameOver(int score, int& highScore)
{
	setCursorPosition(0, 7 + GRID_HEIGHT);
	bool beatHighScore = score > highScore;
	if (beatHighScore)
	{
		highScore = score;
		// New high scores are instantly written to file
		writeHighScoreToFile(highScore);
		cout << "Congratulations! You beat the high score!" << endl << endl;
	}
	else
	{
		cout << "Better luck next time!" << endl << endl;
	}
	cout << "Do you want to try again (y/n)? ";
	return userWantsToTryAgain();
}

bool userWantsToTryAgain()
{		
	clearAsyncKeyStates();
	bool yKeyPressed = false;
	bool nKeyPressed = false;

	// Waits for the user to press Y or N
	while(!yKeyPressed && !nKeyPressed)
	{
		yKeyPressed = GetAsyncKeyState('Y') != 0;
		nKeyPressed = GetAsyncKeyState('N') != 0;
	}

	return yKeyPressed;
}
