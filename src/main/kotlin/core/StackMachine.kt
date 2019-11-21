package core

import core.model.*
import java.util.*

class StackMachine(initData: LangStackMachine) {

    private val startState: String = initData.startState
    private val alphabets: CharArray = initData.getAlphabet()
    private val startStack: String = initData.startStack
    private val props: List<LangProps> = initData.props

    fun identifyChain(chain: String): IdentifyResult {
        var stackChain = stringToStack(chain)
        var stackMachine = stringToStack(startStack)
        val fails = mutableListOf<String>()
        val branches = Stack<Branch>()
        var branch = Branch(null, startState, stackChain, stackMachine)

        do {
            var state = branch.state
            stackChain = branch.stackChain
            stackMachine = branch.stackMachine
            while (!stackChain.empty() && !stackMachine.empty()) {
                val charChain = stackChain.peek()
                if (!alphabets.contains(charChain)) println("WARNING: symbol {${charChain}} not contains in the Lang alphabets")
                val charSack = stackMachine.peek()
                var options = if (branch.listProps.isEmpty()) getNeedRules(state, charChain, charSack) else branch.listProps
                if (options.isEmpty()){
//                    fails += "${branch.ReconstructHistory()}: No way for (${state}, ${charChain}, ${charSack})."
//                    break
                    options = listOf(LangProps(state,'-',charSack, ActionProps(state,"")))
                }
                val next = options[0]
                val alternatives = options.drop(1)
                for (altered in alternatives) {
                    branches.push(Branch(branch, altered.state, stackChain, stackMachine, listOf(altered)))
                }

                if(next.sees != '-')
                    stackChain.pop()
                stackMachine.pop()
                state = next.action.mvState
                val push = next.action.push.toCharArray().reversedArray()
                for (symbol in push){
                    stackMachine.push(symbol)
                }
                branch = Branch(branch, state, stackChain, stackMachine)
            }
            if(stackChain.empty()){
                if(stackMachine.empty()){
                    return IdentifyResult( true, branch.ReconstructHistory())
                } else {
                    //fails += "${branch.ReconstructHistory()}: Stopped Stack chain is empty while stackMachine is not empty."
                    branches.push(
                        Branch(
                            branch,
                            branch.state,
                            branch.stackChain,
                            branch.stackMachine,
                            listOf(
                                LangProps(
                                    branch.state,
                                    '-',
                                    branch.stackMachine.peek(),
                                    ActionProps(branch.state, "")
                                )
                            )
                        )
                    )
                }
            } else {
                if(stackMachine.empty()){
                    fails += "${branch.ReconstructHistory()}: Stopped Stack chain is not empty while stackMachine is empty."
                } else {
                    fails += "${branch.ReconstructHistory()}: Stopped not in final configuration."
                }
            }
            if (!branches.empty()){
                branch = branches.pop()
            } else {
                break
            }
        } while (true)

        val log = "None path accepted the string:\n" + fails.joinToString("\n")
        return IdentifyResult(false, log)
    }

    private fun getNeedRules(state: String, symbol: Char, stack: Char): List<LangProps> {
        val rules = mutableListOf<LangProps>()
        for (rule in props) {
            if (rule.state == state &&
                rule.sees == symbol &&
                rule.onStack == stack) {
                rules.add(rule)
            }
        }
        return rules.toList()
    }

    private fun stringToStack(chain: String): Stack<Char> {
        val stack = Stack<Char>()
         for(symbol in chain.reversed()) {
             stack.push(symbol)
         }
        return stack
    }
}