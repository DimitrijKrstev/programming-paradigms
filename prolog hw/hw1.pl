%% 1 Uneven Palindrome

% Key idea is to count the position of each element after the 2nd half of the 
% list (Len // 2, // is floor int division), then do Z = Len - current_pos and 
% check if current element is same as element at Z

% Further optimization could be done, e.g. is_element_at could check 2nd half only

% Alternative solution is to reverse the list and then compare it with its non-reversed
% self ensuring each element matches.

neparen_palindrom(L):-length(L, Len), Len mod 2 =:= 1, palindrome(L, L, Len, 0), !.

palindrome([], _, _, _):-!.
palindrome(_, _, Len, N):-
    N is Len // 2.
palindrome([X | Y], L, Len, N):-
    M is N + 1,
    Pos is Len - M,
    is_element_at(X, L, Pos, 0),
    palindrome(Y, L, Len, M).

is_element_at(X, [X | _], Pos, Pos):-!.
is_element_at(X, [_ | L], Pos, N):-M is N + 1, is_element_at(X, L, Pos, M).


%% 2 Most frequented sub-array with length N

nаj_podniza([X | Y], N, Lmax):-
    sub_arr([X | Y], N, [0], 0, Lmax), !.

sub_arr([], _, Lmax, _, Lmax).
sub_arr([X | Y], N, Lmax, Max, FinalLMax):-
    next_N_els([X | Y], N, L2),
    occurrence(L2, Y, N, Lmax, Max, NewLmax, NewMax),
    sub_arr(Y, N, NewLmax, NewMax, FinalLMax).

next_N_els(_, 0, []).
next_N_els([], _, []). % <- Za sluchaj kade N > len(L)
next_N_els([Y | Z], N, [Y | L]):-M is N - 1, next_N_els(Z, M, L).

occurrence(L1, L2, N, Lmax, Max, NewLmax, NewMax):-
    findall(L1, is_sublist(L1, L2, N), L),
    length(L, M),
    ((Max >= M, NewMax is Max, NewLmax = Lmax); 
    (NewMax is M, NewLmax = L1)).

is_sublist(_, _, 0):-!.
is_sublist([X | Y], [X | Z], N):-M is N - 1, is_sublist(Y, Z, M). 
is_sublist(L, [_ | X], N):-is_sublist(L, X, N).


%% 3 Small, Big order array

proveri([X | Y]):-length([X | Y], N), N > 1, next_is_greater(X, Y).

next_is_greater(_, []):-!.
next_is_greater(X, [Y | L]):-X < Y, next_is_lesser(Y, L).
next_is_lesser(_, []):-!.
next_is_lesser(X, [Y | L]):-X > Y, next_is_greater(Y, L).


%% 4 Permutations

remove_from(X, [X | Y], Y).
remove_from(X, [Z | Y], [Z | L]):-remove_from(X, Y, L).

permutation([], []).
permutation(L, [X | Y]) :-
    remove_from(X, L, R),
    permutation(R, Y).

permutations(L1, L2) :-
    findall(X, permutation(L1, X), L2).

%% 5 Binary arithmetic

% 5.1 Addition

% Alternative solution is to reverse the lists instead of equalizing them.

% Current position digit with digits sum + carry over, return new carry over
binary_add(X1, X2, C, L, [C | L], 1):-X1 + X2 =:= 2, !.
binary_add(X1, X2, C, L, [Res | L], C):-X1 + X2 =:= 1, Res is 1 - C, !.
binary_add(_, _, C, L, [C | L], 0):-!.

addition([X1], [X2], L, C):-
    binary_add(X1, X2, 0, [], L, C).

addition([X1 | Y1], [X2 | Y2], L2, NewC):-
    addition(Y1, Y2, L, C),
    binary_add(X1, X2, C, L, L2, NewC).

% When both arrs are equal length start addition
equalize(Y1, Y2, L, C):-length(Y1, L1), length(Y2, L2),
    L1 =:= L2, 
    addition(Y1, Y2, L, C).

% After addition of equal lengths, add digits from the longer array to the front of the result, 
% with appropriate carry over
equalize([X1 | Y1], Y2, NewL, NewC):-
    length([X1 | Y1], L1), length(Y2, L2),
    L1 > L2, 
    equalize(Y1, Y2, L, C),
    binary_add(X1, 0, C, L, NewL, NewC).

equalize(Y1, [X1 | Y2], NewL, NewC):-
    equalize(Y1, Y2, L, C),
    binary_add(X1, 0, C, L, NewL, NewC).

% If there is a final carry over, add 1 to the front of the array
sobiranje(L1, L2, L3):-equalize(L1, L2, L, C),
    C =:= 1, binary_add(0, 0, 1, L, L3, _), !.

sobiranje(L1, L2, L3):-equalize(L1, L2, L3, _), !.

% 5.2 Subtraction

subtract([X1], [X2], NewL, NewC):-
    binary_sub(X1, X2, 0, [], NewL, NewC).

subtract([X1 | Y1], [X2 | Y2], NewL, NewC):-
    subtract(Y1, Y2, L, C),
    binary_sub(X1, X2, C, L, NewL, NewC).

binary_sub(X1, X2, C, L, [Res | L], 0):-X1 - X2 =:= 1, Res is 1 - C, !.
binary_sub(X1, X2, C, L, [C | L], C):-X1 - X2 =:= 0, !.
binary_sub(_, _, C, L, [Res | L], 1):-Res is 1 - C, !.

equalize_sub(L1, L2, L, C):-
    length(L1, NL1),
    length(L2, NL2),
    NL1 =:= NL2,
    subtract(L1, L2, L, C).

equalize_sub([X | L1], L2, NewL, NewC):-
    equalize_sub(L1, L2, L, C),
    binary_sub(X, 0, C, L, NewL, NewC).

sub(L1, L2, [1 | L]):-equalize_sub(L1, L2, [X, _ | L], C), X =:= 0, C \= 0.
sub(L1, L2, L):-equalize_sub(L1, L2, L, _).

% Util

remove_zeros([], [0]).
remove_zeros([1 | L1], [1 | L1]).
remove_zeros([0 | L1], L2):-remove_zeros(L1, L2).

is_subtractable([], []).
is_subtractable([1 | _], [0 | _]).
is_subtractable([0 | L1], [0 | L2]):-is_subtractable(L1, L2).
is_subtractable([1 | L1], [1 | L2]):-is_subtractable(L1, L2).

odzemanje(L1, L2, NewR):-length(L1, N1), length(L2, N2), N1 > N2,
    sub(L1, L2, R),
    remove_zeros(R, NewR), !.

odzemanje(L1, L2, NewR):-length(L1, N1), length(L2, N2), N1 =:= N2,
    is_subtractable(L1, L2),
    sub(L1, L2, R),
    remove_zeros(R, NewR), !.

% 5.3 Multiplication

shift([], 0, []).
shift([], N, [0 | L2]):-M is N - 1, shift([], M, L2).
shift([X | L1], N, [X | L2]):-shift(L1, N, L2).

multiply(L1, [1], 1, L1).
multiply(_, [0], 1, [0]).

multiply(L1, [1 | Y2], N, NewR):-
    multiply(L1, Y2, M, R),
    shift(L1, M, L2),
    sobiranje(L2, R, NewR),
    N is M + 1.

multiply(L1, [0 | Y2], N, R):-
    multiply(L1, Y2, M, R),
    N is M + 1.

mnozenje(L1, L2, L3):-multiply(L1, L2, _, L3), !.

% 5.4 Division

bigger([], []).
bigger([1 | _], [0 | _]).
bigger([1 | L1], [1 | L2]):- bigger(L1, L2).
bigger([0 | L1], [0 | L2]):- bigger(L1, L2).

can_divide(L1, L2):- 
    length(L1, N1), 
    length(L2, N2), 
    N1 =:= N2, 
    bigger(L1, L2).

can_divide(L1, L2):- 
    length(L1, N1), 
    length(L2, N2), N1 > N2, !.

delenje(L1, L2, R):-
    can_divide(L1, L2),
    odzemanje(L1, L2, R1),
    delenje(R1, L2, R2),
    sobiranje(R2, [1], R), !.

delenje(_, _, [0]):- !.


%% 6 Matrix multiplied by transposed self

mat_mul([], [], 0).
mat_mul([X1 | L1], [X2 | L2], N):-mat_mul(L1, L2, R), N is R + X1 * X2.

multiply_rows(_, [], []).
multiply_rows(X1, [X2 | L], [MR | R]):-mat_mul(X1, X2, MR), multiply_rows(X1, L, R).

row_by_row([], _, []).
row_by_row([X1 | L1], L2, [HR | R]):-
    multiply_rows(X1, L2, HR), 
    row_by_row(L1, L2, R).

presmetaj(L1, R):-row_by_row(L1, L1, R), !.


%% 7 Unique lists ordered by length and then by elements

% Bubble-sort approach

lists_equal([], []).
lists_equal([X | L1], [X | L2]):-lists_equal(L1, L2).

remove_element(_, [], []). % Base case is this rather than below predicate to ensure unique lists
remove_element(X1, [X1 | L], R):-remove_element(X1, L, R).
remove_element(X1, [X2 | L], [X2 | R]):-remove_element(X1, L, R).

first_is_bigger([X1 | _], [X2 | _]):- X1 > X2.
first_is_bigger([X1 | L1], [X2 | L2]):- X1 =:= X2, first_is_bigger(L1, L2).
max_elements_lists(L1, L2, R):-first_is_bigger(L1, L2), R = L1.
max_elements_lists(_, L2, R):-R = L2.

current_pos_max([X2], X2).

current_pos_max([X | L], NewMax):-
    current_pos_max(L, PosMax),
    length(PosMax, NM), length(X, N),
    NM =:= N,
    max_elements_lists(PosMax, X, Max),
    NewMax = Max, !.

current_pos_max([X | L], NewMax):-
    current_pos_max(L, Max),
    length(Max, NM), length(X, N),
    NM > N,
    NewMax = Max, !.

current_pos_max([X | L], NewMax):- % Else if X is bigger than current max
    current_pos_max(L, _),
    NewMax = X, !.

bubble_sort_arr([X], [X]).
bubble_sort_arr([X1 | L], [X1 | R]):-
    current_pos_max(L, Xmax),
    lists_equal(X1, Xmax),
    remove_element(X1, L, L1), % In case first position is correct and has a dupe
    bubble_sort_arr(L1, R).

bubble_sort_arr([X1 | L], [Xmax | R]):-
    current_pos_max(L, Xmax),
    remove_element(Xmax, [X1 | L], L1),
    bubble_sort_arr(L1, R).

transform(L, R):-bubble_sort_arr(L, R), !.


%% 8 Delete every 2nd occurrence of element in multilist

brisi_sekoe_vtoro(L, R) :-
    delete_2nd(L, R, [], _), !.

delete_2nd([], [], O, O).
delete_2nd([X | L], [X2 | L2], OI, OO) :-
    is_list(X),
    delete_2nd(X, X2, OI, OT),
    delete_2nd(L, L2, OT, OO).

delete_2nd([X|L], P, OI, OO) :-
    count_occ(X, OI, C),
    C1 is C + 1,
    update_occ(X, OI, OT),
    C1 mod 2 =:= 0,
    P = PT,
    delete_2nd(L, PT, OT, OO).

delete_2nd([X|L], P, OI, OO) :-
    update_occ(X, OI, OT),
    P = [X | PT],
    delete_2nd(L, PT, OT, OO).

count_occ(_, [], 0).
count_occ(E, [E - C | _], C).
count_occ(E, [O - _ | R], C) :-
    E \= O,
    count_occ(E, R, C).

update_occ(E, [], [E - 1]).
update_occ(E, [E - C | R], [E - NC | R]) :-
    NC is C + 1.
update_occ(E, [O - C | R], [O - C | NR]) :-
    E \= O,
    update_occ(E, R, NR).
