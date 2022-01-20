# monte-carlo

## DESCRIPTION

A JavaFX program that estimates volume of bounded regions. The volume is calculated in
a 2x2x2 cubic region using random samples of points, and division of these points within and outside
the given bounded region. The bounded region is entered as six strings-- a lower and upper bound for 
the x, y, and z coordinates. The string is evaluated at each random point using JEP Java Parser.
There is no limit to the sample count of points, but between 5000 - 50000 is recommended.
Currently only supports cartesian coordinates, with polar and spherical coordinates planned.

## CLASSES

### Dot
Cosntructor takes x, y, z coordinates and boolean variable, in, as parameters.
Includes a sphere with two options for color using PhongMaterial-- red and blue for inside and outside the region respectively.
Includes functions for returning the sphere, and sending the sphere to the back for more proper perspective.

### DotList
An array of class Dot. Constructor takes sample count as parameter.
The function play() assigns random coordinates to each dot, determines if they are in the bounded region,
then estimates the total bounded volume with the forumla (A)(inDots)/(inDots + outDots) where A = total region = (2^3).

### MonteCarloDriver
Includes GUI elements and camera. The function start() initializes the groups, scene, and stage.
processScroll() uses JavaFX ScrollEvent to allow the region to be rotated along its center y-axis.

## EXAMPLE BOUNDED REGIONS

1)
-250 &emsp 250
-250 &emsp 250
0.5*y^x*-1 &emsp 10*y^6 / x

2)
y^2/z &emsp y^3/(y-z^2)
-250 &emsp 250
-250 &emsp 250

3)
-500 &emsp 500
-500 &emsp 500
ln(cos(ln(x)))*-1 &emsp ln(y)^2*15

4)
-500 &emsp asin(y/z)
-250 &emsp 250
-250 &emsp 250

5)
y-z &emsp z-y
-250 &emsp 250
-250 &emsp 250

## E-MAIL

ntjohn04@louisville.edu
