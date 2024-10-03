package daniel.brian.mealy.utils

fun String.shortenName(): String {
    val mealNameArray = split(" ").filter {
        it != "and" && it != "&" && it != "(" && it != "-" && it != "the"
                && it != "A" && it != "a"
    }.take(2)
    return mealNameArray.joinToString(" ")
}

fun String.shortenNameLength() : String {
    return if(this.length > 10) {
        this.take(7) + "..."
    }else{
        this
    }
}