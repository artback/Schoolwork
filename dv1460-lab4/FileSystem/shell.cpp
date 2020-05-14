#include <iostream>
#include <sstream>
#include "FileSystem.h"

const int MAXCOMMANDS = 8;
const int COMMANDS = 14;
std::string availableCommands[COMMANDS] = {
	"quit","pwd","ls","format","mkdir","create","cd","cat", "rm","rmdir","cp",
	"createImage","restoreImage","help"
};

/* Takes usercommand from input and returns number of commands, commands are stored in strArr[] */
int parseCommandString(const std::string &userCommand, std::string strArr[]);
int findCommand(std::string &command);
std::string help();

int main(void) {

	FileSystem* filesystem = new FileSystem;

	std::string userCommand, commandArr[MAXCOMMANDS];
	std::string user = "freak@Artback";    // Change this if you want another user to be displayed
	std::string currentDir = "/";    // current directory, used for output

    bool bRun = true;

    do {
        cout << user << ":" << currentDir << "$ ";
        getline(std::cin, userCommand);
        int nrOfArguments = parseCommandString(userCommand, commandArr)-1;
        if (nrOfArguments >= 0) {

            int cIndex = findCommand(commandArr[0]);
            switch(cIndex) {

            case 0: // quit
                bRun = false;
                break;

			case 1:  // pwd
				cout << filesystem->pwd() << endl;
				break;

			case 2://ls
				switch(nrOfArguments) {
					case 0:
						cout << filesystem->ls(".") << endl;
						break;
					case 1:
						cout << filesystem->ls(commandArr[1]) << endl;
						break;
					default:
						int i =1;
						while(commandArr[i] != "" && i < MAXCOMMANDS) {
							cout << commandArr[i] << ":\n" << filesystem->ls(commandArr[i]) << endl;
							i++;
						}
				}
		        break;

			case 3:  // format
				filesystem->format();
				break;

			case 4:  // mkdir
		         if( 1 <= nrOfArguments){
			         int i=1;
                    while(commandArr[i] != "" && i < MAXCOMMANDS) {
                        if (!filesystem->mkdir(commandArr[1]))
                            cout << "Cannot create directory." << endl;
	                    i++;
                    }
		         }else{
                     cout << "mkdir: missing operand" << endl;
                 }
				break;
            case 5:  // create
                if (nrOfArguments == 1) {
	                int i =1;
		                string content;
		                getline(cin, content);
		                if (!filesystem->createFile(commandArr[i], content)) {
			                cout << "Failed to create File '" << commandArr[i] << "': File exist" << endl;
		                }
                } else {
                    cout << "Invalid number of arguments" << endl;
                }
                break;

			case 6:  // cd
				if (nrOfArguments == 1 ) {
						if (filesystem->cd(commandArr[1])) {
							currentDir = filesystem->pwd();
						} else {
							cout << "Failed to find directory." << endl;
						}
					}
				break;


			case 7:  // cat
				switch(nrOfArguments) {
					case 1:
						cout << filesystem->cat(commandArr[1]) << endl;
						break;
					default:
						int i=1;
						while(commandArr[i] != "" && i < MAXCOMMANDS) {
							cout << commandArr[i] << ":\n" << filesystem->cat(commandArr[i]) << endl ;
							i++;
						}
				}
				break;

			case 8:  // rm
            {
                if(1 <= nrOfArguments){
	                int i=1;
                    while (commandArr[i] != "" && i < MAXCOMMANDS) {
                        cout << filesystem->rm(commandArr[i]) << endl;
                        i++;
                    }
                } else{
                    cout << "rm: missing operand" << endl;
                }
            }
				break;

            case 9: //rmdir
                if(1 <= nrOfArguments) {
                    int i=1;
                    while (commandArr[i] != "" && i < MAXCOMMANDS) {
                        cout << filesystem->rmdir(commandArr[i]) << endl;
                        i++;
                    }
                }else{
	                cout << "rmdir: missing operand" << endl;
                }
                 break;

			case 10:  // cp
				if (nrOfArguments == 2) {
					if (filesystem->cp(commandArr[1], commandArr[2])) {
						cout << "File copied." << endl;
					}
					else {
						cout <<  "cp: cannot stat '"<< commandArr[1] <<"': No such file or directory "<< endl;
					}
				}
				else {
					cout << "Invalid number of arguments" << endl;
				}
				break;

			case 11: // createImage
				if (nrOfArguments == 1)
				{
					filesystem->createImage(commandArr[1]);
					cout << "Image created." << endl;
				}
				else
				{
					cout << "Invalid number of arguments" << endl;
				}
				break;

			case 12: // restoreImage
				if (nrOfArguments == 1) {
					if (filesystem->restoreImage(commandArr[1])) {
						cout << "Image restored." << endl;
					}else {
						cout << "Failed to restore image." << endl;
					}
				}
				else {
					cout << "Invalid number of arguments" << endl;
				}
				break;

            case 13: // help
	            cout << help() << endl;
                break;

            default:
                cout << "sh: command not found: " << commandArr[0] << endl;
            }
            for (int j = 0; j < nrOfArguments+1; ++j) {
               commandArr->clear();
            }
        }
    } while (bRun);
    delete filesystem;
    return 0;
}

int parseCommandString(const std::string &userCommand, string strArr[]) {
    std::stringstream ssin(userCommand);
    int counter = 0;
    while (ssin.good() && counter < MAXCOMMANDS) {
        ssin >> strArr[counter];
	    if(strArr[counter] != "")
        counter++;
    }
    if (strArr[0] == "")
        counter = 0;
    return counter;
}
int findCommand(std::string &command) {
    int index = -1;
    for (int i = 0; i < COMMANDS && index == -1; ++i) {
        if (command == availableCommands[i]) {
            index = i;
        }
    }
    return index;
}

std::string help() {
    std::string helpStr;
	helpStr += " _____________________________________________________________________________________________________________________\n";
	helpStr += "/   Kommando <krav> [valfri] - Sammanfattning                                                                         \\\n";
	helpStr += "| quit                       : l�mnar k�rningen <Redan gjord i filsystemet>                                           |\n";
	helpStr += "| pwd                        : skriver ut den fullst�ndiga s�kv�gen �nd� fr�n roten till filnamnet.                   |\n";
	helpStr += "| ls           [katalognamn] : listar inneh�llet i aktuellt katalog (filer och undermappar).                          |\n";
	helpStr += "| format                     : bygger upp ett tomt filsystem, dvs. formatterar disken.                                |\n";
	helpStr += "| mkdir        <katalognamn> : skapar en ny tom katalog p� den simulerande disken.                                    |\n";
	helpStr += "| cd           <katalognamn> : �ndrar aktuell katalog till den angivna katalogen p� den simulerade disken.            |\n";
	helpStr += "| create       <filnamn>     : skapar en fil p� den simulerande disken (datainneh�llet skrivs in p� en extra tom rad) |\n";
	helpStr += "| cat          <filnamn>     : skriver ut inneh�llet i filen filnamn p� sk�rmen.                                      |\n";
	helpStr += "| rm           <filnamn>     : tar bort angiven fil fr�n den simulerade disken.                                       |\n";
    helpStr += "| rmdir        <katalognamn> : tar bort angiven mapp fr�n den simulerade disken om den är tom.                                       |\n";
	helpStr += "| copy         <fil1> <fil2> : skapar en ny fil fil2 som �r en kopia av den existerande fil fil1.                     |\n";
	helpStr += "| createImage  <filnamn>     : sparar det simulerade systemet p� en fil p� datorns h�rddisk s� att                    |\n";
	helpStr += "|                              den g�r att �terskapa vid ett senare tillf�lle.                                        |\n";
	helpStr += "| restoreImage <filnamn>     : �terst�llet systemet fr�n en fil p� datorns h�rddisk.                                  |\n";
	helpStr += "| help                       : visa denna hj�lptext                                                                   |\n";
	helpStr += "\\_____________________________________________________________________________________________________________________/\n";
	return helpStr;
}
