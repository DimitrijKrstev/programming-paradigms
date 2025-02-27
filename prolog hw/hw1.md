# Програмски Парадигми Домашна 1

## 1 Непарен Палиндром

Централната идеја е преку backtracking да се спореди секој елемент во низата со елементот што е обратно од него. Цела низа е помината и елементите од втората половина на низата се споредени со елементите од првата половина на низата.

- ``neparen_palindrom(L)`` е влезниот предикат кој проверува дали низата е со непарен број елементи и понатаму започнува со проверката со ``palindrome(L, L, 0)``.
- ``palindrome(L, L, 0, Len)`` прима на влез низата, така што првата промелнива ја користи за итерирање низ низата, а втората за целата низа за понатамошна споредба на првата половина. Третата променлива е бројач, кој го инкрементираме на секое минење, што ни укажува дали сме на втората половина на низата и Len е должината на низата. Проверката за дали сме на втората половина се прави со `Len // 2 < Z` и потоа се споредува моменталниот елемент со неговиот пар преку `is_element_at(X, L, 0, Z)`.
- `is_element_at(X, L, 0, Z)` користи бројач да стигне до елементот за споредба кој е на позиција одалечена `Len - N - 1`, каде N  е моменталната позиција во првата половина

## 2 Под-низа со најмногу повторувања од должина N

Целта на решението е да се најде под-низа од дадената низа што најчесто се повторува и има должина N.

- `nаj_podniza(L, N, Lmax)` е главниот предикат кој ја повикува `sub_arr(L, N, [0], 0, Lmax)` за пресметување на најповторуваната под-низа.
- `sub_arr(L, N, Lmax, Max, FinalLMax)` ја поминува низата елемент по елемент, пресметувајќи ја моменталната под-низа со `next_N_els` и проверувајќи ја зачестеноста со `occurrence`. Го ажурира моменталниот максимум `Max` и соодветната под-низа `Lmax`.
- `next_N_els(L, N, SubList)` ја генерира следната под-низа со должина N, што го ограничува решението на соодветната должина.
- `occurrence(L1, L2, N, Lmax, Max, NewLmax, NewMax)` брои колку пати под-низата `L1` се појавува во остатокот од низата `L2` и ги ажурира максимумот и најповторуваната под-низа.
- `is_sublist(L1, L2, N)` проверува дали `L1` е под-низа на `L2` со дадената должина `N`.

## 3 Низа со редослед мал, голем

- `proveri(L)` ја проверува должината на низата и го започнува процесот на споредба преку `next_is_greater`.
- `next_is_greater(X, Y)` проверува дали секој елемент е помал од следниот, и потоа преминува на `next_is_lesser`.
- `next_is_lesser(X, Y)` проверува дали елементот е поголем од следниот. Овие два предикати се повикуваат наизменично, обезбедувајќи правилен редослед.

## 4 Пермутации

 Идејата е преку findall да се генерираат сите пермутации на дадена листа.

- `permutations(L1, L2)` го користи предикатот `findall` за да ги генерира сите можни пермутации на листата `L1` и да ги зачува во `L2`.
- `permutation(L, P)` рекурзивно преку backtracking ги генерира сите можни распореди на елементите со помош на.
- `remove_from(X, L, R)` го отстранува елементот `X` од листата `L`, со што се овозможува создавање на нови пермутации.

## 5 Бинарна аритметика

### 5.1 Собирање

Откога ќе се еквализираат двете низи на иста големина, со backtracking се собираат поединечни цифри, чиј правила за собирање се во `binary_add()`. Потоа, на поголемата низа се додаваат цифрите во резултатот, соодветно со преостанат carry over од собирањето на двете еквализирани низи.

- `sobiranje(L1, L2, L3)` го повикува `equalize` за да ги изедначи должините на низите и започнува со собирање.
- `binary_add(X1, X2, C, L, R, NewC)` пресметува еден бит од резултатот, враќајќи го новиот carry.
- `equalize` итерира низ двете низи додека не се еднаква должина и потоа преку backtracking ги додава преостанатите елементи од подолгата низа со евентуален carry over.

### 5.2 Одземање

Идејата е слична како и за бинарното собирање. Се еквализира па се продолжува со одземање, па се додава преостанатото од поголемата низа (која мора секогаш да е првата).

- `odzemanje(L1, L2, R)` го проверува условот за одземање преку `is_subtractable` и повикува `sub` за пресметување.
- `binary_sub(X1, X2, C, L, R, NewC)` пресметува одземање за еден бит и соодветно позајмување (C).
- `equalize_sub` е слична како `equalize` во бинарното собирање, различна е во тоа што повикува одземање наместо собирање и претпоставува дека само првата низа може да е подолга од двете низи.
- `remove_zeros` се користи на отстранување на преостанати нули на почетокот на бројот при извршеното одзимање.

### 5.3 Множење

Решението следи стандардно бинарно множење каде се врши поместување (shifting) на множеникот преку backtracking.

- `mnozenje(L1, L2, L3)` го користи `multiply` за да пресмета производ преку рекурзивни повици и пресметка на shift.
- `sobiranje` се користи за собирање на поместените делумни производи.

### 5.4 Делење

Решението пресметува целобројно делење.

- `delenje(A, B, L)` рекурзивно одзема `B` од `A` сè додека `A` е поголемо или еднакво на `B`, градејќи го резултатот `L`.
- `can_divide(L1, L2)` проверува дали бројот L1 е поголем или еднаков на L2
- `sobiranje()` Го зголемува парцијалниот количник користејќи собирање за да се пресмета конечниот количник за овој чекор.

## 6 Матрица помножена со сопствената транспонирана

Наместо да ја транспонираме матрицата за да ја помножиме сама со себе, решението е ефективно исто и поефикасно така што ќе ја помножиме секоја редица со секоја друга. Односно, за секоја позиција во резултатната матрица, $X_{ij}$ се добива со $Col_{i} * Col_{j}$

- `presmetaj(L1, R)` го повикува `row_by_row` кој итерира низ секоја редица во матрицата.
- `multiply_rows(X1, L2, R)` ја множи дадената редица со секоја редица од матрицата, вклучивајќи ја самата редица.
- `mat_mul` пресметува скаларен производ помеѓу две редици.

## 7 Уникатни листи, сортирани по должина и елементи

Овој алгоритам ги сортира листите според должината, а потоа лексикографски.

- `transform(L, R)` го повикува `bubble_sort_arr` за сортирање на листите.
- `current_pos_max` го наоѓа најголемиот елемент во моменталната листа, користејќи должина и елементи за споредба.
- `remove_element` обезбедува уникатност на листите.

## 8 Бришење на секое второ појавување на елемент

Централната идеја е да се брише секое второ појавување на елемент во листа што содржи подлисти, притоа задржувајќи ја оригиналната структура на листата. Ова се постигнува со броење на појавувањата на секој елемент и нивно филтрирање базирано на бројот на појавувања.

- `brisi_sekoe_vtoro(L, R)` е влезниот предикат кој ја започнува обработката на листата преку повик на `delete_2nd(L, R, [], _)`. Првиот параметар е влезната листа, вториот е резултантната листа.
- `delete_2nd(L, R, OI, OO)` е главниот предикат кој ја процесира листата. Параметрите се:
    - L: влезна листа што се процесира
    - R: резултантна листа
    - OI: акумулатор за броење на појавувања (влез)
    - OO: финална состојба на акумулаторот (излез)
- `count_occ(E, L, C)` го брои бројот на појавувања на елемент E во листата на појавувања L. Резултатот се враќа во C. Доколку елементот не е пронајден, се враќа 0.
- `update_occ(E, L, NL)` го ажурира бројачот за даден елемент E во листата L. Ако елементот веќе постои, го зголемува неговиот бројач за 1, во спротивно додава нов елемент со бројач 1.
