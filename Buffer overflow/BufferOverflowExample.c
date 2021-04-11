#include <stdio.h>
#include <string.h>
/*
File: BufferOverflowExample.c
Author: Patrick Walsh
Date: 4/2/2021
Purpose: Shows a basic example of a buffer overlow vulnerability and
a simple way to mitigate this vulnerability.
Program uses strncpy() function instead of strcpy() to guard against a
buffer overflow. strncpy() requires a length check parameter to ensure that
the buffer memory is not overfilled. Examples:

strcpy(dest_text, text_being_copied)

vs.

strncpy(dest_text, text_being_copied, length_limit)
*/

int main()
{
	
	char db_entry[12] = "old data";
	printf("db_entry: %s\n", db_entry);  // print our db_entry
	
	
	// strcpy(db_entry, "too much data to fit which causes buffer overflowwwwwwww");  // buffer overflow point of attack
	strncpy(db_entry, "too much data to fit which causes buffer overflowwwwwwww", 12);  // buffer overflow mitigation
	
	printf("***AFTER UPDATE***\n");
	printf("db_entry: %s\n",  db_entry);  // print our db_entry again
	
	return 0;
}
	