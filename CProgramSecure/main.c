/*
 * Project name: CProgramSecure
 * Author: Patrick Walsh
 * Date: 7/24/2021
 * Purpose: Program provides simple interface that lets user make selections
 * and then exit. Upon exit, the program generates a password and waits for
 * user to confirm exiting before terminating the program.
 * 
 * Includes security enhancements so that program is compliant with secure
 * C programming coding standards.
 */


#include<stdio.h>
#include <string.h>

// Function prototypes
void fillPassword(size_t, char[]);
void showResults(char);
// should have void listed
//void showMenu();  // OLD CODE
void showMenu(void);  // NEW CODE DCL-20C compliant

// Define a variable to hold a password
// and the copy
char password[15];
char cpassword[15];

int main(void)
{ 
	// Welcome the User
	printf("Welcome to the C Array Program!\n");
        
	// Variables
//	char cont = 'y'; // OLD CODE
        int cont;  // NEW CODE FIO34-C compliant
	int cVar = 0; // process variable

	// Display menu and Get Selection
//        while (cont != 'E' && cont != 'e') {  // OLD CODE
	while ((cont != EOF) || (!feof(stdin) && !ferror(stdin))) {  // NEW CODE FIO34-C compliant
            
            // NEW CODE to check if user is trying to exit loop
            if (cont == 'E' || cont == 'e'){
                break;
            }
            // Display the Menu
            showMenu();
	
            // Get the user selection
            fflush(stdout);  // NEW CODE: place before getchar() to "flush" pending input and output
            cont = getchar();
            getchar();  // NEW CODE: so that loop does not repeat twice

            // Display the menu response
            showResults(cont);
            
	}  // end of while loop
        
	// Call the Copy routine	
	fillPassword(sizeof(password), password);	
	
	// Display variable values
	printf("password is %s\n", password);
	printf("cVar is %d\n", cVar);

	// Copy password 	
	memcpy(cpassword, password, sizeof(password));
	
	// Pause before exiting
	char confirm;
	printf("Confirm your exit!");
        fflush(stdout);  // NEW CODE: place before getchar() to "flush" pending input and output
	confirm = getchar();
        
        return 0;
}

// Make a String of '1's
void fillPassword(size_t n, char dest[]) {
	// Should be n-1
//	for (size_t j = 0; j < n; j++) {  // OLD CODE
        for (size_t j = 0; j < (n - 1); j++){  // NEW CODE STR31-C compliant
		dest[j] = '1';
	}
	// Add null terminator for string
	dest[n] = '\0';
}

/* Display the Results*/
void showResults(char value) {
	switch (value){
	case 'F':
	case 'f':
		printf("Welcome to the Football season!\n");
                break; // NEW CODE break needed to avoid printing football and soccer
	case 'S':		
	case 's':
		printf("Welcome to the Soccer season!\n");
		break;
	case 'B':		
	case 'b':
		printf("Welcome to the Baseball season!\n");
		break;			
	case 'E':		
	case 'e':
		printf("Exiting the Menu system!\n");
		break;
	default:
		printf("Please enter a valid selection\n");
	}
	
}

/* Display the Menu*/
void showMenu(void) {
	printf("Enter a selection from the following menu.\n");
	printf("B. Baseball season.\n");
	printf("F. Football season.\n");
	printf("S. Soccer season.\n");
	printf("E. Exit the system.\n");
}

