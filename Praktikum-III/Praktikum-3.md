% 1 Fakten, Regeln und Anfragen


% a)
elternKind(homer, bart).
elternKind(homer, lisa).
elternKind(homer, maggie).

elternKind(marge, bart).
elternKind(marge, lisa).
elternKind(marge, maggie).

elternKind(mona, homer).
elternKind(mona, herb).

elternKind(abraham, homer).
elternKind(abraham, herb).

elternKind(clancy, marge).
elternKind(clancy, patty).
elternKind(clancy, selma).

elternKind(jackie, marge).
elternKind(jackie, patty).
elternKind(jackie, selma).

elternKind(selma, ling).


% b)
istVorfahre(P1, P2) :-
    elternKind(P1, P2).

% c)

istVorfahre(P1, P2) :-
    elternKind(P1, P2).

istVorfahre(P1, P2) :-
    elternKind(P1, X),
    istVorfahre(X, P2).


% d)

sindGeschwister(P1, P2) :-
    elternKind(E, P1),
    elternKind(E, P2),
    P1 \= P2.


% e)

% Fakten über das Geschlecht der Personen
maennlich(homer).
maennlich(bart).
maennlich(herb).
maennlich(abraham).
maennlich(clancy).

weiblich(mona).
weiblich(lisa).
weiblich(maggie).
weiblich(ling).
weiblich(selma).
weiblich(patty).
weiblich(jackie).


istSchwesterVon(P1, P2) :-
    elternKind(E, P1),
    elternKind(E, P2),
    weiblich(P1),
    P1 \= P2.

istBruderVon(P1, P2) :-
    elternKind(E, P1),
    elternKind(E, P2),
    maennlich(P1),
    P1 \= P2.


% f)

% sindGeschwister(P1, P2) :-
%     elternKind(E, P1),
%     elternKind(E, P2),
%     P1 \= P2.

% ?- sindGeschwister(homer, Wer)

% sindGeschwister(homer, Wer) :-
%     elternKind(E, homer), => abraham, mona
%     elternKind(E, Wer), => herb
%     homer \= herb. => true




% 2 Arbeiten mit Listen

% a)

% Setzt ’Size ’ auf die Anzahl der Elemente in ’List ’
count([], 0). % alternativ auch Size/Res = 0.

count([_ | Tail], Size):-
    count(Tail, SizeAusDerZukunft),
    Size is SizeAusDerZukunft + 1.
    
% Zählt , wie oft ’X’ in ’List ’ vorkommt . Das Ergebnis steht in ’Res ’ 
countX(_, [], 0).

countX(Head, [Head|Tail], Res):-
    countX(Head, Tail, ResAusDerZukunft),
    Res is ResAusDerZukunft + 1.

countX(X, [Head|Tail], Res):-
    X \= Head,
    countX(X, Tail, Res).
    
% Setzt ’Sum ’ auf die Summe aller Elemente von ’List ’.
% Achtung : Dieses Prädikat soll nur mit Listen von Zahlen funktionieren   

sum([], 0).


sum([Head | Tail], Sum ):-
    number(Head),
    sum(Tail, SumAusDerZukunft),
    Sum is SumAusDerZukunft + Head.
    
    
% Setzt ’Avg ’ auf den Durchschnittswert aller Elemente in ’List ’
% Achtung : Dieses Prä dikat soll nur mit Listen von Zahlen funktionieren
avg(List, Avg ):-
    sum(List, Summe),
   	count(List, Size),
    Avg is Summe / Size.


% Testing

% ?- count([1, 2, 3], N).
% N = 3

% ?- count([], N).
% N = 0

% ?- countX(1, [1, 2, 3, 1, 5], N).
% N = 2

% ?- countX(1, [2, 3], N)
% N = 0

% ?- sum([10, 5, 3], N)
% N = 18

% ?- sum([], N)
% N = 0

% ?- avg([10, 5, 3], N)
% N = 6

% ?- avg([hallo, welt ], N)
% false


% b)

sumEndrekursiv([], Res, Res).

sumEndrekursiv([Head|Tail], Sum, Res):-
    NewSum is Sum + Head,
    sumEndrekursiv(Tail, NewSum, Res).

countEndrekursiv([], Res, Res).

countEndrekursiv([Head|Tail], Count, Res):-
    NewCount is Count + 1,
    countEndrekursiv(Tail, NewCount, Res).


% ?- sumEndrekursiv([10, 5, 3], 0, N).
% N = 18

% ?- countEndrekursiv([1, 2, 3], 0, N).
% N = 3


% c)

% Die Endrekursion, auch als "Tail Recursion" bezeichnet, ist ein Verfahren zur Optimierung von rekursiven Funktionsaufrufen. 
% Es lässt sich anwenden, wenn der letzte Funktionsaufruf innerhalb der rekursiven Funktion sie selbst ist.

% Die Idee dahinter ist, in einem solchen Fall den zusätzlichen Funktionsaufruf zu streichen und die aktuelle Instanz der Funktion 
% samt dazugehörigem Stack wiederzuverwenden. Auf die Art erspart die Endrekursion den aufwendigen Aufbau eines neuen Stacks, 
% den Aufruf einer neuen Funktion samt abschließendem Abbau des erzeugten Stacks.

% Bei rekursiven Funktionen, die mit Endrekursion optimiert werden, kann zudem kein Stack Overflow auftreten. 
% Das gehört bei rekursiven Funktionen zu einem der Standardfehler, nämlich wenn die Tiefe der Rekursion zu groß wird 
% und die Verwaltungsinformationen überhand nehmen.

% Ein Nachteil von Endrekursion sei jedoch nicht verschwiegen: Sie erschwert das Debuggen einer durch sie optimierten Funktion bedeutend, 
% da die Aufrufreihenfolge und der dazugehörige Call Stack nicht mehr erkennbar sind. 
% Schließlich wird letztlich nur durch die immer gleiche Instanz der Funktion iteriert.

% Zur Wiederholung:

% sum([Head | Tail], Sum ):-
%     number(Head),
%     sum(Tail, SumAusDerZukunft),
%    Sum is SumAusDerZukunft + Head.

% sum([], 0).

% Aufruf
% ?- sum([10, 5, 3], N)


% sum([10 | [5, 3]], Sum ):-
%     number(10), => True
%     sum([5, 3], SumAusDerZukunft), 
%     Sum is SumAusDerZukunft + 10.

% sum([5 | [3]], SumAusDerZukunft ):-
%     number(5), => True
%     sum([3], SumAusDerZukunft1), 
%     SumAusDerZukunft is SumAusDerZukunft1 + 5.

% sum([3 | []], SumAusDerZukunft1 ):-
%     number(3), => True
%     sum([], SumAusDerZukunft2), 
%     SumAusDerZukunft1 is SumAusDerZukunft2 + 53

% sum([], 0)

% Rücklauf

% sum([3 | []], SumAusDerZukunft1 ):-
%     number(3), => True
%     sum([], SumAusDerZukunft2), 
%     SumAusDerZukunft1 is 0 + 3

% sum([5 | [3]], SumAusDerZukunft ):-
%     number(5), => True
%     sum([3], 3), 
%     Sum is 3 + 5.

% sum([10 | [5, 3]], Sum ):-
%     number(10), => True
%     sum([5, 3], SumAusDerZukunft), 
%     Sum is 8 + 10.




% Wiederholung
% sumEndrekursiv([], Res, Res).

% sumEndrekursiv([Head|Tail], Sum, Res):-
%     NewSum is Sum + Head,
%     sumEndrekursiv(Tail, NewSum, Res).


% Aufruf
% ?- sumEndrekursiv([10, 5, 3], 0, N).

% sumEndrekursiv([10|[5, 3]], 0, Res):-
%     NewSum is 0 + 10,
%     sumEndrekursiv([5, 3], 10, Res).

% sumEndrekursiv([5|[3]], 10, Res):-
%     NewSum is 10 + 5,
%     sumEndrekursiv([3], 15, Res).

% sumEndrekursiv([[3]], 15, Res):-
%     NewSum is 15 + 3,
%     sumEndrekursiv([], 18, Res).

% sumEndrekursiv([], 18, 18).

% d)
% 
% Prüft, ob ’X’ in ’List ’ vorkommt
contains(X, [X|Tail]).

contains(X, [Head|Tail]):-
    contains(X, Tail).
    
    
% Setzt ’N’ auf das Element mit dem Index ’I’
elementAtIndex(0, Head, [Head|Tail]).

elementAtIndex(I, N, [Head|Tail]):-
               NewIndex is I - 1,
               elementAtIndex(NewIndex, N, Tail).
    
    
% Entfernt alle Vorkomnisse von ’X’ in ’List ’. Die neue Liste steht in ’Res ’
remove(X, [],[]).

remove(Head, [Head|Tail], Res):-
    remove(Head, Tail, Res).

remove(X, [Head|Tail], [Head|Res]):-
    X \= Head,
    remove(X, Tail, Res).


% ?- contains(3, [1, 2, 3]).
% true

% ?- contains(5, [1, 2, 3])
% false

% ?- elementAtIndex(0, X, [8, 3, 5])
% X = 8 %, weil die 8 an der 0ten Stelle in List steht

% ?- elementAtIndex(2, X, [8, 3, 5])
% X = 5 %, weil die 5 an der 2ten Stelle in List steht

% ?- elementAtIndex(4, X, [8, 3, 5])
% false %, weil weil 4 nicht in der Liste vorkommt

% ?- remove(5, [1, 2, 3], Res)
% Res = [1 ,2 ,3] % 5 kommt nicht in [1 ,2 ,3] vor

% ?- remove(2, [1, 2, 3], Res)
% Res = [1 ,3] % 2 wurde entfernt

% ?- remove(1, [], Res)
% Res = [] % leere Liste