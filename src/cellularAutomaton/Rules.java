package cellularAutomaton;

import java.util.HashMap;

public class Rules {


	
	public static String[] configurations = {"0000","1000","0100","1100","0010","1010","0110","1110","0001","1001","0101","1101","0011","1011","0111","1111"};

	public HashMap<String, Integer> configRuleToIdx = new HashMap<String, Integer>();
	public HashMap<Integer, String> configIdxToRule = new HashMap<Integer, String>();

	public int[] rules;

	public Rules() {

		for (int i = 0; i < Rules.configurations.length; i++) {
			this.configIdxToRule.put(i, Rules.configurations[i]);
			this.configRuleToIdx.put(Rules.configurations[i], i);
		}

	}

	public void setRules(String ruleString) {
		String[] ruleParts = ruleString.split(";");
		this.rules = new int[ruleParts.length];

		for (int i = 0; i < ruleParts.length; i++) {
			this.rules[i] = Integer.parseInt(ruleParts[i]);
		}
	}

	public void reverseRules() {
		int[] newRules = new int[this.rules.length];
		for (int i = 0; i < newRules.length; i++) {
			newRules[this.rules[i]] = i;
		}
		this.rules = newRules;
	}

}
