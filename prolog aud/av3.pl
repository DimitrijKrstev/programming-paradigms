% 3.1 Graph

arc(1,2).
arc(1,3).
arc(2,4).
arc(2,5).
arc(3,6).
arc(3,7).
arc(6,8).
arc(8,9).
arc(7,9).
arc(4,8). 

% Either direct or indirect path
path(A, B):-arc(A, B); arc(A, C), path(C, B).

recorded_path(A, B, [A, B]):-arc(A, B).
recorded_path(A, B, [A | R]):-arc(A, C), recorded_path(C, B, R).


% 3.2 Geometric shapes

tocka(1,1).
tocka(2,3).
tocka(4,1).
tocka(5,3).
tocka(6,1).
otsecka(tocka(1,1), tocka(2,3)).
triagolnik(tocka(4,1), tocka(6,1), tocka(5,3)).

horizontal_segment(otsecka(tocka(X, _), tocka(X, _))).
vertical_segment(otsecka(tocka(_, Y), tocka(_, Y))).

quadrilateral(tocka(X, Y), tocka(X, Y), tocka(X, Y), tocka(X, Y))).

is_quadrilateral_rectangle(quadrilateral(tocka(X, Y), tocka(X, Y2), tocka(X2, Y), tocka(X2, Y2))).


% 3.3 Families

% data(den, mesec, godina).
% rab_mesto(ime, plata).

% lice(ime, prezime, data, rab_mesto).
% semejstvo(tatko, majka, lista_deca).

semejstvo(lice(trajko, trajkovski, data(7,maj,1950), raboti(mrtv,20000)),
lice(persa, trajkovska, data(1,jan,1952), nevraboteno),
[lice(petko, trajkovski, data(5,maj,1973), nevraboteno)]). 

% Find all names of fathers that have the last name trajkovski: 
% semejstvo(lice(X, trajkovski, _, _), _, _)

% All mothers that have 3 children:
% semejstvo(_, X, [_])

% All names of mothers that have at least 3 children:
% semejstvo(_, lice(Ime, _, _ , _), [_, _, _ | _])

is_man(X):-semejstvo(X, _, _).
is_woman(X):-semejstvo(_, X, _).
is_child(X):-semejstvo(_, _, L), is_in(X, L).

is_in(X, [X | _]).
is_in(X, [_ | L]):-is_in(X, L).

person_with_full_name(X,Y) :- semejstvo(lice(X, Y, _, _), _, _); semejstvo(_, lice(X, Y, _, _), _);
(semejstvo(_, _, L), is_in(lice(X, Y, _, _), L)).

person_exists(X):-is_man(X); is_woman(X); is_child(X).

date_of_birth(Date):-person_exists(lice(_, _, Date, _)).

salary(P, S):-P = (lice(_, _, _, raboti(_, S))), person_exists(P).
salary(P, 0):-P = (lice(_, _, _, nevraboteno)), person_exists(P).

salary_of_men(S):-salary(P, S), is_man(P).

children_born_in_year(P, Y):- P = lice(_, _, data(_, _, Y), _), is_child(P).

employed(lice(_, _, _, employed(_, _))).
employed_women(X):-is_woman(X), employed(X).






