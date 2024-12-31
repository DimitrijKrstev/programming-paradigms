%% 1 Constraint satisfaction integram

ime(teo).
ime(mira).
ime(bruno).
ime(igor).

hrana(sendvich).
hrana(pita).
hrana(hamburger).
hrana(pica).

hobi(krstozbori).
hobi(pishuvanje).
hobi(chitanje).
hobi(fotografija).

maica(bela).
maica(zolta).
maica(sina).
maica(crvena).

devojka(mira).

% 1.1 Teo is eating a sandwich and is leftmost (first in list)
constraint1([(teo, sendvich, _, _) | _]).

% 1.2 Mira is eating a pie and is solving a crossword
constraint2([(mira, pita, krstozbori, _) | _]).
constraint2([_ | L]):-constraint2(L).

% 1.3 The girl has a white shirt
constraint3([(Ime, _, _, bela) | _]):-devojka(Ime).
constraint3([_ | L]):-constraint3(L).

% 1.4 Bruno has a yellow shirt
constraint4([(bruno, _, _, zolta) | _]).
constraint4([_ | L]):-constraint4(L).

% 1.5 The person who wants to write is eating a burger
constraint5([(_, hamburger, pishuvanje, _) | _]).
constraint5([_ | L]):-constraint5(L).

% 1.6 The person eating pie is next to teo
constraint6([(teo, _, _, _), (_, pita, _, _) | _]).
constraint6([(_, pita, _, _), (teo, _, _, _) | _]).
constraint6([_ | L]):-constraint6(L).

% 1.7 Bruno is sitting to the person eating pizza
constraint7([(bruno, _, _, _), (_, pica, _, _) | _]).
constraint7([(_, pica, _, _), (bruno, _, _, _) | _]).
constraint7([_ | L]):-constraint7(L).

% 1.8 The person sitting next to white blouse is eating pizza
constraint8([(_, _, _, bela), (_, pica, _, _) | _]).
constraint8([(_, pica, _, _), (_, _, _, bela) | _]).
constraint8([_ | L]):-constraint8(L).

% 1.9 Igor reads
constraint9([(igor, _, chitanje, _) | _]).
constraint9([_ | L]):-constraint9(L).

% 1.10 Person on the right of the girl has a blue shirt
constraint10([(Ime, _, _, _), (_, _, _, sina) | _]):-devojka(Ime).
constraint10([_ | L]):-constraint10(L).

% Util
different([]).
different([X | L]) :- 
    not(member(X, L)), 
    different(L).

% Solution is singular and correct without different predicate constraint
% but speeds up computation time drastically
populate_L(L):-
    L = [(Ime1,Hrana1,Hobi1,Maica1),
         (Ime2,Hrana2,Hobi2,Maica2),
         (Ime3,Hrana3,Hobi3,Maica3),
         (Ime4,Hrana4,Hobi4,Maica4)],
    
    ime(Ime1), ime(Ime2), ime(Ime3), ime(Ime4),
    different([Ime1, Ime2, Ime3, Ime4]),
    
    hrana(Hrana1), hrana(Hrana2), hrana(Hrana3), hrana(Hrana4),
    different([Hrana1, Hrana2, Hrana3, Hrana4]),
    
    hobi(Hobi1), hobi(Hobi2), hobi(Hobi3), hobi(Hobi4),
    different([Hobi1, Hobi2, Hobi3, Hobi4]),
    
    maica(Maica1), maica(Maica2), maica(Maica3), maica(Maica4),
    different([Maica1, Maica2, Maica3, Maica4]).

reshenie(L) :-
    populate_L(L),
    constraint1(L),
    constraint2(L),
    constraint3(L),
    constraint4(L),
    constraint5(L),
    constraint6(L),
    constraint7(L),
    constraint8(L),
    constraint9(L),
    constraint10(L).
