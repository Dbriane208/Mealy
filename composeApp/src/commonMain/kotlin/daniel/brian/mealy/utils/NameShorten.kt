package daniel.brian.mealy.utils

fun String.shortenName(): String {
    val mealNameArray = this.replace("(","").split(" ").filter { word ->
        word.lowercase() !in setOf("and", "&", ")","-", "the", "a")
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