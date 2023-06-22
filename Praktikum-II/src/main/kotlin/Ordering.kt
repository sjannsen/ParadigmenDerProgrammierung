enum class OrderResult {
    Lower, // -1
    Higher,  // 1
    Equal // 0
}

interface Ordering<A> {
    fun compare(left: A, right: A): OrderResult
}

typealias OrderingImpl<A> = (A, A) -> OrderResult

// Strategien

//class IntOrdering : Ordering<Int> {
//    override fun compare(left: Int, right: Int): OrderResult {
//        return if (left < right) OrderResult.Lower
//        else if (left > right) OrderResult.Higher
//        else OrderResult.Equal
//    }
//}

val intOrderingImpl: OrderingImpl<Int> =
    { left, right ->
    if (left < right) OrderResult.Lower
    else if (left > right) OrderResult.Higher
    else OrderResult.Equal
}

//class StringOrdering : Ordering<String> {
//    override fun compare(left: String, right: String): OrderResult {
//        val result = left.compareTo(right)
//        return if (result < 0) OrderResult.Lower
//        else if (result > 0) OrderResult.Higher
//        else OrderResult.Equal
//    }
//}


val stringOrderingImpl: OrderingImpl<String> =
    { left, right ->
    val result = left.compareTo(right)
    when {
        result < 0 -> OrderResult.Lower
        result > 0 -> OrderResult.Higher
        else -> OrderResult.Equal
    }
}

val doubleOrderingImpl: OrderingImpl<Double> =
    { left, right ->
    when {
        left < right -> OrderResult.Lower
        (left > right) -> OrderResult.Higher
        else -> OrderResult.Equal
    }
}

val booleanOrderingImpl: OrderingImpl<Boolean> =
    { left, right ->
    when {
        !left && right -> OrderResult.Lower
        left && !right -> OrderResult.Higher
        else -> OrderResult.Equal
    }
}

// Dekorierer

//class ReversedOrdering<A>(val ord: Ordering<A>) : Ordering<A> {
//    override fun compare(left: A, right: A): OrderResult {
//        return ord.compare(right, left)
//    }
//}

fun <A> reversedOrdering(orderingImpl: OrderingImpl<A>): OrderingImpl<A> =
    { left, right ->
    when (orderingImpl(left, right)) {
        OrderResult.Lower -> OrderResult.Higher
        OrderResult.Higher -> OrderResult.Lower
        OrderResult.Equal -> OrderResult.Equal
    }
}

//class DebugOrdering<A>(val ord: Ordering<A>) : Ordering<A> {
//    override fun compare(left: A, right: A): OrderResult {
//        val result = ord.compare(left, right)
//        println("$left is $result than $right")
//        return result
//    }
//}

fun <A> debugOrdering(orderingImpl: OrderingImpl<A>): OrderingImpl<A> =
    {left, right ->
        val result = orderingImpl(left, right)
        println("$left is $result than $right")
        result
    }


/*
* Beantworten Sie zudem folgende Fragen:
*
* Warum sind reversed und debug Funktionen höherer Ordnung?
*   reversedOrdering & debugOrdering nehmen eine Funktion als Parameter entgegen
*   und geben eine Funktion zurück
*
*
* Welches Entwurfsmuster wurde durch die Verwendung von Funktionen höherer Ordnung realisiert?
*   Dekorierer
*
*
*
* Warum kann das Entwurfsmuster dadurch implementiert werden?
* Was ist die Grundlegende Struktur des Entwurfsmusters und
* inwiefern korreliert diese Struktur mit der vonFunktionen höherer Ordnung?
*
*
*
*
*
* */


// Kompositum
 // 1.3

// a.)
data class Person(val name: String, val age: Int, val height: Double = 0.0)

// b.)

fun <A, B> contraMap(orderingImpl: OrderingImpl<A>, transform: (B) -> A): OrderingImpl<B>  = {
        left, right -> orderingImpl(transform(left), transform(right))
}

// 1.3

//c.)

val stringLengthOrderingImpl: OrderingImpl<String> = contraMap(
    intOrderingImpl
) { string -> string.length }

val personNameOrderingImpl: OrderingImpl<Person> = contraMap(stringOrderingImpl) { person ->
    person.name
}

val personAgeOrderingImpl: OrderingImpl<Person> = contraMap(intOrderingImpl) { it ->
    it.age
}

//class PersonNameOrdering : Ordering<Person> {
//    override fun compare(left: Person, right: Person): OrderResult {
//        return StringOrdering().compare(left.name, right.name)
//    }
//}

//class PersonAgeOrdering : Ordering<Person> {
//    override fun compare(left: Person, right: Person): OrderResult {
//        return IntOrdering().compare(left.age, right.age)
//    }
//}


fun  <A, B> zip(orderingImpl1: OrderingImpl<A>, orderingImpl2: OrderingImpl<B>): OrderingImpl<Pair<A, B>> = {
    left, right ->
    val ordering1Result = orderingImpl1(left.first, right.first)
    if (ordering1Result == OrderResult.Equal) orderingImpl2(left.second, right.second)
    else ordering1Result
}

//class CombineOrdering<A>(val ord1: Ordering<A>, val ord2: Ordering<A>) : Ordering<A> {
//    override fun compare(left: A, right: A): OrderResult {
//        val ord1Result = ord1.compare(left, right)
//        return if (ord1Result == OrderResult.Equal) ord2.compare(left, right)
//        else ord1Result
//    }
//}

//class Orderings<A>(val orderings: List<Ordering<A>>) : Ordering<A> {
//    override fun compare(left: A, right: A): OrderResult {
//        return when (orderings.size) {
//            0 -> OrderResult.Equal
//            1 -> {
//                val ord = orderings.first()
//                ord.compare(left, right)
//            }
//
//            else -> {
//                val ord = orderings.reduce { ord1, ord2 -> CombineOrdering(ord1, ord2) }
//                return ord.compare(left, right)
//            }
//        }
//    }
//}

// 1.5

// a)


fun <A, B> OrderingImpl<A>.zip2(orderingImpl2: OrderingImpl<B>): OrderingImpl<Pair<A, B>> = {
    left, right ->
    val ordering1Result = this(left.first, right.first)
    if (ordering1Result == OrderResult.Equal) orderingImpl2(left.second, right.second)
    else ordering1Result
}

fun <A, B> OrderingImpl<A>.contraMap2(transform: (B) -> A): OrderingImpl<B> = {
    left, right -> this(transform(left), transform(right))
}


fun <A> OrderingImpl<A>.debug(): OrderingImpl<A> = {
    left, right ->
    val result = this(left, right)
    println("$left is $result than $right")
    result
}


fun <A> OrderingImpl<A>.reversed(): OrderingImpl<A> = {
    left, right -> this(right, left)
}

class Sorting {
    fun <A> sort(list: MutableList<A>, ord: OrderingImpl<A>) {
        if (list.size <= 1) return

        var sorted = false
        var tmp: A?

        while (!sorted) {
            sorted = true
            for (i in 0 until list.lastIndex) {
                val left = list[i]
                val right = list[i + 1]
                // if (left > right)
                if (ord(left, right) == OrderResult.Higher) {
                    tmp = left
                    list[i] = right
                    list[i + 1] = tmp
                    sorted = false
                }
            }
        }
    }

//    fun <A> sort(list: MutableList<A>, ord: Ordering<A>) {
//        if (list.size <= 1) return
//
//        var sorted = false
//        var tmp: A?
//
//        while (!sorted) {
//            sorted = true
//            for (i in 0 until list.lastIndex) {
//                val left = list[i]
//                val right = list[i + 1]
//                // if (left > right)
//                if (ord.compare(left, right) == OrderResult.Higher) {
//                    tmp = left
//                    list[i] = right
//                    list[i + 1] = tmp
//                    sorted = false
//                }
//            }
//        }
//    }
}






