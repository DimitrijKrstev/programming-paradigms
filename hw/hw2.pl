%% 1 Families

lice(1,petko,petkovski,m,datum(1,3,1950),kratovo,skopje).
lice(2,marija,petkovska,z,datum(30,5,1954),kumanovo,skopje).
lice(3,ljubica,petkovska,z,datum(29,11,1965),skopje,skopje).
lice(4,vasil,vasilev,m,datum(8,4,1954),bitola,bitola).
lice(5,elena,vasileva,z,datum(19,6,1958),resen,bitola).
lice(6,krste,krstev,m,datum(9,8,1948),veles,veles).
lice(7,biljana,krsteva,z,datum(13,8,1949),veles,veles).
lice(8,igor,krstev,m,datum(26,10,1971),veles,skopje).
lice(9,kristina,krsteva,z,datum(30,5,1974),kumanovo,skopje).
lice(10,julija,petrova,z,datum(30,5,1978),skopje,skopje).
lice(11,bosko,petkovski,m,datum(13,11,1981),skopje,skopje).
lice(12,gjorgji,vasilev,m,datum(15,7,1978),bitola,bitola).
lice(13,katerina,petkovska,z,datum(11,12,1979),bitola,skopje).
lice(14,petar,vasilev,m,datum(21,2,1982),skopje,skopje).
lice(15,andrej,krstev,m,datum(3,8,1998),skopje,skopje).
lice(16,martina,petkovska,z,datum(5,12,2005),skopje,skopje).

familija(1,2,[9,10]).
familija(1,3,[11]).
familija(4,5,[12,13,14]).
familija(6,7,[8]).
familija(8,9,[15]).
familija(11,13,[16]).


% 1.1 People Born in different cities from their parents
rodeni_razlicen_grad(N):-
    findall(D,( 
            familija(T, M, LD),
            lice(T, _, _, _, _, XT, _),
            lice(M, _, _, _, _, XM, _),
            member(D, LD),
            lice(D, _, _, _, _, XD, _),
            XD \= XT, XD \= XM
            ), L),
    length(L, N).


% 1.2 Ancestors with same gender, born 1 week apart (months have exact 30 days)

within_week_range(PDen, PMesec, Den, Mesec) :-
    Start is Den - 7,
    End is Den + 7,
    within_range(PDen, PMesec, Den, Mesec, Start, End).

within_range(PDen, PMesec, _, Mesec, Start, End) :-
    (Start > 0, End =< 30, PMesec = Mesec, PDen >= Start, PDen =< End);
    (Start =< 0, PrevDen is 30 + Start, PrevMesec is (Mesec - 1 + 12) mod 12, 
     (PMesec = PrevMesec, PDen >= PrevDen; PMesec = Mesec, PDen =< End));
    (End > 30, NextDen is End - 30, NextMesec is (Mesec + 1) mod 12, 
     (PMesec = Mesec, PDen >= Start; PMesec = NextMesec, PDen =< NextDen)).

filter([], _, _, _, []).
filter([P | L], Pol, Den, Mesec, [P | R]):-
    lice(P, _, _, Pol, datum(PDen, PMesec, _), _, _),
    within_week_range(PDen, PMesec, Den, Mesec),
    filter(L, Pol, Den, Mesec, R).

filter([_ | L], Pol, Den, Mesec, R):-filter(L, Pol, Den, Mesec,  R).

all_predci(D, Pol, Den, Mesec, L):-
    familija(T, M, LD), 
    member(D, LD), 
    all_predci(T, Pol, Den, Mesec, TL), 
    all_predci(M, Pol, Den, Mesec, ML), 
    append([T | TL], [M | ML], TML),
    filter(TML, Pol, Den, Mesec, L).

% In case person doesn't have ancestors
all_predci(_, _, _, _, []).

predci(D, L):-lice(D, _, _, Pol, datum(Den, Mesec, _), _, _),all_predci(D, Pol, Den, Mesec, L), !.


%% Telecommunications

telefon(111111,petko,petkovski,[povik(222222,250),povik(101010,125)]).
telefon(222222,marija,petkovska,[povik(111111,350),povik(151515,113),povik(171717,122)]).
telefon(333333,ljubica,petkovska,[povik(555555,150),povik(101010,105)]).
telefon(444444,vasil,vasilev,[povik(171717,750)]).
telefon(555555,elena,vasileva,[povik(333333,250),povik(101010,225)]).
telefon(666666,krste,krstev,[povik(888888,75),povik(111111,65),povik(141414,50),povik(161616,111)]).
telefon(777777,biljana,krsteva,[povik(141414,235)]).
telefon(888888,igor,krstev,[povik(121212,160),povik(101010,225)]).
telefon(999999,kristina,krsteva,[povik(666666,110),povik(111111,112),povik(222222,55)]).
telefon(101010,julija,petrova,[]).
telefon(121212,bosko,petkovski,[povik(444444,235)]).
telefon(131313,gjorgji,vasilev,[povik(141414,125),povik(777777,165)]).
telefon(141414,katerina,petkovska,[povik(777777,315),povik(131313,112)]).
telefon(151515,petar,vasilev,[]).
telefon(161616,andrej,krstev,[povik(666666,350),povik(111111,175),povik(222222,65),povik(101010,215)]).
telefon(171717,martina,petkovska,[povik(222222,150)]).

sms(111111,[222222,999999,101010]).
sms(444444,[333333,121212,161616]).
sms(111111,[777777]).
sms(666666,[888888]).
sms(444444,[555555,121212,131313,141414]).
sms(666666,[777777,888888]).
sms(888888,[999999,151515]).
sms(171717,[131313,161616]).


% 2.1 Most calls (contained in other's call lists, excluding own)

person_max_calls([(X, Y, Num)], X, Y, Num). 
person_max_calls([(_, _, Num) | L], MaxX, MaxY, MaxNum):-
    person_max_calls(L, MaxX, MaxY, MaxNum),
    Num < MaxNum.

person_max_calls([(X, Y, Num) | L], X, Y, Num):-
    person_max_calls(L, _, _, _).

najbroj(X, Y):-
    findall((X, Y, N2),
            (telefon(B, X, Y, _),
            findall((_, _),
                (telefon(_, _, _, PL2),
                member(povik(B, _), PL2)
                ),
                 L2),
            length(L2, N2)
            ),
        L),
    person_max_calls(L, X, Y, _), !.


% 2.2 Favorite number (person who has most outgoing and inbound call time with other person), 
% sms counts as 100 call time

id_minute_sum_pairs(PCalls, PSMS, LCalls, LSMS, R) :-
    append([PCalls, PSMS, LCalls, LSMS], Combined),
    findall(ID, member(povik(ID, _), Combined), AllIDs),
    sort(AllIDs, UniqueIDs),  % Extract unique IDs from combined list
    findall((ID, TotalMins),
            (member(ID, UniqueIDs),
             sum_minutes(ID, Combined, TotalMins)),
            R).

sum_minutes(ID, List, TotalMins) :-
    findall(Mins, member(povik(ID, Mins), List), MinutesList),
    sum_list(MinutesList, TotalMins).

max_mins([(ID, Mins)], ID, Mins).
max_mins([(_, Mins) | L], MaxId, MaxMins):-
    max_mins(L, MaxId, MaxMins),
    MaxMins > Mins.

max_mins([(ID, Mins) | L], ID, Mins):-
    max_mins(L, _, _).

omilen(B, R):-
    telefon(B, _, _, PCalls),
    (sms(B, PSMS); PSMS = []), % In case if person hasn't made sms
    findall(povik(B2, Mins),
            (telefon(B2, _, _, PL2),
            member(povik(B, Mins), PL2)
            ),
            LCalls),
    findall(povik(B2, 100),
            (sms(B2, PL2),
            member(B, PL2)
            ),
            LSMS),
    id_minute_sum_pairs(PCalls, PSMS, LCalls, LSMS, LSums),
    max_mins(LSums, R, _), !.


%% 3 Taxi Company

klient(1,petko,petkov,[usluga(a,b,50,datum(12,12,2015),23),usluga(c,a,50,datum(7,12,2015),34),usluga(c,f,40,datum(7,11,2015),23)]).
klient(2,vasil,vasilev,[usluga(a,e,50,datum(25,12,2015),12),usluga(c,g,40,datum(17,11,2015),56),usluga(g,d,50,datum(17,12,2015),45),usluga(e,a,40,datum(24,12,2015),34)]).
klient(3,krste,krstev,[usluga(c,b,60,datum(31,12,2015),56),usluga(e,f,60,datum(31,12,2015),34)]).
klient(4,petar,petrov,[usluga(a,f,50,datum(25,12,2015),23),usluga(f,d,50,datum(25,12,2015),34)]).
klient(5,ivan,ivanov,[usluga(d,g,50,datum(7,12,2015),56),usluga(g,e,40,datum(25,12,2015),34)]).
klient(6,jovan,jovanov,[usluga(c,f,50,datum(5,12,2015),12),usluga(f,d,50,datum(27,12,2015),45)]).
klient(7,ana,aneva,[usluga(e,d,50,datum(11,12,2015),12),usluga(d,g,50,datum(11,12,2015),12)]).
klient(8,lidija,lideva,[usluga(e,g,50,datum(29,12,2015),45),usluga(f,b,50,datum(29,12,2015),34)]).

rastojanie(a,b,4).
rastojanie(a,c,7).
rastojanie(b,c,5).
rastojanie(b,d,3).
rastojanie(c,d,4).
rastojanie(b,e,6).
rastojanie(c,e,2).
rastojanie(b,f,8).
rastojanie(e,f,5).
rastojanie(f,g,3).


% 3.1 How many times location has been current location or destination

izbroj_lokacija(Lok,Br):-
    findall(Lok,
            (
                klient(_, _, _, UL),
                (member(usluga(Lok, _, _, _, _), UL); member(usluga(_, Lok, _, _, _), UL))
            ),
            L),
    length(L, Br).


% 3.2 Client who has the most travel distance

% Base case: When we reach the target point, the distance is 0, and the path is a single node.
find_path_distance(Start, Start, [Start], 0) :- !.

% Recursive case: Explore paths from Start to End.
find_path_distance(Start, End, [Start | Path], Distance) :-
    rastojanie(Start, Next, Dist),
    find_path_distance(Next, End, Path, SubDistance),
    Distance is Dist + SubDistance.

shortest_path(Start, End, ShortestDistance) :-
    findall((Path, Distance),
            find_path_distance(Start, End, Path, Distance),
            AllPaths),
    sort(2, @=<, AllPaths, SortedPaths),
    SortedPaths = [(_, ShortestDistance) | _].

find_distances(UL, L):-
    findall(Distance,
            (
                member(usluga(From, To, _, _, _), UL),
                (rastojanie(From, To, Distance);
                shortest_path(From, To, Distance))
            ),
            L).

max_distance([(Id, _, _, Lens)], Id, MaxLen):-max_list(Lens, MaxLen).
max_distance([(Id, _, _, Lens) | L], Id, MaxLen):-
    max_distance(L, _, OldLen),
    max_list(Lens, MaxLen),
    MaxLen > OldLen.

max_distance([(_, _, _, _) | L], MaxId, MaxLen):-
    max_distance(L, MaxId, MaxLen).

najmnogu_kilometri(X, Y):-
    findall((ID, X, Y, R),
            (
                klient(ID, X, Y, UL),
                find_distances(UL, R)
            ),
            L),
   max_distance(L, ID, _),
   klient(ID, X, Y, _), !.


% 3.3 Id of taxi that earned the most in December 2015

path_lengths(PL, L):-
    findall((TId, From, To, Distance, Price),
            (
                member((TId, From, To, Price), PL),
                (shortest_path(From, To, Distance))
            ),
            L).

find_max_pay([(Id, _, _, Len, Price)], Id, Product):-Product is Len * Price.
find_max_pay([(Id, _, _, Len, Price) | L], Id, Prod):-
    find_max_pay(L, _, MaxProd),
    Prod is Len * Price,
    Prod > MaxProd.
find_max_pay([(_, _, _, _, _) | L], MaxId, MaxProd):-
    find_max_pay(L, MaxId, MaxProd).

najmnogu_zarabotil(MaxId):-
    findall((TId, From, To, Price),
            (
                klient(_, _, _, UL),
                member(usluga(From, To, Price, datum(_, 12, 2015), TId), UL)
            ),
            L),
    path_lengths(L, PL),
    find_max_pay(PL, MaxId, _).
