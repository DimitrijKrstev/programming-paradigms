# Програмски Парадигми Домашна 2

## 1 Фамилии

### 1.1 Родени во различни градови од нивните родители

- `rodeni_razlicen_grad(N)` ја пребарува базата на податоци за сите лица кои се родени во град кој е различен од градовите на нивните родители. Се земаат родителите и нивните деца, потоа се споредуваат местата на раѓање на родителите и на децата. Ако детето е родено во различен град, се додава во резултатната листа. Предикатот враќа број на вакви случаи.

### 1.2 Предци со ист пол, родени 1 недела оддалечени

- `within_week_range(PDen, PMesec, Den, Mesec)` проверува дали два датума (ден и месец) се наоѓаат во временски интервал од 7 дена. Го користи предикатот `within_range`, кој ги зема предвид и случаите кога интервалот преминува во претходниот или следниот месец.
- `within_range(PDen, PMesec, _, Mesec, Start, End)` ја дефинира логиката за проверка на интервали во истиот месец или преминување во соседни месеци. Соодветно се споредуваат деновите и месеците за да се определи дали вториот датум се наоѓа во интервалот на првиот.
- `filter(Predci, Pol, Den, Mesec, Result)` филтрира листа на предци така што ги враќа само оние со ист пол како лицето, и кои се родени во временски интервал од 7 дена според предикатот `within_week_range`.
- `all_predci(D, Pol, Den, Mesec, L)` ја наоѓа целата листа на предци на дадено лице `D` преку рекурзивно пребарување низ родителите. Потоа, листата на предци се филтрира преку `filter` за да се задоволат барањата за пол и временски интервал.

## 2 Телекомуникации

### 2.1 Лице со најмногу повици кај други

- `person_max_calls(Lista, MaxPerson, To, MaxCalls)` ја споредува листата на лица и ги враќа лицето, примателот на повикот, и бројот на повици што се најмногу регистрирани кај други корисници.
- `najbroj(X, Y)` ја наоѓа личноста `X` и примателот на повикот `Y` со најмногу регистрирани повици во листите на други корисници, исклучувајќи ги сопствените.

### 2.2 Омилен број

- `id_minute_sum_pairs(PCalls, PSMS, LCalls, LSMS, Result)` ги собира сите идентификатори на броеви од повици и СМС пораки, пресметувајќи ја вкупната должина во минути за секој број.
- `sum_minutes(ID, List, TotalMins)` го пресметува вкупниот број минути за даден број `ID` според листата на повици `List`.
- `max_mins(List, ID, Mins)` го враќа бројот со најмногу минути од листата `List`.
- `omilen(B, R)` го наоѓа омилениот број `R` за даден број `B`, кој има најмногу вкупно време на повици (вклучувајќи СМС пораки, кои се сметаат како 100 минути).

## 3 Телекомуникации (Детално Објаснување)

### 3.1 Лице со најмногу повици кај други

- `person_max_calls(Lista, MaxPerson, To, MaxCalls)` Зима листа од сите лица и ги пребарува нивните повици за да го најде лицето (`MaxPerson`) кое најмногу ја контактирало одредена личност (`To`). Резултатот `MaxCalls` ја претставува максималната бројка на повици. За секој повик, се проверува примателот и се одржува континуирана пресметка на вкупните повици, што резултира со најголемата вредност.
- `najbroj(X, Y)` Го пребарува целата база на податоци за да го идентификува лицето `X` кое има направено најмногу повици кон друго лице `Y`. Овој предикат ги исклучува повиците кон самиот себе, така што бројот на повици е исклучиво кон други корисници.

### 3.2 Омилен број

- `id_minute_sum_pairs(PCalls, PSMS, LCalls, LSMS, Result)` Ги зема сите повици (`PCalls`) и пораки (`PSMS`) поврзани со даден број, ги спојува со локалните повици (`LCalls`) и пораки (`LSMS`), и создава парови од број и вкупен број минути (`Result`). Повиците се сумираат по нивната должина, додека за СМС пораките секоја се смета како 100 минути.
- `sum_minutes(ID, List, TotalMins)` Го пресметува вкупното време за повици кон одреден број `ID`. Пребарува низ листата `List` и собира ги должините на сите повици, враќајќи ја нивната вкупна вредност во `TotalMins`.
- `max_mins(List, ID, Mins)` Ги споредува сите бројки од листата и го враќа бројот `ID` со најголем вкупен број минути (`Mins`). Ова вклучува повици и пораки (како 100 минути по порака) за да се утврди кој број е најчесто контактиран.
- `omilen(B, R)` Главен предикат за наоѓање на омилениот број на корисникот `B`. Ги собира сите вкупни времиња за бројките со кои корисникот комуницира, користејќи `id_minute_sum_pairs`. Потоа, со `max_mins`, го одредува бројот `R` кој има најголема вредност, што го прави најчестиот контакт.
