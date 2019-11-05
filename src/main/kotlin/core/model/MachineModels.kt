package core.model

import java.util.*

data class Branch(
    val previous: Branch?,
    val state: String,
    val stackChain: Stack<Char>,
    val stackMachine: Stack<Char>
) {
    val stringStackChain = if (!stackChain.isNullOrEmpty()) {
        stackChain.toString()
    } else { "λ" }

    val stringStackMachine = if (!stackMachine.isNullOrEmpty()) {
        stackMachine.toString()
    } else { "λ" }

    fun ReconstructHistory(): String {
        val stack = Stack<Branch>()
        var c: Branch?  = this
        while ( c != null){
            stack.push(c)
            c = c.previous
        }
        val res = stack.reversed()
        return res.joinToString(" ⊢ ")
    }

    override fun toString(): String {
        return "(${state}, ${stringStackChain}, ${stringStackMachine})"
    }
}