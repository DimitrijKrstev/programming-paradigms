% 1.1 INTERSECTION

% location, direction pairs
kola(jug,sever).
kola(zapad,sever).
kola(istok,sever).
kola(sever,zapad).

% X, Y; as for car X is on the right of car Y
na_desno(zapad,jug).
na_desno(jug,istok).
na_desno(istok,sever).
na_desno(sever,zapad).

% the current car X has a car Y left to it if it itself is on the right of that car
na_levo(X,Y):-na_desno(Y,X).

% current car is going straight if its direction is the car on the left of the car
% on its left such that Y=Y (assuming intersection always has 4 cars on each side)
going_straight(X,Y):-na_desno(X,Z), na_desno(Z,Y).

% current car is going left if the car on the left of us has our direction
going_left(X):-kola(X,Y), na_desno(X,Y).

% stop going straight only if the car on the right is not going left
stop_straight(X):-na_desno(Y,X), not(going_left(Y)).


% the car on the right has priority over us if its on our right and it is 
% going straight whilst we are going left
right_has_priority(X):-na_desno(X,Z), kola(Z,Y), going_straight(Z,Y).

% the car opposite us has priority if it is either going forward or right (our left)
opposite_has_priority(X,Y):-na_desno(X,Z), na_desno(Z,V), (kola(V,Y); kola(V,Y)).

stop_left(X,Y):-right_has_priority(X); opposite_has_priority(X,Y).


stop(X):-kola(X,Y), (going_straight(X,Y), stop_straight(X)); (going_left(X), stop_left(X,Y)).
