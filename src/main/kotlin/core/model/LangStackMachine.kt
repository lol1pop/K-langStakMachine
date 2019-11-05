package core.model

data class ActionProps(
    val mvState: String,
    val push: String
){
    fun getPushChar() = push.toCharArray()
}

data class LangProps(
    val state: String,
    val sees: Char,
    val onStack: Char,
    val action: List<ActionProps>
)

data class LangStackMachine (
    val states: String,
    val alphabet: String,
    val props: LangProps,
    val startState: Char,
    val endStates: String,
    val chain: String? = null
) {
    fun getState() = states.split(',')
    fun getAlphabet() = alphabet.toCharArray()
    fun getEndStates() = endStates.split(',')
}