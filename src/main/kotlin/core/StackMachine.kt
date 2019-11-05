package core

import core.model.Branch
import core.model.LangStackMachine
import java.util.*

class StackMachine(initData: LangStackMachine) {

    private val startState: String = initData.startState
    private val alphabets: CharArray = initData.getAlphabet()
    private val startStack: String = initData.startStack

    fun identifyChain(chain: String) {
        val stackChain = stringToStack(chain)
        val stackMachine = stringToStack(startStack)
        val fails = mutableListOf<String>()
        var branch = Branch(null, startState, stackChain, stackMachine)

        do {
            val state = startState
            while(!stackChain.empty()){
                val charChain = stackChain.pop()
                if(!alphabets.contains(charChain)) println("WARNING: symbol {${charChain}} not contains in the Lang alphabets")


            }
        }while (true)

    }

    fun stringToStack(chain: String): Stack<Char> {
        val stackMachine = Stack<Char>()
        chain.toList()
             .stream()
             .peek { symbol ->  stackMachine.push(symbol) }
        return stackMachine
    }
}