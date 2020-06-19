# System of Linear equations solver

### Input file format 
*The coefficients of the equations only* 
* Two integers represents the number of equations and the number of variables, respectively.
* (no. of equations × (no. of variables + 1)) numbers (complex or real) separated by space.
### Function
* 2D augmented matrix is transformed to echelon form by Gaussian Elimination.
* Backtracking from the bottom row to the top row calculating each variable value.