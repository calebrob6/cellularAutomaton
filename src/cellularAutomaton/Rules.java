package cellularAutomaton;

import java.util.HashMap;

public class Rules {


	public static final String[] ruleSets = {
		"15;14;13;3;11;5;6;1;7;9;10;2;12;4;8;0", //critters
		"15;1;2;3;4;5;6;7;8;9;10;11;12;13;14;0", //tron
		"0;8;4;3;2;5;9;14;1;6;10;13;12;11;7;15" //BounceGas
	};
	
	//public static String[] configurations = {"0000","1000","0100","1100","0010","1010","0110","1110","0001","1001","0101","1101","0011","1011","0111","1111"};
	public static final Byte[] configurations = {0X00,0X08,0X04,0X0C,0X02,0X0A,0X06,0X0E,0X01,0X09,0X05,0X0D,0X03,0X0B,0X07,0X0F};
	public HashMap<Byte, Integer> configRuleToIdx = new HashMap<Byte, Integer>();
	public HashMap<Integer, Byte> configIdxToRule = new HashMap<Integer, Byte>();

	public int[] rules;

	public Rules() {

		for (int i = 0; i < Rules.configurations.length; i++) {
			this.configIdxToRule.put(i, Rules.configurations[i]);
			this.configRuleToIdx.put(Rules.configurations[i], i);
		}

	}

	public Rules(String ruleString) {
		for (int i = 0; i < Rules.configurations.length; i++) {
			this.configIdxToRule.put(i, Rules.configurations[i]);
			this.configRuleToIdx.put(Rules.configurations[i], i);
		}
		setRules(ruleString);
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
