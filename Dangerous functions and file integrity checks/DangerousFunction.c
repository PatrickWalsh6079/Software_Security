#include <stdio.h>
#include <string.h>
/*
File: DangerousFunction.c
Author: Patrick Walsh
Date: 4/11/2021
Purpose: Shows use of a potentially dangerous function in C
that can be exploited with a buffer overflow attack. The 
function strcpy() copies one string to another, but does
not check buffer size. The safe alternative is strncpy()
which takes in a size limit parameter to avoid buffer
overflow incidents.
*/

int main()
{
	
	// function takes in string, then copies to buffer:
	void copy_string(char * msg){
	char buffer[12];
	
		// strcpy(buffer, msg);  // strcpy() can be subject to buffer overflow attack
		
		strncpy(buffer, msg, 12);  // strncpy() prevents buffer overflow by specifying string limit
		
		printf("buffer: %s\n", buffer);

	}
	
	// copy_string() function executes with no issues
	printf("copy_string() executed:\n");
	copy_string("copying...");
	
	// copy_string() attempts to run with string larger than expected buffer size
	printf("\n\ncopy_string() executed:\n");
	copy_string("BUFFER OVERFLOW ATTACK");
	
	return 0;
}
	