fun main() {

    val intDesc = reversedOrdering(intOrderingImpl)
    val string = debugOrdering(stringOrderingImpl)
    val doubleDesc = debugOrdering(reversedOrdering(doubleOrderingImpl))

    println(intDesc(5, 10))
    string("Hallo", "Welt")
    doubleDesc(5.0, 10.0)



   // 1.3
   // c)
   val stringLengthOrdering: OrderingImpl<String>


    // 1.4
    // b)
//    val people = mutableListOf(
//        Person(" Nathalie ", 25),
//        Person(" Alex ", 33),
//        Person("Zah ", 28),
//        Person(" Alex ", 18),
//        Person(" Jens ", 33),
//    )
//
//    val personOrd: OrderingImpl<Person> = contraMap(
//        zip(stringOrderingImpl, intOrderingImpl)
//    ) { person -> Pair(person.name, person.age) }
//
//    Sorting().sort(people, personOrd)
//
//    people.forEach(::println)


    // 1.5
    val people = mutableListOf(
        Person(" Nathalie ", 25, 172.5),
        Person(" Alex ", 33, 186.0),
        Person("Zah ", 28, 158.3),
        Person(" Alex ", 18, 183.0),
        Person(" Jens ", 33, 168.5),
    )

    val personOrd: OrderingImpl<Person> =
        stringOrderingImpl
            .zip2(intOrderingImpl.reversed())
            .zip2(doubleOrderingImpl)
            .contraMap2 { person ->
                person.name to person.age to person.height // kürzere Schreibweise für Pair( Pair(person.name, person.age), person.height )
            }
    Sorting().sort(people, personOrd)
    people.forEach(::println)


    // 2 f)
    val stringA = "Left"
    val intB = 5
    val either: Either<String, Int> = makeEither(stringA, intB)
}




// Aufgabe 2

// a)
/*
*   Welcher Typ in Kotlin ist äquivalent zur 1 in der Algebra?
*   Unit
*
* */


// b)

/*
*
* Zeigen Sie, ob Either<Option<A>, B> äquivalent bzw. isomorph zu Option<Either<A, B>> ist.
* Überführen Sie dazu die Typen in Algebra. Hinweis: Option ist der nullfähige Typ in Kotlin.
*
*
*   Either<A, B> -> Left<A> || Right<B>
*   Option<A> -> Some<A> || None
*
*   Either<Option<A>, B>
*   => Left<Option A> || Right <B>
*   => (Left<Some<A> || Left<None>) || Right <B>
*
*   Option<Either<A, B>>
*   => Some<Either<A, B>> || None
*   => (Some<Left<A>> || Some<Right<B>>) || None
*
*
*   => Sind äquivalent bzw. isomorph, beide repräsentieren die gleiche Information A, B oder None
* */

// c)

/*
*
* Überführen Sie das Potenzgesetz a^0 = 1 in Typen.
* Implementieren Sie auch die jeweilige to- und from-Funktion.
* Die Funktionen sind:
* fun <A> to(f: ( Nothing ) -> A): Unit = TODO ()
* fun <A> from ( unit : Unit ): ( Nothing ) -> A = TODO ()
*
* a^0 = 1, außer wenn a = 0
*  -> Unit, Nothing
*
* Nothing hat keinen Wert -> einfach Unit */
fun <A> to(f: (Nothing) -> A): Unit = Unit

// Nothing hat keinen Wert -> Fehler
fun <A> from(unit: Unit): (Nothing) -> A = { _ -> throw Exception() }



// d)

/*
* Warum kann die from-Funktion implementiert werden, obwohl nur ein Nothing zur Verfügungsteht,
* aber ein Wert vom Typ A zurückgegeben werden muss?
* Hinweis: Die Antwort liegt im Subtyping-System von Kotlin.
*
*
* Nothing -> unterste Typ in der Typenhierarchie (Bottom-Type), dadurch Nothing immer verwendbar
* fun <A> from(option: Option<A>): A
* Wenn option den Wert None enthält -> gibt es keinen gültigen Wert vom Typ A, für die Rückgabe
* In diesem Fall -> Nothing als Rückgabetyp, da Nothing ein Untertyp von A ist.
* Die from-Funktion entspricht dann noch den Typanforderungen, da Nothing immer als
* Wert vom Typ A herhalten kann, auch wenn Nothing als Wert nicht erreichbar ist.
*
* */

// e)
sealed interface Either <out A, out B>
data class Left <A >( val a: A): Either <A, Nothing >
data class Right <B >( val b: B): Either < Nothing , B>
fun <A, B> makeEither (a: A, b: B): Either <A, B> = Left(a)