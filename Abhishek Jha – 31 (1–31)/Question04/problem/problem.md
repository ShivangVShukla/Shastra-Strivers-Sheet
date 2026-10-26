Coding Question: Student Result With Subject-Wise Rule

Problem Statement:
Write a program that reads marks in three subjects. Each subject's marks must be between 0 and 100.
If any mark is less than 0 or greater than 100, print "Invalid marks".
Otherwise, calculate the average of the three marks.
If all three subjects have marks greater than or equal to 40, print "Pass".
Otherwise, print "Fail".

Input Format:
A single line containing three integers: m1, m2, and m3.

Output Format:
If any mark is invalid, print "Invalid marks".
Otherwise, print the average in the format "Average: X", followed by "Pass" or "Fail" on the next line.

Constraints:
-1000 <= m1, m2, m3 <= 1000

Sample Input 1:
70 80 90

Sample Output 1:
Average: 80
Pass

Sample Input 2:
90 90 30

Sample Output 2:
Average: 70
Fail

Sample Input 3:
90 105 80

Sample Output 3:
Invalid marks

Sample Input 4:
40 40 40

Sample Output 4:
Average: 40
Pass