% 2.1 Contains

contains(X, [X | _]):-!.

contains(X, [_ | Z]):-contains(X, Z).


% 2.2 Concatenate two arrays into a third

concat([], L2, L2).

% Build L3 by backtracking
concat([X | Y], L2, [X | L3]):-concat(Y, L2, L3).


% 2.3 Is array in ascending order

asc([_]):-!.

asc([X, Y | L]):-(X =< Y), asc([Y | L]).


% 2.4 Is X the N-th element of L (Also doubles as what element is X of L?)

nth(X, [X | _], 1):-!.

nth(X, [_ | L], M):-nth(X, L, N), M is N + 1.


% 2.5 Is X the last element of L

last(X, [X]):-!.

last(X, [_ | L]):-last(X, L).


% 2.6 Are two elements neighbors in L

neighbors(X1, X2, [X1, X2 | _]):-!.
neighbors(X1, X2, [X2, X1 | _]):-!.

neighbors(X1, X2, [_ | L]):-neighbors(X1, X2, L).


% 2.7 Delete first occurrence of element in L

del_el(X, [X | L], L):-!.

del_el(X, [Y | L], [Y | L2]):-del_el(X, L, L2).


% 2.8 Delete all occurrences of element in L

del_el(_, [], []):-!.

del_el(X, [X | L], L2):-del_el(X, L, L2).

del_el(X, [Y | L], [Y | L2]):-del_el(X, L, L2).


% 2.9 Reverse list

reverse([], []):-!.

% In order for the list to be reversed X needs to be put at the end of the array in each iteration
% We do this in order to escape the problem of doing [L2 | X] which will result in multi-arrays
reverse([X | Y], L2):-reverse(Y, L),concat(L, [X], L2).

concat([], L, L):-!.
concat([X | Y], Z, [X | L]):-concat(Y, Z, L).


% 2.10 Subarray

% Base case unused unless L1 is empty
subarray([], _):-!.

% If first element is found, start searching for the rest 
% Cut so DFS doesn't continue after backtrack from prefix
subarray([X | L1], [X | L2]):-prefix(L1, L2),!.
% If the first element of L1 still isn't found, keep searching
subarray(L1, [_ | L2]):-subarray(L1, L2).

% If all prefixes are removed, i.e. all elements match the array is a subarray
prefix([], _):-!.

% Start removing matching elements
prefix([X | L1], [X | L2]):-prefix(L1, L2).


% 2.11 Unique elements of array

unique([], []):-!.

% Before rebuilding L2 as L1, check in contains if L2 already has X
% If so, remove X in contains
unique([X | L1], [X | L2]):-unique(L1, Y), contains(X, Y, L2).

contains(_, [], []):-!.

% If duplicate, rebuild L2 without it
contains(X, [X | Y], L2):-contains(X, Y, L2).
contains(X, [Z | Y], [Z | L2]):-contains(X, Y, L2).


% 2.12 Is array length N (also finds solution for N)

arr_length([], 0):-!.

% Build N back up by backtracking, alternative is to decrement N before each pass to arr_length
arr_length([_ | L], N):-arr_length(L, M), N is M + 1.

% Alternative solution that doesn't find solution for given N
% arr_length([_ | L], N):- M is N - 1,arr_length(L, M).


% 2.13 Maximum element in list

max_el([X], X):-!.

% If M is larger than the current element when rebuilding, continue with M
max_el([X | L], M):-max_el(L, M), M >= X, !.
% Else update M
max_el([X | L], X):-max_el(L, _).


% 2.2.1 Level multi-dimensional array

level([], []):-!.

level([X | L1], [X | L2]):-not(is_list(X)), level(L1, L2).

% Flatten head and flatten tail, concatenate them into L2
level([X | L1], L2):-level(X, LX), level(L1, LL1), concat(LX, LL1, L2).


% 2.2.2 Multi-dimensional array contains element

multi_contains([X | _], X):-!.

multi_contains([Y | L], X):-not(is_list(Y)),multi_contains(L, X), !.
% Check head Y as a list, if not found THEN check tail L as a list
multi_contains([Y | L], X):-multi_contains(Y, X);multi_contains(L, X).

% Or alternative solution using leveling:
% multi_contains(L, X):-level(L, L1),multi_contains(L1, X).

% 2.2.3 Multi-dimensional array max element

% Base case for passing a list as a singular element with no tail; find its maximum
% In other words, this de-listifies list heads
multi_max([X], M):-is_list(X), multi_max(X, M).

% If head is a list, find its maxium and then compare it with the maximum of its tail
multi_max([X | Y], M):-
    is_list(X), 
    multi_max(X, M2), 
    multi_max(Y, M3), 
    biggest(M2, M3, M),
    !.

% Base case is below previous because if X is a list this is entered
multi_max([X], X).

% If head is not a list, compare it with the maximum of its tail
multi_max([X | Y], M):-
    multi_max(Y, M2), 
    biggest(X, M2, M).

biggest(X, Y, X):-X >= Y.
biggest(X, Y, Y):-Y > X.
