#include <stdio.h>
#include <stdlib.h> // For exit()


//This program expects only ints
int main(int argc, char* argv[])
{
    FILE *fptr;
	int i, max = 0;
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
    while (fscanf(fptr, "%d", &i) != EOF)
    {
        printf ("%d", i);
        if(i > max)
			max = i;
    }
 
    fclose(fptr);
    return 0;
}