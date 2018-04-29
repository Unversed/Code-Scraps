#include <stdio.h>
#include <stdlib.h> // For exit()
 
int main(int argc, char* argv[])
{
    FILE *fptr;
 
    char filename[100], c;
 
	if(argc != 2) 
	{
		printf("Usage: %s <filename>", argv[0]);
		exit(0);
	}
 
    // Open file
    fptr = fopen(argv[1], "r");
    if (fptr == NULL)
    {
        printf("Cannot open file \n");
        exit(0);
    }
 
    // Read contents from file
    c = fgetc(fptr);
    while (c != EOF)
    {
        printf ("%c", c);
        c = fgetc(fptr);
    }
 
    fclose(fptr);
    return 0;
}